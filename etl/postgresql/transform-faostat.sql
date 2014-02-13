
-- Countries are based on M49 UN classification
-- See http://unstats.un.org/unsd/methods/m49/m49alpha.htm
-- Google expects ISO 3166-1

-- ----- CREATE DIMENSION TABLES -----

-- Extract countries
-- DON'T TAKE THIS, USE THE countries.sql INSTEAD!
DROP TABLE IF EXISTS countries;
CREATE TABLE countries AS SELECT CAST(country_code AS INTEGER) as code, country AS name FROM production_raw GROUP BY country_code, country;
ALTER TABLE countries ALTER COLUMN code TYPE INTEGER;
ALTER TABLE countries ALTER COLUMN code SET NOT NULL;
-- TODO PRIMARY KEY
ALTER TABLE countries RENAME COLUMN code TO country_code;

-- Distinguish countries from continents, etc.
-- 1 = normal country
-- 2 = other (todo: Divide further into world, continent, region, ...)
ALTER TABLE countries ADD COLUMN type INTEGER NOT NULL DEFAULT 1;
UPDATE countries set type = 2 where country_code >= 5000;

-- Extract items
DROP TABLE IF EXISTS items;
CREATE TABLE items AS SELECT CAST(item_code AS INTEGER) as code, item AS name FROM production_raw GROUP BY item_code, item;
ALTER TABLE items ALTER COLUMN code TYPE INTEGER;
ALTER TABLE items ALTER COLUMN code SET NOT NULL;
-- TODO PRIMARY KEY
ALTER TABLE items RENAME COLUMN code to item_code;

-- Extract elements
DROP TABLE IF EXISTS elements;
CREATE TABLE elements AS
  SELECT CAST(element_code AS INTEGER) as code, element_group, element AS name, unit FROM production_raw GROUP BY element_code, element_group, element, unit;
ALTER TABLE elements ALTER COLUMN code TYPE INTEGER;
ALTER TABLE elements ALTER COLUMN code SET NOT NULL;
-- TODO PRIMARY KEY
ALTER TABLE elements RENAME COLUMN code TO element_code;

-- ----- TRANSFORM FACT TABLE -----
-- TODO: Indices, foreign keys, not-null
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
ALTER TABLE production ALTER COLUMN country_code TYPE INTEGER;
ALTER TABLE production ALTER COLUMN country_code SET NOT NULL;
ALTER TABLE production ALTER COLUMN item_code TYPE INTEGER;
ALTER TABLE production ALTER COLUMN item_code SET NOT NULL;
ALTER TABLE production ALTER COLUMN element_code TYPE INTEGER;
ALTER TABLE production ALTER COLUMN element_code SET NOT NULL;
ALTER TABLE production ALTER COLUMN year TYPE INTEGER;
ALTER TABLE production ALTER COLUMN year SET NOT NULL;
ALTER TABLE production ADD INDEX (country_code);
ALTER TABLE production ADD INDEX (item_code);
ALTER TABLE production ADD INDEX (element_code);
ALTER TABLE production ADD INDEX (year);

ALTER TABLE production ADD CONSTRAINT
  FOREIGN KEY (country_code)
  REFERENCES countries(country_code)
  ON UPDATE CASCADE ON DELETE RESTRICT;

-- DID NOT WORK?!
ALTER TABLE production ADD CONSTRAINT
  FOREIGN KEY (element_code)
  REFERENCES elements(element_code)
  ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE production ADD CONSTRAINT
  FOREIGN KEY (item_code)
  REFERENCES items(item_code)
  ON UPDATE CASCADE ON DELETE RESTRICT;



-- Handle flags
-- flags are data quality indicators. "These flags would indicate to the user the source and, thus, the reliability of the data"
