$(function() {

  createChart([]);

  function createChart(seriesOptions) {
    $('#chart').highcharts({
      title : 'Test chart',
      xAxis : {
        title : 'Date',
        type : 'category'
      },
      yAxis : {
        title : 'Count(#)',
        plotLines : [ {
          value : 0,
          width : 1,
          color : '#808080'
        } ]
      },
      series : seriesOptions
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

  function loadData(titles, callback) {
    var url = path + '/pagecount/' + titles.join(',');
    $.getJSON(url, function(result) {
      var seriesOptions = [];
      for ( var title in result) {
        var data = [];
        for ( var i in result[title]) {
          data.push([ result[title][i]['date'], parseInt(result[title][i]['viewCount']) ]);
        }
        seriesOptions.push({
          name : title,
          data : data
        });
      }
      callback(seriesOptions);
    });
  }

  $.getJSON(path + '/pagecount/titles', function(result) {
    var item = $('#select-search');
    item.html();
    for ( var i in result) {
      item.append($('<option></option>').attr('value', result[i]).text(
          result[i]));
    }
    item.chosen({
      allow_single_deselect : true
    });
  });

  $('#btn-search').click(function() {
    var titles = [];
    $('li.search-choice span').each(function() {
      titles.push($(this).text());
    });
    if(titles.length > 0) {
      loadData(titles, resetSeries);
    }
  });

});