$(function() {

  var options1 = {
    chart : {
      type : 'column',
      renderTo : 'chart1'
    },
    title : {
      text : 'Wikipedia Page View Statistics'
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
    plotOptions : {
      marker : {
        radius : 0
      }
    },
    series : []
  };

  var options2 = {
    chart : {
      renderTo : 'chart2',
      zoomType : 'xy'
    },
    title : {
      text : ''
    },
    xAxis : [ {
      categories : [],
      crosshair : true
    } ],
    yAxis : [ { // Primary yAxis
      labels : {
        format : '{value}',
        style : {
          color : Highcharts.getOptions().colors[2]
        }
      },
      title : {
        text : 'Page Count',
        style : {
          color : Highcharts.getOptions().colors[2]
        }
      }
    }, { // Secondary yAxis
      labels : {
        format : '{value}',
        style : {
          color : Highcharts.getOptions().colors[0]
        }
      },
      title : {
        text : 'Page Trend',
        style : {
          color : Highcharts.getOptions().colors[0]
        }
      },
      opposite : true
    } ],
    tooltip : {
      shared : true
    },
    series : []
  };

  var chart1 = new Highcharts.Chart(options1);
  var chart2 = new Highcharts.Chart(options2);
  var chartdata = {};

  $('#select-search').select2({
    ajax : {
      url : 'pagecount/titles/search',
      dataType : 'json',
      delay : 200,
      data : function(params) {
        return {
          prefix : params.term
        };
      },
      processResults : function(data, params) {
        return {
          results : data
        }
      },
      cache : true
    },
    escapeMarkup : function(markup) {
      return markup
    },
    minimumInputLength : 0
  });

  $('#btn-search').click(function() {
    var titles = [];
    $('li.select2-selection__choice').each(function() {
      titles.push($(this).attr('title'));
    });
    reset(titles.join(',,'));
  });

  $('#select-title').change(function() {
    var title = $(this).val();
    if (title == '--SELECT--') {
      return;
    }
    options2.title.text = 'Page Trend for "' + title + '"';
    options2.xAxis[0].categories = chartdata[title]['date'];
    options2.series = [ {
      name : 'Page Count',
      type : 'spline',
      data : chartdata[title]['viewCount'],

    }, {
      name : 'Page Trend',
      type : 'column',
      yAxis : 1,
      data : chartdata[title]['trend'],
    } ];
    console.log(JSON.stringify(options2));
    chart2 = new Highcharts.Chart(options2);
  });

  function reset(titles, type) {
    var url = 'pagecount/trends/' + titles;
    $.getJSON(url, function(result) {
      var seriesOptions = [];
      chartdata = {};
      var select = $('#select-title').html('<option>--SELECT--</option>');
      for ( var title in result) {
        select.append('<option>' + title + '</option>');
        chartdata[title] = {
          'date' : [],
          'viewCount' : [],
          'trend' : []
        };
        var seriesdata = [];
        for ( var i in result[title]['pageData']) {
          var date = result[title]['pageData'][i]['date'];
          var viewCount = parseInt(result[title]['pageData'][i]['viewCount']);
          var trend = parseFloat(result[title]['pageData'][i]['trend']);
          chartdata[title]['date'].push(date);
          chartdata[title]['viewCount'].push(viewCount);
          chartdata[title]['trend'].push(trend);
          seriesdata.push([ date, viewCount ]);
        }
        seriesOptions.push({
          name : title,
          data : seriesdata
        });
      }
      options1.series = seriesOptions;
      options1.chart.type = type || 'spline';
      chart1 = new Highcharts.Chart(options1);
    });
  }

});
