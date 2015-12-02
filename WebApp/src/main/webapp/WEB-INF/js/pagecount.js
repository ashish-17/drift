$(function() {

  var options = {
    chart: {
      type: 'column',
      renderTo: 'chart'
    },
    title: {
      text: 'Wikipedia Page Count Statistics'
    },
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
    plotOptions: {
      marker: {
        radius: 0
      }
    },
    series: []
  };

  var chart = new Highcharts.Chart(options);

  $('#select-search').select2({
    ajax: {
      url: '/pagecount/titles/search',
      dataType: 'json',
      delay: 200,
      data: function(params) {
        return {
          prefix: params.term
        };
      },
      processResults: function(data, params) {
        return {
          results: data
        }
      },
      cache: true
    },
    escapeMarkup: function(markup) {return markup},
    minimumInputLength: 0
  });
  
  $('#btn-search').click(function() {
    var titles = [];
    $('li.select2-selection__choice').each(function() {
      titles.push($(this).attr('title'));
    });
    reset(titles.join(',,'));
  });
  
  function reset(titles, type) {
    var url = '/pagecount/' + titles;
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
      options.series = seriesOptions;
      options.chart.type = type || 'spline';
      chart = new Highcharts.Chart(options);
    });
  }

});
