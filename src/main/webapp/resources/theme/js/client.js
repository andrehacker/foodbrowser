/*
 * All data are taken from FAOSTAT (faostat.fao.org), the Food and Agriculture Organization of the United Nations.
 * 
 * D3 is more flexible:
 * - http://bl.ocks.org/mbostock/4060606
 * - http://bl.ocks.org/jasondavies/4188334
 *
 * FaoData has a REST API, which is however not used in their new interface
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

  var PER_ITEM_TABLE_SIZE = 20;
  var PER_COUNTRY_TABLE_SIZE = 20;

  var INITIAL_ITEM_ID = 667;    // 667 = tea
  var INITIAL_COUNTRY_ID = 351;    // 351 = china
  var INITIAL_YEAR       = 2011;
  var INITIAL_YEAR_FROM  = 2000;
  var INITIAL_YEAR_TO    = 2011;
  var INITIAL_TYPE_ID    = 5510;   // 5510 = production
  var INITIAL_MEASURE_DESCRIPTION = 'Production (tonnes)';

  var perItemState = {
    // current filter
    itemID: INITIAL_ITEM_ID,
    measureID: INITIAL_TYPE_ID,
    year: INITIAL_YEAR,
    measureDescription: INITIAL_MEASURE_DESCRIPTION,
    // visualization objects
    tableChart: {},
    geoChart: {},
    lastDataTable: {}
  };

  var perCountryState = {
    // current filter
    countryID: INITIAL_COUNTRY_ID,
    measureID: INITIAL_TYPE_ID,
    year: INITIAL_YEAR,
    measureDescription: INITIAL_MEASURE_DESCRIPTION,
    // visualization objects
    tableChart: {},
    lastDataTable: {}
  };

  var perCountryTimeState = {
    // current filter
    //countryID: INITIAL_COUNTRY_ID,
    //measureID: INITIAL_TYPE_ID,
    //measureDescription: INITIAL_MEASURE_DESCRIPTION,
    yearfrom: INITIAL_YEAR_FROM,
    yearto: INITIAL_YEAR_TO,
    items: undefined,
    // visualization objects
    lineChart: {}
  };

  var allItems;
  var allCountries;
  var allMeasures;

  function findFirstByProperty(list, propertyName, value) {
    return $.grep(list, function(obj) {return obj[propertyName] == value})[0];
  }

  function findItemByID(searchID) {
    return findFirstByProperty(allItems, '_id', searchID);
  }

  function findCountryByID(searchID) {
    return findFirstByProperty(allCountries, '_id', searchID);
  }

  function findMeasureByID(searchID) {
    return findFirstByProperty(allMeasures, '_id', searchID);
  }


  /*
   *
   * Initialize items and countries
   *
   */

  var getAllItems = function() {
    $.getJSON('/items', function (data) {
      allItems = data;
      data.sort(function(a, b) { 
        return a.name.localeCompare(b.name);
      });
      var options = $("#per_item_item_select");
      options.find('option').remove();
      for(var i=0; i<data.length; i++) {
        var isSelected = (parseInt(data[i]._id) === parseInt(perItemState.itemID)) ? ' selected ' : '';
        options.append($("<option " + isSelected + "/>").val(data[i]._id).text(data[i].name));
      }
    });
  }

  var getAllMeasures = function() {
    $.getJSON('/measures', function(data) {
      allMeasures = data;
      data.sort(function(a, b) { 
        return a.name.localeCompare(b.name);
      })
      populateMeasures('#per_item_measure_select', perItemState.measureID, data);
      populateMeasures('#per_country_measure_select', perCountryState.measureID, data);
    });
  }

  var populateMeasures = function(controlID, currentmeasureID, data) {
    var options = $(controlID);
    options.find('option').remove();
    for(var i=0; i<data.length; i++) {
      var isSelected = (parseInt(data[i]._id) === parseInt(currentmeasureID)) ? ' selected ' : '';
      options.append($("<option " + isSelected + "/>").val(data[i]._id).text(data[i].name + " (" + data[i].unit + ")"));
    }
  }

  var getAllYears = function() {
    $.getJSON('/years', function(data) {
      data.sort(function(a, b) { 
        return a < b;
      });
      populateYears('#per_item_year_select', perItemState.year, data);
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
      allCountries = data;
      data.sort(function(a, b) { 
        return a.name.localeCompare(b.name);
      });
      var options = $("#per_country_country_select");
      options.find('option').remove();
      for(var i=0; i<data.length; i++) {
        var isSelected = (parseInt(data[i]._id) === parseInt(perCountryState.countryID)) ? ' selected ' : '';
        options.append($("<option " + isSelected + "/>").val(data[i]._id).text(data[i].name));
      }
    });
  }


  /*
   *
   * Per item view
   *
   */

  var updatePerItemView = function() {
    // TODO: Move this into the handler, but be careful not to run the query multiple times
    if (_.isUndefined(allCountries)) {
      // Probably the page was just loaded and the countries were not received yet.
      setTimeout(updatePerItemView, 250);
      return;
    }
    $.getJSON('/peritem?itemid=${item-id}&measureid=${measure-id}&year=${year}'
      .replace('${item-id}',perItemState.itemID)
      .replace('${measure-id}',perItemState.measureID)
      .replace('${year}',perItemState.year), function(data) {

      var datatable = new google.visualization.DataTable();
      datatable.addColumn('string', 'Country');
      datatable.addColumn('number', perItemState.measureDescription);
      datatable.addRows(data.length);
      var country;
      _.each(data, function(fact, index, list){
        country = findCountryByID(fact.country_id);
        datatable.setCell(index, 0, country.name);  // country name
        datatable.setCell(index, 1, parseInt(fact.value), parseInt(fact.value).toString());   // value
      });
      perItemState.lastDataTable = datatable;

      perItemState.geoChart.draw(datatable, optionsGeoChart);
      perItemState.tableChart.draw(datatable, {showRowNumber: true, page: 'enable', pageSize:PER_ITEM_TABLE_SIZE});
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

  var perItemSelectHandler = function() {

    // Show selection in geo chart. Does not work as desired.
    
    // Trick from http://stackoverflow.com/questions/18321025/google-charts-api-set-selection-choose-series-to-highlight
    var enableTrick = true;
    if (enableTrick) {
      var selection = perItemState.tableChart.getSelection();
      perItemState.geoChart.setSelection(selection);
      if (selection.length > 0) {
        var view = new google.visualization.DataView(perItemState.lastDataTable);
        view.setColumns([0, {
          type: 'number',
          label: perItemState.lastDataTable.getColumnLabel(1),
          calc: function (dt, row) {
            return (selection[0].row === row) ? 2 : 1;
          }
        }]);
        perItemState.geoChart.draw(view, optionsGeoChart);
      } else {
        perItemState.geoChart.draw(perItemState.lastDataTable, optionsGeoChart);
      }
    }
  }


  /*
   *
   * Per Country view
   *
   */

  var updatePerCountryView = function() {

    // TODO: Move this into the handler, but be careful not to run the query multiple times
    if (_.isUndefined(allItems)) {
      // Probably the page was just loaded and the items were not received yet.
      setTimeout(updatePerCountryView, 250);
      return;
    }
    $.getJSON('/percountry?countryid=${country-id}&measureid=${measure-id}&year=${year}'
      .replace('${country-id}',perCountryState.countryID)
      .replace('${measure-id}',perCountryState.measureID)
      .replace('${year}',perCountryState.year), function(data) {

      var datatable = new google.visualization.DataTable();
      datatable.addColumn('string', 'Product');
      datatable.addColumn('number', 'Production (tonnes)');
      datatable.addRows(data.length);
      var item;
      _.each(data, function(fact, index, list){
        item = findItemByID(fact.item_id);
        datatable.setCell(index, 0, item.name);   // item name
        datatable.setCell(index, 1, parseInt(fact.value), parseInt(fact.value).toString());  // number and caption
        datatable.setRowProperty(index, 'item_id', fact.item_id)
      });
      perCountryState.lastDataTable = datatable;
      perCountryState.tableChart.draw(datatable, {showRowNumber: true, page: 'enable', pageSize: PER_COUNTRY_TABLE_SIZE});

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

    if (_.isUndefined(perCountryTimeState.items)) return;
    $.getJSON('/percountrytime?countryid=${country-id}&items=${items}&measureid=${measure-id}&yearfrom=${year-from}&yearto=${year-to}'
      .replace('${country-id}',perCountryState.countryID)
      .replace('${measure-id}',perCountryState.measureID)
      .replace('${year-from}',perCountryTimeState.yearfrom)
      .replace('${year-to}',perCountryTimeState.yearto)
      .replace('${items}',perCountryTimeState.items), function(data) {

      var datatable = new google.visualization.DataTable();
      var numRows = perCountryTimeState.yearto - perCountryTimeState.yearfrom + 1;
      datatable.addColumn('string', 'Year');
      datatable.addRows(numRows);
      for (var i=0; i<numRows; i++) {
        datatable.setCell(i, 0, (perCountryTimeState.yearfrom + i).toString());
      }

      _.each(data, function(item, indexItem, listItems) {
        datatable.addColumn('number', findItemByID(item.item_id).name);  // Add column for current item
        _.each(_.range(perCountryTimeState.yearfrom, perCountryTimeState.yearto+1), function(year, index, listYears) {
          // Add the value for this year
          var fact = findFirstByProperty(item.values, 'year', year);
          if (!_.isUndefined(fact)) {
            datatable.setCell(year - perCountryTimeState.yearfrom, indexItem+1, fact.value, fact.value.toString());
          }
        })
      });
      perCountryTimeState.lineChart.draw(datatable, {height: 300, chartArea:{left:"auto",top:20,width:"80%",height:"80%"}});
    });
  }

  var perCountrySelectHandler = function() {

    // Show selected items in time series chart
    
    var selection = perCountryState.tableChart.getSelection();
    if (selection.length > 0) {
      perCountryTimeState.items = _.map(selection, function(obj){
        return perCountryState.lastDataTable.getRowProperty(obj.row,'item_id')
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
      getAllItems();
      getAllMeasures();
      getAllCountries();
      getAllYears();

      updatePerItemView();
      updatePerCountryView();
      //updatePerCountryTimeView();
      // 351 = china
      // 667 = tea

      perItemState.tableChart = new google.visualization.Table(document.getElementById('per_item_table_div'));
      perItemState.geoChart = new google.visualization.GeoChart(document.getElementById('per_item_geochart_div'));
      perCountryState.tableChart = new google.visualization.Table(document.getElementById('per_country_table_div'));
      perCountryTimeState.lineChart = new google.visualization.LineChart(document.getElementById('per_country_linechart_div'));

      google.visualization.events.addListener(perItemState.tableChart, 'select', perItemSelectHandler);
      google.visualization.events.addListener(perCountryState.tableChart, 'select', perCountrySelectHandler);
    },

    perItemSwitchItem: function perItemSwitchItem(value) {
      perItemState.itemID = value;
      updatePerItemView();
    },

    perItemSwitchMeasure: function perItemSwitchMeasure(value) {
      perItemState.measureID = value;
      // Get the name of the measure. It is a problem that the data
      // result might be returned earlier than the list of measures (and names)
      var currentMeasure = findMeasureByID(value);
      if (_.isUndefined(currentMeasure)) {
        currentMeasure = {_id: -1, name:'unknown', unit:'unknown'};
      }
      perItemState.measureDescription = currentMeasure.name + ' (' + currentMeasure.unit + ')';
      updatePerItemView();
    },

    perItemSwitchYear: function perItemSwitchYear(value) {
      perItemState.year = value;
      updatePerItemView();
    },

    perCountrySwitchCountry: function perCountrySwitchCountry(value) {
      perCountryState.countryID = value;
      updatePerCountryView();
    },

    perCountrySwitchMeasure: function perCountrySwitchMeasure(value) {
      perCountryState.measureID = value;
      var currentMeasure = findMeasureByID(value);
      if (_.isUndefined(currentMeasure)) {
        currentMeasure = {_id: -1, name:'unknown', unit:'unknown'};
      }
      perCountryState.measureDescription = currentMeasure.name + ' (' + currentMeasure.unit + ')';
      updatePerCountryView();
    },

    perCountrySwitchYear: function perItemSwitchYear(value) {
      perCountryState.year = value;
      updatePerCountryView();
    },

    perCountrySwitchYearFrom: function perItemSwitchYear(value) {
      perCountryTimeState.yearfrom = parseInt(value);
      updatePerCountryTimeView();
    },

    perCountrySwitchYearTo: function perItemSwitchYear(value) {
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
