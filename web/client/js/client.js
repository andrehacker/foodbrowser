$(document).ready( function() {
  getTeaProduction();
});

function getTeaProduction()
{
  $.getJSON('tea/production', function(data) {
    alert(JSON.stringify(data));
    var transformed = [];
    transformed.push(['Country', 'Production (tonnes)'])
    $.each(JSONObject, function(i, obj) {
      //locations.push([obj.place.value, obj.lat.value, obj.long.value, obj.page.value]);
    });

    alert(JSON.stringify(transformed));

    var chart = new google.visualization.GeoChart(document.getElementById('chart_div_china'));
    chart.draw(data, optionsChina);
  });
}