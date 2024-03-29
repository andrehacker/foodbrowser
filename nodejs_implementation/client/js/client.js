/*
 * All data are taken from FAOSTAT (faostat.fao.org), the Food and Agriculture Organization of the United Nations.
 * 
 * D3 might be more flexible: 
 * - http://bl.ocks.org/mbostock/4060606
 * - http://bl.ocks.org/jasondavies/4188334
 *
 * FaoData has a REST API, which is however not used in new interface
 * - http://data.fao.org/
 * - http://api.data.fao.org/1.0/
 * - http://faodata.blogspot.de/2011/12/data.html
 * - http://www.programmableweb.com/api/faodata
 * 
 * Author: André Hacker
 */


/*
 * Use the module pattern
 * http://addyosmani.com/resources/essentialjsdesignpatterns/book/#modulepatternjavascript
 */

var StatsModule = (function() {

  "use strict";

  /*
   *
   * Constants and variables
   *
   */

  var PER_PRODUCT_TABLE_SIZE = 20;
  var PER_COUNTRY_TABLE_SIZE = 20;

  var INITIAL_PRODUCT_ID = 667;    // 667 = tea. green coffee = 656
  var INITIAL_COUNTRY_ID = 351;    // 351 = china
  var INITIAL_YEAR       = 2020;
  var INITIAL_YEAR_FROM  = 2010;
  var INITIAL_YEAR_TO    = 2020;
  var INITIAL_TYPE_ID    = 5510;   // 5510 = production
  var INITIAL_TYPE_DESCRIPTION = 'Production (tonnes)';

  var perProductState = {
    // current filter
    productCode: INITIAL_PRODUCT_ID,
    typeID: INITIAL_TYPE_ID,
    year: INITIAL_YEAR,
    typeDescription: INITIAL_TYPE_DESCRIPTION,
    // visualization objects
    tableChart: {},
    geoChart: {},
    lastDataTable: {}
  };

  var perCountryState = {
    // current filter
    countryCode: INITIAL_COUNTRY_ID,
    typeID: INITIAL_TYPE_ID,
    year: INITIAL_YEAR,
    typeDescription: INITIAL_TYPE_DESCRIPTION,
    // visualization objects
    tableChart: {},
    lastDataTable: {}
  };

  var perCountryTimeState = {
    // current filter
    //countryCode: INITIAL_COUNTRY_ID,
    //typeID: INITIAL_TYPE_ID,
    //typeDescription: INITIAL_TYPE_DESCRIPTION,
    yearfrom: INITIAL_YEAR_FROM,
    yearto: INITIAL_YEAR_TO,
    products: undefined,
    // visualization objects
    lineChart: {}
  };

  var allTypes;


  /*
   *
   * Initialize products and countries
   *
   */

  var getAllProducts = function() {
    $.getJSON('/products', function (data) {
      data.sort(function(a, b) { 
        return a.product_name.localeCompare(b.product_name);
      });
      var options = $("#per_product_product_select");
      options.find('option').remove();
      for(var i=0; i<data.length; i++) {
        var isSelected = (parseInt(data[i].product_code) === parseInt(perProductState.productCode)) ? ' selected ' : '';
        options.append($("<option " + isSelected + "/>").val(data[i].product_code).text(data[i].product_name));
      }
    });
  }

  var getAllTypes = function() {
    $.getJSON('/types', function(data) {
      allTypes = data;
      data.sort(function(a, b) { 
        return a.name.localeCompare(b.name);
      })
      populateTypes('#per_product_type_select', perProductState.typeID, data);
      populateTypes('#per_country_type_select', perCountryState.typeID, data);
    });
  }

  var populateTypes = function(controlID, currentTypeID, data) {
    var options = $(controlID);
    options.find('option').remove();
    for(var i=0; i<data.length; i++) {
      var isSelected = (parseInt(data[i].type_id) === parseInt(currentTypeID)) ? ' selected ' : '';
      options.append($("<option " + isSelected + "/>").val(data[i].type_id).text(data[i].name + " (" + data[i].unit + ")"));
    }
  }

  var getAllYears = function() {
    $.getJSON('/years', function(data) {
      data.sort(function(a, b) { 
        return a < b;
      });
      populateYears('#per_product_year_select', perProductState.year, data);
      populateYears('#per_country_year_select', perCountryState.year, data);
      populateYears('#per_country_yearfrom_select', perCountryTimeState.yearfrom, data);
      populateYears('#per_country_yearto_select', perCountryTimeState.yearto, data);
    });
  }

  var populateYears = function(controlID, currentYear, data) {
    var options = $(controlID);
    options.find('option').remove();
    for(var i=0; i<data.length; i++) {
      var isSelected = (parseInt(data[i]) === parseInt(currentYear)) ? ' selected ' : '';
      options.append($("<option " + isSelected + "/>").val(data[i]).text(data[i]));
    }
  }

  var getAllCountries = function() {
    $.getJSON('/countries', function(data) {
      data.sort(function(a, b) { 
        return a.country_name.localeCompare(b.country_name);
      });
      var options = $("#per_country_country_select");
      options.find('option').remove();
      for(var i=0; i<data.length; i++) {
        var isSelected = (parseInt(data[i].country_code) === parseInt(perCountryState.countryCode)) ? ' selected ' : '';
        options.append($("<option " + isSelected + "/>").val(data[i].country_code).text(data[i].country_name));
      }
    });
  }


  /*
   *
   * Per Product view
   *
   */

  var updatePerProductView = function() {
    $.getJSON('/perproduct?productid=${productid}&typeid=${typeid}&year=${year}'
      .replace('${productid}',perProductState.productCode)
      .replace('${typeid}',perProductState.typeID)
      .replace('${year}',perProductState.year), function(data) {
  //    alert(JSON.stringify(data))

      var datatable = new google.visualization.DataTable();
      datatable.addColumn('string', 'Country');
      datatable.addColumn('number', perProductState.typeDescription);
      datatable.addRows(data.length);
      for(var i=0; i<data.length; i++) {
        datatable.setCell(i, 0, data[i].name);
        datatable.setCell(i, 1, parseInt(data[i].summ), parseInt(data[i].summ).toString());
      }
      perProductState.lastDataTable = datatable;

      // See http://stackoverflow.com/questions/18204678/how-to-format-numbers-in-google-api-linechart
      var formatter1 = new google.visualization.NumberFormat({pattern:'###,###'});
      formatter1.format(datatable, 1);
      perProductState.geoChart.draw(datatable, optionsGeoChart);
      perProductState.tableChart.draw(datatable, {showRowNumber: true, page: 'enable', pageSize:PER_PRODUCT_TABLE_SIZE});
    });
  }

  var optionsGeoChart = {
    backgroundColor: 'b1d0fe',  // b1d0fe = Google maps water
    region: 'world',  //  world is default
    //datalessRegionColor: 'white',
    //colorAxis: {minValue: 0,  colors: ['#FF0000', '#00FF00']},
    // efe6dc, 109618 is default green
    colorAxis: {colors: ['#d1dbc2', '#109618']},
    //colorAxis: {colors: ['blue', 'green', 'red']},
    resolution: 'countries'
  };

  var perProductSelectHandler = function() {

    // Show selection in geo chart. Does not work as desired.
    
    // Trick from http://stackoverflow.com/questions/18321025/google-charts-api-set-selection-choose-series-to-highlight
    var enableTrick = true;
    if (enableTrick) {
      var selection = perProductState.tableChart.getSelection();
      perProductState.geoChart.setSelection(selection);
      if (selection.length > 0) {
        var view = new google.visualization.DataView(perProductState.lastDataTable);
        view.setColumns([0, {
          type: 'number',
          label: perProductState.lastDataTable.getColumnLabel(1),
          calc: function (dt, row) {
            return (selection[0].row === row) ? 2 : 1;
          }
        }]);
        perProductState.geoChart.draw(view, optionsGeoChart);
      } else {
        perProductState.geoChart.draw(perProductState.lastDataTable, optionsGeoChart);
      }
    }
  }


  /*
   *
   * Per Country view
   *
   */

  var updatePerCountryView = function() {

    $.getJSON('/percountry?countryid=$country-id&typeid=${typeid}&year=${year}'
      .replace('$country-id',perCountryState.countryCode)
      .replace('${typeid}',perCountryState.typeID)
      .replace('${year}',perCountryState.year), function(data) {

      var datatable = new google.visualization.DataTable();
      datatable.addColumn('string', 'Product');
      datatable.addColumn('number', 'Production (tonnes)');
      datatable.addRows(data.length);
      for(var i=0; i<data.length; i++) {
        datatable.setCell(i, 0, data[i].name);
        datatable.setCell(i, 1, parseInt(data[i].value), parseInt(data[i].value).toString());  // number and caption
        datatable.setRowProperty(i, 'product_code', data[i].product_code)
      }
      var formatter1 = new google.visualization.NumberFormat({pattern:'###,###'});
      formatter1.format(datatable, 1);
      perCountryState.lastDataTable = datatable;
      perCountryState.tableChart.draw(datatable, {showRowNumber: true, page: 'enable', pageSize:PER_COUNTRY_TABLE_SIZE});

      // Select first entry, will cause the time series view to be updated
      perCountryState.tableChart.setSelection([{row:0}]);
      perCountrySelectHandler();
    });
  }

  var barOptions = {
    title: 'Production',
    vAxis: {title: 'Production tonnes',  titleTextStyle: {color: 'red'}}
  };

  var updatePerCountryTimeView = function() {

    if (_.isUndefined(perCountryTimeState.products)) return;
    $.getJSON('/percountrytime?countryid=$country-id&products=${products}&typeid=${typeid}&yearfrom=${yearfrom}&yearto=${yearto}'
      .replace('$country-id',perCountryState.countryCode)
      .replace('${typeid}',perCountryState.typeID)
      .replace('${yearfrom}',perCountryTimeState.yearfrom)
      .replace('${yearto}',perCountryTimeState.yearto)
      .replace('${products}',perCountryTimeState.products), function(data) {

      var datatable = new google.visualization.DataTable();
      var numRows = perCountryTimeState.yearto - perCountryTimeState.yearfrom + 1;
      datatable.addColumn('string', 'Year');
      datatable.addRows(numRows);
      for (var i=0; i<numRows; i++) {
        datatable.setCell(i, 0, (perCountryTimeState.yearfrom + i).toString());
      }

      for(var i=0; i<data.length; i++) {
        datatable.addColumn('number', data[i].name);
        _.each(_.range(perCountryTimeState.yearfrom, perCountryTimeState.yearto+1), function(year, index, list) {
          // Add the value for this year
          datatable.setCell(year - perCountryTimeState.yearfrom, i+1, parseInt(data[i][year]), parseInt(data[i][year]).toString());
        })
      }
      var formatter1 = new google.visualization.NumberFormat({pattern:'###,###'});
      formatter1.format(datatable, 1);
      perCountryTimeState.lineChart.draw(datatable, {height: 300, chartArea:{left:"auto",top:20,width:"80%",height:"80%"}});
    });
  }

  var perCountrySelectHandler = function() {

    // Show selected products in time series chart
    
    var selection = perCountryState.tableChart.getSelection();
    if (selection.length > 0) {
      perCountryTimeState.products = _.map(selection, function(obj){
        return perCountryState.lastDataTable.getRowProperty(obj.row,'product_code')
      });
      updatePerCountryTimeView();
    } else {
      // Show default, or empty?
    }
  }



  /*
   *
   * Public methods and variables of this module
   *
   */

   return {

    onGoogleLoad: function onGoogleLoad() {
      getAllProducts();
      getAllTypes();
      getAllCountries();
      getAllYears();

      updatePerProductView();
      updatePerCountryView();
      updatePerCountryTimeView();
      // 351 = china
      // 667 = tea

      perProductState.tableChart = new google.visualization.Table(document.getElementById('per_product_table_div'));
      perProductState.geoChart = new google.visualization.GeoChart(document.getElementById('per_product_geochart_div'));
      perCountryState.tableChart = new google.visualization.Table(document.getElementById('per_country_table_div'));
      perCountryTimeState.lineChart = new google.visualization.LineChart(document.getElementById('per_country_linechart_div'));

      google.visualization.events.addListener(perProductState.tableChart, 'select', perProductSelectHandler);
      google.visualization.events.addListener(perCountryState.tableChart, 'select', perCountrySelectHandler);
    },

    perProductSwitchProduct: function perProductSwitchProduct(value) {
      perProductState.productCode = value;
      updatePerProductView();
    },

    perProductSwitchType: function perProductSwitchType(value) {
      perProductState.typeID = value;
      // Get the name of the type. It is a problem that the data
      // result might be returned earlier than the list of types (and names)
      var currentType = $.grep(allTypes, function(obj) {return obj.type_id==value});
      currentType = (currentType.length>0) ? currentType[0] : {type_id: -1, name:'unknown', unit:'unknown'};
      perProductState.typeDescription = currentType.name + ' (' + currentType.unit + ')';
      updatePerProductView();
    },

    perProductSwitchYear: function perProductSwitchYear(value) {
      perProductState.year = value;
      updatePerProductView();
    },

    perCountrySwitchCountry: function perCountrySwitchCountry(value) {
      perCountryState.countryCode = value;
      updatePerCountryView();
    },

    perCountrySwitchType: function perCountrySwitchType(value) {
      perCountryState.typeID = value;
      var currentType = $.grep(allTypes, function(obj) {return obj.type_id==value});
      currentType = (currentType.length>0) ? currentType[0] : {type_id: -1, name:'unknown', unit:'unknown'};
      perProductState.typeDescription = currentType.name + ' (' + currentType.unit + ')';
      updatePerCountryView();
    },

    perCountrySwitchYear: function perProductSwitchYear(value) {
      perCountryState.year = value;
      updatePerCountryView();
    },

    perCountrySwitchYearFrom: function perProductSwitchYear(value) {
      perCountryTimeState.yearfrom = parseInt(value);
      updatePerCountryTimeView();
    },

    perCountrySwitchYearTo: function perProductSwitchYear(value) {
      perCountryTimeState.yearto = parseInt(value);
      updatePerCountryTimeView();
    }
  }

})();


/*
 *
 * Initialize Google visualization
 *
 */

google.load('visualization', '1', {packages: ['table']});
google.load('visualization', '1', {packages: ['corechart']});
google.load('visualization', '1', {packages: ['geochart']});

google.setOnLoadCallback(StatsModule.onGoogleLoad);


/*
 *
 * Ajax Loader animation
 *
 */

$(this).ajaxStart( function() {
  $("#ajaxloading").show();
});

$(this).ajaxStop(function()
{
  $("#ajaxloading").hide();
});
