## Food Browser ([Live Demo](http://foodbrowser-andrehacker.rhcloud.com))
A dynamic webapp visualizing statistics about things you eat and drink (currently only crops). All statistics are extracted from [FAOSTAT](http://faostat.fao.org/) (UN department).

Includes
* Logic to load data from FAOSTAT csv into database (postgres or mysql) or into MongoDB
* Static web pages for visualization, using jQuery, Twitter Bootstrap and Google charts.
* Two backend implementations
    * Node.js server offering an REST/Json API, using a relational db.
    * A Java/Spring/MongoDB REST/Json backend (deprecated)

## MongoDB schema
I use a normalized star schema with one fact table and three dimension tables (countries, items, measures). The client (JavaScript) holds all values for the dimension-tables in memory and can resolve the IDs without any additional query. A denormalized (embedded) model would not have any advantages for this use case.


