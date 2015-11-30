$(function() {
  
  loadData(10, createChart);
  
  function createChart(seriesOptions) {
    $('#chart').highcharts({
      chart: {
        type: 'column'
      },
      title: {
        text: 'Wikipedia PageRank Statistics'
      },
      subtitle: {
        text: 'Source: <a href="https://dumps.wikipedia.org/enwiki/latest">Wikipedia Dumps</a>'
      },
      xAxis: {
        type: 'category',
        labels: {
          rotation: -45
        }
      },
      yAxis: {
        min: 0,
        title: {
          text: 'PageRank Index'
        }
      },
      series: seriesOptions
    });
  }
  
  function resetSeries(seriesOptions) {
    var chart = $('#chart').highcharts();
    while(chart.series.length > 0) {
      chart.series[0].remove(true);
    }
    for(var i in seriesOptions) {
      chart.addSeries(seriesOptions[i]);
    }
    chart.redraw();
  }
  
  function loadData(top, callback) {
    var url = path + '/pagerank/top/' + top;
    $.getJSON(url, function(result) {
      var seriesOptions = [];
      var data = [];
      for ( var i in result) {
        data.push([result[i]['pageTitle'],parseFloat(result[i]['pageRank'])]);
      }
      seriesOptions.push({
        name : 'Wikipedia Titles',
        data : data
      });
      console.log(data);
      callback(seriesOptions);
    });
  }
  
});