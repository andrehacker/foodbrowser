/*
 * REST API for faostat statistics
 * 
 * There are two ways to support complex queries:
 * 1) GET and parameters
 * 2) POST and query object
 * Hard to say what is better for our purpose...
 */


var express = require('express');
var pg = require('pg');
var path = require('path');
var _ = require('underscore');

// Create express server instance
var app = express();

//var mysql      = require('mysql');
//var connection = mysql.createConnection({
//  host     : 'localhost',
//  user     : 'root',
//  password : 'hannek',
//  database : 'tea'
//});
//connection.connect();

// postgres://USER:PASS@HOST/DATABASE
var conString = "postgres://postgres:postgres@localhost/tea";

var client = new pg.Client(conString);
client.connect(function(err) {
  if(err) {
    return console.error('could not connect to postgres', err);
  }
});

app.configure(function () {
  var staticDir = path.resolve(__dirname, '../client');
  console.log('Directory with static http content: ' + staticDir);
  app.use(
    "/", //the URL through which to access to static content
    express.static(staticDir) // location of static content
  );
});

// elements: 5312=area harvested(Ha) 5419=Yield(Hg/Ha) 5510=Production Quantity(tonnes) 5525=Seed(tonnes)

/*
 * Countries
 */
app.get('/countries', function(req, res) {
  var sql = "SELECT country_code, name as country_name \
    FROM countries \
    WHERE \
      type=1"
  getResult(req, res, sql)
});

/*
 * Products
 */
app.get('/products', function(req, res) {
  var sql = "SELECT item_code as product_code, name as product_name \
    FROM items"
  getResult(req, res, sql)
});

/*
 * Elements (e.g. production, yield, ...)
 */
app.get('/types', function(req, res) {
  var sql = "SELECT element_code as type_id, name, unit \
    FROM elements"
  getResult(req, res, sql)
});

/*
 * Supported years
 */
app.get('/years', function(req, res) {
  // 1961 - 2012
  res.json(_.range(1961, 2013));
});

/*
 * 1 product, 1 type, N countries
 */
// Add route using app.<http-method>(url, function)
app.get('/perproduct', function(req, res) {
  console.log(req.query.q)
  var sql = "SELECT min(c.name) AS name, sum(value) AS summ \
    FROM production p \
    INNER JOIN countries c ON c.country_code = p.country_code \
    WHERE \
      item_code={productid} \
      AND year={year} \
      AND p.country_code < 5000 \
      AND element_code={typeid} \
    GROUP BY p.country_code \
    ORDER BY summ DESC;"
    .replace('{productid}', req.query.productid)
    .replace('{typeid}', req.query.typeid)
    .replace('{year}', req.query.year);
  getResult(req, res, sql)
});

/*
 * 1 country, 1 type, N products
 */
app.get('/percountry', function(req, res) {
  console.log(req.query.q)
  var sql = "SELECT c.name, year, i.name, value AS production \
    FROM production p \
    INNER JOIN countries c ON c.country_code = p.country_code \
    INNER JOIN items i ON i.item_code = p.item_code \
    WHERE \
      p.country_code={countryid} \
      AND year={year} \
      AND value<>0 \
      AND element_code={typeid} \
      AND p.item_code < 1000 \
    ORDER BY value desc;"
    .replace('{countryid}', req.query.countryid)
    .replace('{typeid}', req.query.typeid)
    .replace('{year}', req.query.year);
  getResult(req, res, sql)
});

function getResult(req, res, sql) {
  console.log(sql)
  client.query(sql, function(err, rows, fields) {
    if (err) throw err;

//    console.log('The solution is: ', rows);

    // Postgre result is stored in .rows property.
    // MySql result can be returned directly
    res.json(rows.rows);
  });
}

app.listen(8888);

//client.end();
//connection.end();