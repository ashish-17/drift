$(function() {
  
  var options = {
    chart: {
      type: 'column',
      renderTo: 'chart'
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
    plotOptions: {
      marker: {
        radius: 0
      }
    },
    series: []
  };
  
  var chart = new Highcharts.Chart(options);

  var types = {
    country: {
      titles: [ 'China', 'India', 'Indonesia', 'Brazil',
      'Pakistan', 'Nigeria', 'Bangladesh', 'Russia', 'Japan', 'Mexico',
      'Philippines', 'Ethiopia', 'Vietnam', 'Egypt', 'Turkey', 'Germany',
      'Iran', 'Thailand'],
      type: 'spline'
    },
    state: {
      titles: ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California',
    'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii',
    'Idaho', 'Illinois Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana',
    'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota',
    'Mississippi', 'Missouri', 'Montana Nebraska', 'Nevada', 'New Hampshire',
    'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota',
    'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania Rhode Island', 'South Carolina', 
    'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont',
    'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'],
      type: 'area'
    },
    year: {
      titles: function(begin, end) {
        var titles = [];
        for(var i=begin;i<=end;i++) titles.push(i);
        return titles;
      }(1950, 2016),
      type: 'areaspline'
    }
  };

  $('#select-search').select2({
    ajax: {
      url: '/pagerank/titles/search',
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
    minimumInputLength: 0
  });
  
  $('.btn-search-default').click(function() {
    var type = $(this).attr('title');
    type = types[type];
    reset(type.titles, type.type);
  });
  
  $('#btn-search').click(function() {
    var titles = [];
    $('li.select2-selection__choice').each(function() {
      titles.push($(this).attr('title'));
    });
    reset(titles.join(','));
  });
  
  function reset(titles, type) {
    var url = '/pagerank/' + titles;
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
      options.series = seriesOptions;
      options.chart.type = type || 'column';
      chart = new Highcharts.Chart(options);
    });
  }
  
});
