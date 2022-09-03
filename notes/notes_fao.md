## Dataset Statistics

__Production Crops DB__
293 countries
249 countries with iso2 code
183 items
4 types
years: 1961 - 2012 = 51

4 * 183 * 293 = 214.476
214.476 * 51  = 10.938.276

Records in production: 2.346.959
- 256 distinct country codes in production table. include totals (country_code >= 5000)
- 674.566 records for country_code >= 5000
- => 2.346.959 - 674.566 = 1.672.393 valid records

Fact-count for measure:
5510: 740.057
5312: 691.027


__Redundant Data:__
/Production_Crops_E_All_Data.csv contains redundant lines:
"41","China, mainland","656","Coffee, green","31","5312","Area Harvested","2011","Ha","29550.000",""
"41","China, mainland","656","Coffee, green","31","5312","Area Harvested","2011","Ha","29550.000",""

All China mainland data (country_id 41) are redundant.

Unique 2.327.806
sort ~/dev/foodbrowser/tea-datasets/faostat/Production_Crops_E_All_Data.csv | uniq --count | wc -l
Redundant: 19.154
sort ~/dev/foodbrowser/tea-datasets/faostat/Production_Crops_E_All_Data.csv | uniq --count | grep -e "^\s*2" > redundant
Test:
sort ~/dev/foodbrowser/tea-datasets/faostat/Production_Crops_E_All_Data.csv | uniq --count | grep -e "^\s*2" | grep -e "China.*656.*29550"

Remove redundant data: 
```
db.facts.find({country_id: 41}).count() = 38308 = 19154 * 2
var res = db.facts.aggregate(
    { $match : { country_id : 41 } },
    { $group : { _id : {item_id: "$item_id", measure_id:"$measure_id", year: "$year", country_id: "$country_id"}, fact_count: { $sum : 1 } } } );
res.result.length
res.result.forEach(function(obj){ db.facts.remove( {item_id: obj._id.item_id, measure_id:obj._id.measure_id, year: obj._id.year, country_id: obj._id.country_id} , true) });
```

Count afterwards = 2327805
Redundant count openshift: 6290. New count 733767 (= 740.057 - 6290)

## MongoDB Queries
```
# CODES
# - items: 667=tea, 656=coffee, green, 221=almonds
# - countries: 351=China, 100=India, 38=Sri Lanka
# - elements: 5312=area harvested 5419=Yield 5510=Production 5525=Seed

# search by item (and measure and year)
db.facts.find( {item_id: 667, measure_id: 5510, year: 2011} );

# Count of facts for one country and all items:
db.facts.aggregate(
    { $match : { country_id : 41, year: {$gte: 2011, $lte: 2012} } },
    { $group : { _id : "$country_id", fact_count: { $sum : 1 } } } );

# Facts for 1 country, n items and range of years (time series)
db.facts.aggregate(
    { $match : { country_id : 41, measure_id : 5510, year: {$gte: 2011, $lte: 2011}, item_id: { $in: [656, 667]} } },
    { $group : { 
        _id : {country_id: "$country_id", item_id: "$item_id"},
        fact_count: { $sum : 1 },
        values: {$push: {year: "$year", value: "$value", flag: "$flag"}} } },
    { $project : {
        _id : 0,
        item_id: "$_id.item_id",
        values: "$values"} } );
```




## API

Resource APIs
This is used in Query Builder!!
http://api.data.fao.org/1.0/esb-rest/resources/introduction.html

Named queries (Get the list of resources of type 'query':):
http://api.data.fao.org/1.0/esb-rest/catalog/examples.html

ALL properties for countries
http://data.fao.org/statistics/techcdr-mdx?authKey=d30aebf0-ab2a-11e1-afa6-0800200c9a66&version=1.0&workspace=Fisheries&catalog=fisheries&schema=Fisheries%20-%20en&cube=Production&dimension=Country&propertyMode=all

All countries - but only name and iso3
http://data.fao.org/statistics/named-query?database=countryprofiles&queryName=country-list&authKey=d30aebf0-ab2a-11e1-afa6-0800200c9a66&version=1.0

