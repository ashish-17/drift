$(function() {

  createChart([]);

  function createChart(seriesOptions) {
    $('#chart').highcharts({
      title : 'Wikipedia Page Count Statistics',
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
    while (chart.series.length > 0) {
      chart.series[0].remove(true);
    }
    for ( var i in seriesOptions) {
      chart.addSeries(seriesOptions[i]);
    }
    chart.redraw();
  }

  function loadData(titles, callback) {
    var url = '/pagecount/' + titles.join(',');
    $.getJSON(url, function(result) {
      var seriesOptions = [];
      for ( var title in result) {
        var data = [];
        for ( var i in result[title]['pageData']) {
          data.push([ result[title]['pageData'][i]['date'],
              parseInt(result[title]['pageData'][i]['viewCount']) ]);
        }
        seriesOptions.push({
          name : title,
          data : data
        });
      }
      callback(seriesOptions);
    });
  }

  $('#btn-search').click(function() {
    var searchInput = $('#search-input');
    var titles = [];
    titles.push(searchInput.val());
    if (titles.length > 0) {
      loadData(titles, resetSeries);
    }

    /*
     * var titles = []; $('li.search-choice span').each(function() {
     * titles.push($(this).text()); }); if(titles.length > 0) { loadData(titles,
     * resetSeries); }
     */
  });

  function refreshList() {
    var searchInput = $('#search-input');
    var prefix = searchInput.val();
    if (prefix.length > 2) {
      $.getJSON(path + '/pagecount/titles/' + prefix, function(result) {
        var searchItems = $('#search-items');
        searchItems.empty();
        for ( var i in result) {
          searchItems.append($('<option></option>').attr('value', result[i])
              .text(result[i]));
        }
        searchItems.chosen({
          allow_single_deselect : true
        });
      });
    }
  }

});
