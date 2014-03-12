

## Remove quotes from numbers
echo '"5100","Africa + (Total)","2659.33"' | sed -r 's|"([0-9]+.[0-9]+)"|\1|g'

CREATE TABLE items AS SELECT item_code as code, item AS name FROM fao GROUP BY item_code, item;

CREATE TABLE countries AS SELECT country_code as code, country AS name FROM fao GROUP BY country_code, country;

select * from fao where item_code = 2635 and country_code = 351 and year = 2009;
-- germany 79
select * from fao where item_code = 2635 and country_code = 79 and year = 2009;