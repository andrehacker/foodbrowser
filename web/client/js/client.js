// Feostat uses highcharts http://www.highcharts.com/

google.load('visualization', '1', {packages:['table']});
google.load("visualization", "1", {packages:["corechart"]});
google.load('visualization', '1', {'packages': ['geochart']});

google.setOnLoadCallback(getTeaProduction);
google.setOnLoadCallback(getCountryProduction);

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

function getTeaProduction()
{
  $.getJSON('/product/tea/production', function(data) {
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
    table.draw(datatable, {showRowNumber: true, page: 'enable', pageSize:20});
  });
}

function getCountryProduction()
{
  $.getJSON('/country/Sri Lanka/production', function(data) {
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
