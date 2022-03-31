
-- DIMENSION TABLES -----

-- One can derive countries from fact table, but downside is there is no additional info about the countries except country code and name. So we take the csv from faostat.
-- some country_codes have two entries
insert into countries select country_code, max(name), max(m49_code), max(iso2_code), max(iso3_code), max(start_year), max(end_year), 1 from countries_raw group by country_code;
update countries set type = 2 where country_code >= 400;
-- Trade data have additional country codes like "Africa (excluding intra-trade)". They are not in definitions and standards.
insert into countries
  select cast(country_code as INTEGER), min(country), null, null, null, null, null, 3 from production_raw where country like '%excluding intra-trade%' group by country_code;

INSERT INTO items
  SELECT CAST(item_code AS INTEGER), item AS name FROM production_raw GROUP BY item_code, item;

INSERT INTO elements
  SELECT CAST(element_code AS INTEGER), element AS name, unit FROM production_raw GROUP BY element_code, element, unit;

-- FACT TABLE --

-- Create as select and transform (indices, foreign keys) is faster than insert into select into empty table
DROP TABLE IF EXISTS production;
CREATE TABLE production AS
  SELECT
    CAST(country_code AS INTEGER) AS country_code,
    CAST(item_code AS INTEGER) AS item_code,
    CAST(element_code AS INTEGER) AS element_code,
    CAST(year AS INTEGER) AS year,
    CAST(value AS DECIMAL(20,5)) AS value,
    flag
    FROM production_raw;
ALTER TABLE production ALTER COLUMN country_code SET NOT NULL;
ALTER TABLE production ALTER COLUMN item_code SET NOT NULL;
ALTER TABLE production ALTER COLUMN year SET NOT NULL;

CREATE INDEX production_country_code_idx ON production(country_code);
CREATE INDEX production_item_code_idx ON production(item_code);
CREATE INDEX production_element_code_idx ON production(element_code);
CREATE INDEX production_year_idx ON production(year);

ALTER TABLE production ADD CONSTRAINT FK_PRODUCTION_COUNTRY_CODE
  FOREIGN KEY (country_code)
  REFERENCES countries(country_code)
  ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE production ADD CONSTRAINT FK_PRODUCTION_ELEMENT_CODE
  FOREIGN KEY (element_code)
  REFERENCES elements(element_code)
  ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE production ADD CONSTRAINT FK_PRODUCTION_ITEM_CODE
  FOREIGN KEY (item_code)
  REFERENCES items(item_code)
  ON UPDATE CASCADE ON DELETE RESTRICT;


-- Handle flags
-- flags are data quality indicators. "These flags would indicate to the user the source and, thus, the reliability of the data"


