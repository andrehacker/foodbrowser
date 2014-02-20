## Food Browser
A dynamic webapp visualizing statistics about things you eat and drink (currently only crops). All statistics are extracted from [FAOSTAT](http://faostat.fao.org/) (UN department).

[__LIVE DEMO__](foodbrowser-andrehacker.rhcloud.com)

Includes
* Two backend implementations
    * A Java/Spring/MongoDB backend (other repo)
    * Node.js server offering an REST API for the data, using relational db.
* ETL scripts to load data from FAOSTAT csv into database (postgres or mysql)
* Static web pages for visualization, using jQuery, Twitter Bootstrap and Google charts.
* Hosting will be done using heroku, openshift, or any other one (in progress).

## MongoDB schema
* I use a normalized schema to keep 

## Openshift
* I use PaaS openshift to host my application. There is free support for MongoDB (500MB), Java and JBoss Enterprise Application Platform 6.


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
