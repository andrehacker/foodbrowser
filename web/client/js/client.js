/*
 * All data are taken from FAOSTAT (faostat.fao.org), the Food and Agriculture Organization of the United Nations.
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

  var INITIAL_PRODUCT_ID = 667;    // 667 = tea
  var INITIAL_COUNTRY_ID = 351;    // 351 = china
  var INITIAL_TYPE_ID    = 5510;   // 5510 = production
  var INITIAL_TYPE_DESCRIPTION = 'Production (tonnes)';

  var currentProductCode     = INITIAL_PRODUCT_ID;
  var currentTypeID          = INITIAL_TYPE_ID;
  var currentTypeDescription = INITIAL_TYPE_DESCRIPTION;
  var currentCountryCode     = INITIAL_COUNTRY_ID;

  var allTypes;

  // Google visualization objects, will be initialized later
  var perProductTable;
  var perProductGeoChart;
  var perCountryTable;

  var lastPerProductDataTable;
  var lastPerCountryDataTable;


  /*
   *
   * Initialize Google visualization
   *
   */

  google.load('visualization', '1', {packages: ['table']});
  google.load('visualization', '1', {packages: ['corechart']});
  google.load('visualization', '1', {packages: ['geochart']});

  google.setOnLoadCallback(onGoogleLoad);

  function onGoogleLoad() {
    getAllProducts();
    getAllTypes();
    getAllCountries();
    updatePerProductView(INITIAL_PRODUCT_ID, INITIAL_TYPE_ID, INITIAL_TYPE_DESCRIPTION);
    updatePerCountryView(INITIAL_COUNTRY_ID);
    // 351 = china
    // 667 = tea

    perProductTable = new google.visualization.Table(document.getElementById('per_product_table_div'));
    google.visualization.events.addListener(perProductTable, 'select', perProductSelectHandler);
    perCountryTable = new google.visualization.Table(document.getElementById('per_country_table_div'));
    perProductGeoChart = new google.visualization.GeoChart(document.getElementById('map_div'));
  }


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
      var options = $("#product-select");
      options.find('option').remove();
      for(var i=0; i<data.length; i++) {
        var isSelected = (parseInt(data[i].product_code) === parseInt(currentProductCode)) ? ' selected ' : '';
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
      var options = $("#type-select");
      options.find('option').remove();
      for(var i=0; i<data.length; i++) {
        var isSelected = (parseInt(data[i].type_id) === parseInt(currentTypeID)) ? ' selected ' : '';
        options.append($("<option " + isSelected + "/>").val(data[i].type_id).text(data[i].name + " (" + data[i].unit + ")"));
      }
    });
  }

  var getAllCountries = function() {
    $.getJSON('/countries', function(data) {
      data.sort(function(a, b) { 
        return a.country_name.localeCompare(b.country_name);
      });
      var options = $("#country-select");
      options.find('option').remove();
      for(var i=0; i<data.length; i++) {
        var isSelected = (parseInt(data[i].country_code) === parseInt(currentCountryCode)) ? ' selected ' : '';
        options.append($("<option " + isSelected + "/>").val(data[i].country_code).text(data[i].country_name));
      }
    });
  }


  /*
   *
   * Per Product view
   *
   */

  var updatePerProductView = function(productID, typeID, typeDescription) {
    $.getJSON('/perproduct?productid=${productid}&typeid=${typeid}&year=2011'
      .replace('${productid}',productID)
      .replace('${typeid}',typeID), function(data) {
  //    alert(JSON.stringify(data))

      var datatable = new google.visualization.DataTable();
      datatable.addColumn('string', 'Country');
      datatable.addColumn('number', typeDescription);
      datatable.addRows(data.length);
      for(var i=0; i<data.length; i++) {
        datatable.setCell(i, 0, data[i].name);
        datatable.setCell(i, 1, parseFloat(data[i].summ), parseFloat(data[i].summ).toString());
      }
      lastPerProductDataTable = datatable;

      perProductGeoChart.draw(datatable, optionsGeoChart);
      perProductTable.draw(datatable, {showRowNumber: true, page: 'enable', pageSize:PER_PRODUCT_TABLE_SIZE});
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
    var enabledTrick = true;
    if (enabledTrick) {
      var selection = perProductTable.getSelection();
      perProductGeoChart.setSelection(selection);
      var selection = perProductTable.getSelection();
      if (selection.length > 0) {
        var view = new google.visualization.DataView(lastPerProductDataTable);
        view.setColumns([0, {
          type: 'number',
          label: lastPerProductDataTable.getColumnLabel(1),
          calc: function (dt, row) {
            return (selection[0].row === row) ? 2 : 1;
          }
        }]);
        perProductGeoChart.draw(view, optionsGeoChart);
      } else {
        perProductGeoChart.draw(lastPerProductDataTable, optionsGeoChart);
      }
    }
  }


  /*
   *
   * Per Country view
   *
   */

  var updatePerCountryView = function(countryID) {
    $.getJSON('/percountry?countryid=$country-id'.replace('$country-id',countryID), function(data) {
  //    alert(JSON.stringify(data))

      var datatable = new google.visualization.DataTable();
      datatable.addColumn('string', 'Product');
      datatable.addColumn('number', 'Production (tonnes)');
      datatable.addRows(data.length);
      for(var i=0; i<data.length; i++) {
        datatable.setCell(i, 0, data[i].name);
        datatable.setCell(i, 1, parseFloat(data[i].production), parseFloat(data[i].production).toString());  // number and caption
      }
      lastPerCountryDataTable = datatable;

      perCountryTable.draw(datatable, {showRowNumber: true, page: 'enable', pageSize:PER_COUNTRY_TABLE_SIZE});
    });
  }

  var barOptions = {
    title: 'Production',
    vAxis: {title: 'Production tonnes',  titleTextStyle: {color: 'red'}}
  };

  // Public methods and variables of this module
  return {

    switchProduct: function switchProduct(value) {
      currentProductCode = value;
      updatePerProductView(value, currentTypeID, currentTypeDescription);
    },

    switchType: function switchType(value) {

      currentTypeID = value;
      // Get the name of the type. It is a problem that the data
      // result might be returned earlier than the list of types (and names)
      var currentType = $.grep(allTypes, function(obj) {return obj.type_id==value});
      currentType = (currentType.length>0) ? currentType[0] : {type_id: -1, name:'unknown', unit:'unknown'};

      updatePerProductView(currentProductCode, value, currentType.name);
    },

    switchCountry: function switchCountry(value) {
      updatePerCountryView(value);
    }
  }

})();



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
