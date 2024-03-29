create or replace view production_denorm as select value, i.name "item_name", e.name "element_name", e.unit "element_unit", c.name "country_name", year, flag,
  p.item_code,
  p.element_code,
  p.country_code, c.iso2_code "country_iso2_code", c.iso3_code "country_iso3_code", c.type "country_type"
  from production p
  left join elements e on p.element_code = e.element_code
  left join items i on p.item_code = i.item_code
  left join countries c on p.country_code = c.country_code
  ;

create or replace view tradematrix_denorm as select value, i.name "item_name", e.name "element_name", e.unit "element_unit", c.name "country_name", c2.name "partner_country_name", year, flag,
  t.item_code,
  t.element_code,
  t.country_code, c.iso2_code "country_iso2_code", c.iso3_code "country_iso3_code", c.type "country_type",
  t.partner_country_code
  from tradematrix t
  left join elements e on t.element_code = e.element_code
  left join items i on t.item_code = i.item_code
  left join countries c on t.country_code = c.country_code
  left join countries c2 on t.partner_country_code = c2.country_code
  ;
