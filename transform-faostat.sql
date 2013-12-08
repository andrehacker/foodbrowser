
# ----- CREATE DIMENSION TABLES -----

# Extract countries
DROP TABLE IF EXISTS countries
CREATE TABLE countries AS SELECT CAST(country_code AS UNSIGNED) as code, country AS name FROM production_raw GROUP BY country_code, country;
ALTER TABLE countries CHANGE code country_code INT NOT NULL PRIMARY KEY;

# Distinguish countries from continents, etc.
# 1 = normal country
# 2 = other (todo: Divide further into world, continent, region, ...)
ALTER TABLE countries ADD COLUMN type INT NOT NULL DEFAULT 1;
UPDATE countries set type = 2 where country_code >= 5000;

# Extract items
DROP TABLE IF EXISTS items
CREATE TABLE items AS SELECT CAST(item_code AS UNSIGNED) as code, item AS name FROM production_raw GROUP BY item_code, item;
ALTER TABLE items CHANGE code item_code INT NOT NULL PRIMARY KEY;

# Extract elements
DROP TABLE IF EXISTS elements
CREATE TABLE elements AS
  SELECT CAST(element_code AS UNSIGNED) as code, element_group, element AS name, unit FROM production_raw GROUP BY element_code, element_group, element, unit;
ALTER TABLE elements CHANGE code element_code INT NOT NULL PRIMARY KEY;

# ----- TRANSFORM FACT TABLE -----
# TODO: Indices, foreign keys, not-null
DROP TABLE IF EXISTS production;
CREATE TABLE production AS
  SELECT
    CAST(country_code AS UNSIGNED) AS country_code,
    CAST(item_code AS UNSIGNED) AS item_code,
    CAST(element_code AS UNSIGNED) AS element_code,
    CAST(year AS UNSIGNED) AS year,
    CAST(value AS DECIMAL(20,5)) AS value,
    flag
    FROM production_raw;
ALTER TABLE production MODIFY COLUMN country_code int(10) UNSIGNED NOT NULL;
ALTER TABLE production MODIFY COLUMN item_code int(10) UNSIGNED NOT NULL;
ALTER TABLE production MODIFY COLUMN element_code int(10) UNSIGNED NOT NULL;
ALTER TABLE production MODIFY COLUMN year int(4) UNSIGNED NOT NULL;
ALTER TABLE production ADD INDEX (country_code);
ALTER TABLE production ADD INDEX (item_code);
ALTER TABLE production ADD INDEX (element_code);
ALTER TABLE production ADD INDEX (year);

ALTER TABLE production ADD CONSTRAINT
  FOREIGN KEY (country_code)
  REFERENCES countries(country_code)
  ON UPDATE CASCADE ON DELETE RESTRICT;

# DID NOT WORK?!
ALTER TABLE production ADD CONSTRAINT
  FOREIGN KEY (element_code)
  REFERENCES elements(element_code)
  ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE production ADD CONSTRAINT
  FOREIGN KEY (item_code)
  REFERENCES items(item_code)
  ON UPDATE CASCADE ON DELETE RESTRICT;



# Handle flags
# flags are data quality indicators. "These flags would indicate to the user the source and, thus, the reliability of the data"
