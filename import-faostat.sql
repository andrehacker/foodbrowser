# Should add 2254078 entries to production table
# Call this script with --enable-local-infile=1
# Or add loose-local-infile=1 to [client] section in /etc/mysql/my.cf
# To show warnings, run mysql client with --show-warnings option

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

