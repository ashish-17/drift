$(function() {

  var seriesOptions = [], seriesCounter = 0, names = [ '00 Agent',
      '007 Legends', '1 BC' ];

  function createChart() {

    $('#chart')
        .highcharts(
            'StockChart',
            {
              rangeSelector : {
                selected : 4
              },
              yAxis : {
                labels : {
                  formatter : function() {
                    return (this.value > 0 ? ' + ' : '') + this.value + '%';
                  }
                },
                plotLines : [ {
                  value : 0,
                  width : 2,
                  color : 'silver'
                } ]
              },
              plotOptions : {
                series : {
                  compare : 'percent'
                }
              },

              tooltip : {
                pointFormat : '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>',
                valueDecimals : 2
              },
              series : seriesOptions
            });
  }

  $.each(names, function(i, name) {
    var url = path + '/pagecount/' + name;
    $.getJSON(url, function(result) {
      var list = [];
      for(var j in result) {
        list.push(result[j].viewCount);
      }
      seriesOptions[i] = {
        name : name,
        data : list
      };
      seriesCounter += 1;
      if (seriesCounter === names.length) {
        createChart();
      }
    });
  });

});