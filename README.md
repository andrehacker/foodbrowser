## Food Browser ([Live Demo](http://foodbrowser-andrehacker.rhcloud.com))
A dynamic webapp visualizing statistics about things you eat and drink (currently only crops). All statistics are extracted from [FAOSTAT](http://faostat.fao.org/) (UN department).

Includes
* Two backend implementations
    * A Java/Spring/MongoDB REST/Json backend
    * Node.js server offering an REST/Json API, using a relational db.
* ETL scripts to load data from FAOSTAT csv into database (postgres or mysql) and into MongoDB
* Static web pages for visualization, using jQuery, Twitter Bootstrap and Google charts.

## MongoDB schema
I use a normalized star schema with one fact table and three dimension tables (countries, items, measures). The client (JavaScript) holds all values for the dimension-tables in memory and can resolve the IDs without any additional query. A denormalized (embedded) model would not have any advantages for this use case.

## Openshift
The app is hosted via PaaS provider [openshift](http://openshift.com/). There is free support for MongoDB, Java and JBoss Enterprise Application Platform 6. Due to the size restrictions I uploaded data for one measure only (production).


<!--
Postgres Import
https://devcenter.heroku.com/articles/heroku-postgres-import-export#import

NodeJS ORM Mapper and 
http://dailyjs.com/2013/04/15/node-database-library/
- sql
- sequelize
- squel

Drivers
- mysql and pg
-->
