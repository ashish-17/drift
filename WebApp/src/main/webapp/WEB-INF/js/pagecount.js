$(function() {

  var seriesOptions = [];
  var seriesCounter = 0, names = [ '00 Agent', '007 Legends', '1 BC' ];
  
  function createChart() {
    $('#chart').highcharts({
      title: 'Test chart',
      xAxis: {
        title: 'Date',
        type: 'category'
      },
      yAxis: {
        title: 'Count(#)',
        plotLines: [{
          value: 0,
          width: 1,
          color: '#808080'
        }]
      },
      series: seriesOptions
    });
  }

  $.each(names, function(i, name) {
    var url = path + '/pagecount/' + name;
    $.getJSON(url, function(result) {
      var data = [];
      for (var j in result) {
        data.push([result[j].date, parseInt(result[j].viewCount)]);
      }
      seriesOptions[i] = {
        name: name,
        data: data
      };
      seriesCounter += 1;
      if (seriesCounter === names.length) {
        createChart();
      }
    });
  });

});