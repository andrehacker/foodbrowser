
-- set client_encoding to 'latin1'; # had this before, probably not needed

--  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
-- year_code was added at some time
CREATE TABLE production_raw (
  country_code VARCHAR(10),
  country VARCHAR(70),
  item_code VARCHAR(10),
  item VARCHAR(100),
  element_code VARCHAR(10),
  element VARCHAR(100),
  year_code VARCHAR(4),
  year VARCHAR(4),
  unit VARCHAR(10),
  value VARCHAR(20),
  flag VARCHAR(10)
);


-- Country data were retrieved from following Excel file: http://faostat.fao.org/site/371/DesktopDefault.aspx?PageID=371
-- Excel Header: Faostat area code', 'ISO2', 'Area name', 'Area long name'
-- 2022-03-15 converted to csv just for better maintenance.
-- 2022-03-15 added 5707 country code to csv, probably needed for EU with 27 members only (no UK). Not sure what ISO code is.
CREATE TABLE countries (
        country_code  integer CONSTRAINT firstkey PRIMARY KEY,
        iso_code      char(2) NOT NULL,
        name          varchar(50) NOT NULL,
        long_name     varchar(60) NOT NULL,
        type          int NOT NULL DEFAULT 1
);
