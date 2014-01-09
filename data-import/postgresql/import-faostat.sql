-- How to execute:
-- Execute as user postgres
-- 
-- Should add 2254078 entries to production table

-- make sure we have user postgres (to be able to delete tea)
--\c postgres

--DROP DATABASE IF EXISTS tea;

--CREATE DATABASE tea;

--\c tea

set client_encoding to 'latin1';

--  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
CREATE TABLE production_raw (
  country_code VARCHAR(10),
  country VARCHAR(50),
  item_code VARCHAR(10),
  item VARCHAR(100),
  element_group VARCHAR(10),
  element_code VARCHAR(10),
  element VARCHAR(100),
  year VARCHAR(4),
  unit VARCHAR(10),
  value VARCHAR(20),
  flag VARCHAR(10)
);

COPY production_raw
 FROM '/home/andre/dev/tea/tea-datasets/faostat/Production_Crops_E_All_Data.csv'
 WITH DELIMITER ',' ESCAPE '"'
 CSV HEADER;
--  FORMAT csv HEADER true ESCAPE '"'

--LOAD DATA LOCAL INFILE '../tea-datasets/faostat/Production_Crops_E_All_Data.csv'
-- INTO TABLE tea.production_raw
-- FIELDS TERMINATED BY ','
-- ENCLOSED BY '"'
-- LINES TERMINATED BY '\r\n'
-- IGNORE 1 LINES;
--(field1, @dummy, field2, @rechenVar)
--SET field3 = @rechenVar*10


-- http://www.postgresql.org/docs/current/static/sql-copy.html
