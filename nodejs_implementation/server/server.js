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
  var sql = "SELECT country_code, iso_code, name as country_name \
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
 * http://localhost:8888/perproduct?productid=667&typeid=5510&year=2011
 */
// Add route using app.<http-method>(url, function)
app.get('/perproduct', function(req, res) {
  console.log(req.query.q)
  var sql = "SELECT min(c.name) AS name, min(iso_code) as iso_code, sum(value) AS summ \
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
 * http://localhost:8888/percountry?countryid=351&typeid=5510&year=2011
 */
app.get('/percountry', function(req, res) {
  console.log(req.query.q)
  var sql = "SELECT c.name, year, i.name, i.item_code as product_code, value AS value \
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

/*
 * 1 country, 1 type, N products, N years
 * http://localhost:8888/percountrytime?yearfrom=2010&yearto=2011&countryid=351&typeid=5510&products=667,656
 */
 app.get('/percountrytime', function(req, res) {
  console.log(req.query)
  if (_.isUndefined(req.query.products)) {res.json({}); return;};
  var sql = "SELECT c.name, year, i.name, i.item_code, value AS value \
    FROM production p \
    INNER JOIN countries c ON c.country_code = p.country_code \
    INNER JOIN items i ON i.item_code = p.item_code \
    WHERE \
      p.country_code={countryid} \
      AND year>={yearfrom} AND year<={yearto} \
      AND value<>0 \
      AND element_code={typeid} \
      AND p.item_code < 1000 \
      AND i.item_code IN ({products}) \
    ORDER BY c.country_code desc;"
    .replace('{countryid}', req.query.countryid)
    .replace('{typeid}', req.query.typeid)
    .replace('{yearfrom}', req.query.yearfrom)
    .replace('{yearto}', req.query.yearto)
    .replace('{products}', req.query.products);
  console.log(sql)

  client.query(sql, function(err, rows, fields) {
    if (err) throw err;

    var result = new Array();
    var pos = -1;
    var lastItemCode;
    for (var i=0; i<rows.rows.length; i++) {
      if (lastItemCode !== rows.rows[i].item_code) {
        pos++;
        lastItemCode = rows.rows[i].item_code;
        result[pos] = {name: rows.rows[i].name, itemcode: rows.rows[i].item_code};
      }
      result[pos][rows.rows[i].year] = rows.rows[i].value;
    }
    res.json(result);
  });
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