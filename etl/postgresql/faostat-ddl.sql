
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

CREATE TABLE tradematrix_raw (
  country_code VARCHAR(10),
  country VARCHAR(70),
  partner_country_code VARCHAR(10),
  partner_country VARCHAR(70),
  item_code VARCHAR(10),
  item VARCHAR(100),
  element_code VARCHAR(10),
  element VARCHAR(100),
  year_code VARCHAR(4),
  year VARCHAR(4),
  unit VARCHAR(50),
  value VARCHAR(20),
  flag VARCHAR(10)
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

CREATE TABLE items_raw (
  domain_code varchar(5),
  domain varchar(200),
  item_code varchar(40),
  item_name varchar(300),
  description varchar(5000),
  hs_code varchar(100),
  hs07_code varchar(1000),  -- can contain multi-value like "010110, 010190"
  hs12_code varchar(1000),
  cpc_code varchar(200)
);

CREATE TABLE elements (
  element_code INTEGER PRIMARY KEY,
  name varchar(100),
  unit varchar(50)
);

CREATE TABLE units (
  unit_code varchar(40) PRIMARY KEY,
  description varchar(500)
);

CREATE TABLE flags (
  flag_code varchar(20) PRIMARY KEY,
  description varchar(500)
);

CREATE TABLE abbreviations (
  abbreviation_code varchar(40) PRIMARY KEY,
  description varchar(500)
);

