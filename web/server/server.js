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

// Add route using app.<http-method>(url, function)
app.get('/tea/production', function(req, res) {

  var sql = "SELECT c.name, sum(value) AS summ \
  FROM production p \
  INNER JOIN countries c ON c.country_code = p.country_code \
  WHERE \
    item_code=667 \
    AND year=2011 \
    AND p.country_code < 5000 \
    AND element_code=5510 \
  GROUP BY p.country_code \
  ORDER BY summ DESC;"
  console.log(sql)

  connection.query(sql, function(err, rows, fields) {
    if (err) throw err;

    console.log('The solution is: ', rows);

    res.json(rows);
  });
});

app.listen(8888);

//connection.end();