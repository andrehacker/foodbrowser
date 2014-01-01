# ----- QUERIES -----
#
# Dimensions
# - items
# - countries
# - element
# - year
# 
# CODES
# - items: 667=tea, 656=coffee, green, 221=almonds
# - countries: 351=China, 100=India, 38=Sri Lanka
# - elements: 5312=area harvested(Ha) 5419=Yield(Hg/Ha) 5510=Production Quantity(tonnes) 5525=Seed(tonnes)

# China Tea production per year
SELECT name, year, value FROM production p
  INNER JOIN countries c ON c.country_code = p.country_code
  WHERE p.country_code=351 AND item_code=667 AND element_code=5510
  ORDER BY year DESC LIMIT 10;

# Top Countries for product xy
SELECT c.name, element_code, sum(value) AS summ
  FROM production p
  INNER JOIN countries c ON c.country_code = p.country_code
  WHERE
    item_code=667
    AND year=2011
    AND p.country_code < 5000
    AND element_code=5510
  GROUP BY p.country_code
  ORDER BY summ DESC;

# Top products of country xy, in year 2011
SELECT c.name, year, i.name, value
  FROM production p
  INNER JOIN countries c ON c.country_code = p.country_code
  INNER JOIN items i ON i.item_code = p.item_code
  WHERE year=2011 AND p.country_code=351 AND value<>0 AND element_code=5510
  ORDER BY value desc;
