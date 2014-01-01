var express = require('express');

// Create express server instance
var app = express();

var mysql      = require('mysql');
var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : 'hannek',
  database : 'tea'
});
connection.connect();

app.configure(function () {
    app.use(
        "/", //the URL throught which you want to access to you static content
        express.static('/home/andre/dev/tea/web/client') //where your static content is located in your filesystem
    );
});

// TODO read mapping into memory
var productIDs = new Array()
productIDs['tea'] = 667;
productIDs['coffee'] = 656;

var countryIDs = new Array()
countryIDs['china'] = 351;
countryIDs['india'] = 100;
countryIDs['Sri Lanka'] = 38;

// elements: 5312=area harvested(Ha) 5419=Yield(Hg/Ha) 5510=Production Quantity(tonnes) 5525=Seed(tonnes)

// Add route using app.<http-method>(url, function)
app.get('/product/:product/production', function(req, res) {
  // get ID of the product
  var productID = productIDs[req.params.product]
  if (productID == undefined) {
    res.json('unknown product \'' + req.params.product + '\'');
    return;
  }
  console.log(req.params.product)
  var sql = "SELECT c.name, sum(value) AS summ \
  FROM production p \
  INNER JOIN countries c ON c.country_code = p.country_code \
  WHERE \
    item_code={productid} \
    AND year=2011 \
    AND p.country_code < 5000 \
    AND element_code=5510 \
  GROUP BY p.country_code \
  ORDER BY summ DESC;".replace('{productid}', productID)
  getStats(req, res, sql)
});

app.get('/country/:country/production', function(req, res) {
  var countryID = countryIDs[req.params.country]
  if (countryID == undefined) {
    res.json('unknown country \'' + req.params.country + '\'');
    return;
  }
  console.log('' + countryID);
  var sql = "SELECT c.name, year, i.name, value AS production \
  FROM production p \
  INNER JOIN countries c ON c.country_code = p.country_code \
  INNER JOIN items i ON i.item_code = p.item_code \
  WHERE year=2011 AND p.country_code={countryid} AND value<>0 AND element_code=5510 \
  ORDER BY value desc;".replace('{countryid}', countryID)
  getStats(req, res, sql)
});

function getStats(req, res, sql) {
  console.log(sql)
  connection.query(sql, function(err, rows, fields) {
    if (err) throw err;

    console.log('The solution is: ', rows);

    res.json(rows);
  });
}

app.listen(8888);

//connection.end();