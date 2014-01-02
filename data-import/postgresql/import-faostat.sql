# How to execute:
# mysql --enable-local-infile=1 -u root -p
# source import-faostat.sql
# 
# Should add 2254078 entries to production table

DROP DATABASE IF EXISTS tea;

CREATE DATABASE tea;

USE tea;

CREATE TABLE tea.production_raw (
#  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
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

LOAD DATA LOCAL INFILE 'tea-datasets/faostat/Production_Crops_E_All_Data.csv'
INTO TABLE tea.production_raw
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES;
#(field1, @dummy, field2, @rechenVar)
#SET field3 = @rechenVar*10


# http://www.postgresql.org/docs/current/static/sql-copy.html