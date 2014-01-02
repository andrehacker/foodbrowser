// Feostat uses highcharts http://www.highcharts.com/

google.load('visualization', '1', {packages:['table']});
google.load("visualization", "1", {packages:["corechart"]});
google.load('visualization', '1', {'packages': ['geochart']});

google.setOnLoadCallback(onGoogleLoad);

var TABLE_PAGE_SIZE = 15;

function onGoogleLoad() {
  getAllProducts();
  getAllCountries();
  getProduction(667);
  getCountryProduction(351);
  // 351 = china
  // 667 = tea
}

var optionsMap = {
  backgroundColor: 'b1d0fe',  // b1d0fe = Google maps water
  region: 'world',  //  world is default
  //datalessRegionColor: 'white',
  //colorAxis: {minValue: 0,  colors: ['#FF0000', '#00FF00']},
  // efe6dc, 109618 is default green
  colorAxis: {colors: ['#d1dbc2', '#109618']},
  //colorAxis: {colors: ['blue', 'green', 'red']},
  resolution: 'countries'
};

function getAllProducts() {
  $.getJSON('/products', function(data) {
    data.sort(function(a, b) { 
      return a.product_name > b.product_name;
    })
    var options = $("#product-select");
    for(var i=0; i<data.length; i++) {
      //countries[data[i].country_name] = data[i].country_code;
      options.append($("<option />").val(data[i].product_code).text(data[i].product_name));
    }
  });
}

//var countries = new Array();
function getAllCountries() {
  $.getJSON('/countries', function(data) {
    data.sort(function(a, b) { 
      return a.country_name > b.country_name;
    })
    var options = $("#country-select");
    for(var i=0; i<data.length; i++) {
      //countries[data[i].country_name] = data[i].country_code;
      options.append($("<option />").val(data[i].country_code).text(data[i].country_name));
    }
  });
}

function switchProduct(value) {
  getProduction(value);
  $("#product-select")[0].selectedIndex = 0;
  var productName = $("#product-select option[value='" + value + "']").text()
  $("#product-header").html(productName + " Production World Map");
}

function switchCountry(value) {
  getCountryProduction(value);
  $("#country-select")[0].selectedIndex = 0;
  var countryName = $("#country-select option[value='" + value + "']").text()
  $("#country-header").html(countryName + " Production");
}

function getProduction(productID) {
  $.getJSON('/product/$product-id/production'.replace('$product-id',productID), function(data) {
//    alert(JSON.stringify(data))

    var datatable = new google.visualization.DataTable();
    datatable.addColumn('string', 'Country');
    datatable.addColumn('number', 'Production (tonnes)');
    datatable.addRows(data.length);
//    var matrix = new Array(data.length+1);
//    matrix[0] = ['Country', 'Production (tonnes)'];
    for(var i=0; i<data.length; i++) {
//      matrix[i+1] = [data[i].name, data[i].summ];
      datatable.setCell(i, 0, data[i].name);
      datatable.setCell(i, 1, data[i].summ, data[i].summ);  // number and caption
    }
//    alert(JSON.stringify(matrix))
//    var datatable2 = google.visualization.arrayToDataTable(matrix);

    // draw geochart
    var chart = new google.visualization.GeoChart(document.getElementById('map_div'));
    chart.draw(datatable, optionsMap);

    // draw table
    var table = new google.visualization.Table(document.getElementById('table_div'));
    table.draw(datatable, {showRowNumber: true, page: 'enable', pageSize:TABLE_PAGE_SIZE});
  });
}

function getCountryProduction(countryID) {
  $.getJSON('/country/$country-id/production'.replace('$country-id',countryID), function(data) {
//    alert(JSON.stringify(data))

    var datatable = new google.visualization.DataTable();
    datatable.addColumn('string', 'Product');
    datatable.addColumn('number', 'Production (tonnes)');
    datatable.addRows(data.length);
    for(var i=0; i<data.length; i++) {
      datatable.setCell(i, 0, data[i].name);
      datatable.setCell(i, 1, data[i].production, data[i].production);  // number and caption
    }

    var barOptions = {
      title: 'Production',
      vAxis: {title: 'Production tonnes',  titleTextStyle: {color: 'red'}}
    };

    // draw geochart
    //var chart = new google.visualization.BarChart(document.getElementById('bar_div'));
    //chart.draw(datatable, barOptions);

    // draw table
    var table = new google.visualization.Table(document.getElementById('bar_table_div'));
    table.draw(datatable, {showRowNumber: true, page: 'enable', pageSize:20});
  });
}



// Sample data
//        var data = google.visualization.arrayToDataTable([
//          ['Country', 'Popularity'],
//          ['Germany', 200],
//          ['United States', 300],
//          ['Brazil', 400],
//          ['Canada', 500],
//          ['France', 600],
//          ['RU', 700]
//        ]);
        //var data = google.visualization.arrayToDataTable(countriesTeaProduction);
