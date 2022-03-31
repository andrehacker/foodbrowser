
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
  unit VARCHAR(50),
  value VARCHAR(20),
  flag VARCHAR(10),
  note VARCHAR(100)  -- 2022-03-20: this was in Population, but not in Production data
);


-- Faostat has it's own country code. Download is offered under "Definitions and Standards" on faostat:
-- https://www.fao.org/faostat/en/#definitions
-- The download URL is not transparent though. So I downloaded and stored in repo
-- The list contains a mapping to UN M49 Code, which it is based on
--
-- FAO also has NOCS database of "names of countries and territories": https://www.fao.org/nocs/en/
-- https://nocs.fao.org/exportCountryData
-- It does not contain the country_id used in faostat though
--
-- Google visualization expects ISO 3166-1


CREATE TABLE countries_raw (
        country_code  integer,
        name          varchar(100) NOT NULL,
        m49_code      char(6),
        iso2_code     char(2),
        iso3_code     char(3),
        start_year    char(4),
        end_year      char(4)
);

CREATE TABLE country_groups_raw (
        country_group_code integer NOT NULL,
        country_group_name varchar(100) NOT NULL,
        country_code       integer NOT NULL,
        country_name       varchar(100) NOT NULL,
        m49_code           varchar(6),
        iso2_code          char(2),
        iso3_code          char(3)
);


CREATE TABLE countries (
        country_code  integer PRIMARY KEY,
        name          varchar(100) NOT NULL,
        m49_code      char(6),
        iso2_code     char(2),
        iso3_code     char(3),
        start_year    char(4),
        end_year      char(4),
        type          int NOT NULL DEFAULT 1
);

CREATE TABLE items (
  item_code INTEGER PRIMARY KEY,
  name varchar(100)
);

CREATE TABLE elements (
  element_code INTEGER PRIMARY KEY,
  name varchar(100),
  unit varchar(50)
);

CREATE TABLE production (
  country_code INTEGER NOT NULL,
  item_code    INTEGER NOT NULL,
  element_code INTEGER NOT NULL,
  year         INTEGER NOT NULL,
  value        DECIMAL(20,5),
  flag         varchar(10)
--  CONSTRAINT FK_PRODUCTION_COUNTRY_CODE
--    FOREIGN KEY (country_code)
--    REFERENCES countries(country_code)
--    ON UPDATE CASCADE ON DELETE RESTRICT,
--  CONSTRAINT FK_PRODUCTION_ELEMENT_CODE
--    FOREIGN KEY (element_code)
--    REFERENCES elements(element_code)
--    ON UPDATE CASCADE ON DELETE RESTRICT,
--  CONSTRAINT FK_PRODUCTION_ITEM_CODE
--    FOREIGN KEY (item_code)
--    REFERENCES items(item_code)
--    ON UPDATE CASCADE ON DELETE RESTRICT
);
--CREATE INDEX production_country_code_idx ON production(country_code);

-- older variant (from excel no longer available)
--CREATE TABLE countries (
--        country_code  integer PRIMARY KEY,
--        iso_code      char(2) NOT NULL,
--        name          varchar(100) NOT NULL,
--        long_name     varchar(100) NOT NULL,
--        type          int NOT NULL DEFAULT 1
--);
