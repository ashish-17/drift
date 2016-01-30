$(function() {

  var options = {
    chart : {
      type : 'column',
      renderTo : 'chart'
    },
    title : {
      text : 'Wikipedia PageRank Statistics'
    },
    subtitle : {
      text : 'Source: <a href="https://dumps.wikipedia.org/enwiki/latest">Wikipedia Dumps</a>'
    },
    xAxis : {
      type : 'category',
      labels : {
        rotation : -45
      }
    },
    yAxis : {
      min : 0,
      title : {
        text : 'PageRank Index'
      }
    },
    plotOptions : {
      areaspline : {
        marker : {
          enabled : false
        }
      }
    },
    series : []
  };

  var chart = new Highcharts.Chart(options);

  var types = {
    country : {
      titles : [ 'Australia', 'Austria', 'Belgium', 'Canada', 'China',
          'Czech Republic', 'Denmark', 'Estonia', 'Finland', 'France',
          'Germany', 'Greece', 'Iceland', 'India', 'Ireland', 'Israel',
          'Italy', 'Japan', 'Latvia', 'Lithuania', 'Luxembourg', 'Netherlands',
          'New Zealand', 'Norway', 'Portugal', 'San Marino', 'Singapore',
          'Slovakia', 'Slovenia', 'South Korea', 'Spain', 'Sweden',
          'Switzerland', 'United Kingdom', 'United States' ],
      type : 'areaspline'
    },
    state : {
      titles : [ 'Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California',
          'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia',
          'Hawaii', 'Idaho', 'Illinois Indiana', 'Iowa', 'Kansas', 'Kentucky',
          'Louisiana', 'Maine', 'Maryland', 'Massachusetts', 'Michigan',
          'Minnesota', 'Mississippi', 'Missouri', 'Montana Nebraska', 'Nevada',
          'New Hampshire', 'New Jersey', 'New Mexico', 'New York',
          'North Carolina', 'North Dakota', 'Ohio', 'Oklahoma', 'Oregon',
          'Pennsylvania Rhode Island', 'South Carolina', 'South Dakota',
          'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia', 'Washington',
          'West Virginia', 'Wisconsin', 'Wyoming' ],
      type : 'areaspline'
    },
    year : {
      titles : function(begin, end) {
        var titles = [];
        for (var i = begin; i <= end; i++)
          titles.push(i);
        return titles;
      }(1950, 2016),
      type : 'areaspline'
    },
    major : {
      titles : [ 'Humanities', 'History', 'Linguistics', 'Literature', 'Arts',
          'Philosophy', 'Religion', 'Social sciences', 'Anthropology',
          'Archaeology', 'Area studies', 'Cultural studies', 'Economics',
          'Gender and sexuality studies', 'Geography', 'Political science',
          'Psychology', 'Sociology', 'Natural science', 'Biology', 'Chemistry',
          'Physics', 'Earth science', 'Space science', 'Formal science',
          'Mathematics', 'Applied Mathematics', 'Mathematics',
          'Computer science', 'Logic', 'Statistics', 'Systems science',
          'Professions', 'Agriculture', 'Architecture and design', 'Business',
          'Divinity', 'Education', 'Engineering', 'Environmental studies',
          'Family and consumer science', 'Sports biomechanics',
          'Communication studies', 'Law', 'Library science', 'Medicine',
          'Military science', 'Intelligence', 'Public administration',
          'Public policy', 'Social work', 'Transportation' ],
      type : 'areaspline'
    },
    cs : {
      titles : [ 'Web programming', 'Coding theory', 'Game theory',
          'Graph theory', 'Mathematical logic', 'Number theory', 'Algorithms',
          'Data structures', 'Artificial intelligence', 'Automated reasoning',
          'Computer vision', 'Machine learning', 'Natural language processing',
          'Robotics', 'Networking', 'Computer security', 'Cryptography',
          'Computer architecture', 'Operating systems', 'Computer graphics',
          'Image processing', 'Concurrency', 'Parallel computing',
          'Distributed computing ', 'Relational databases',
          'Structured Storage', 'Data mining', 'Compiler theory',
          'Programming language pragmatics', 'Formal semantics', 'Type theory',
          'Computational science', 'Numerical analysis',
          'Symbolic computation', 'Computational physics',
          'Computational chemistry', 'Bioinformatics',
          'Computational neuroscience', 'Formal methods',
          'Software engineering', 'Human–computer interaction',
          'Reverse engineering', 'Automata theory', 'Computability theory',
          'Computational complexity theory', 'Quantum computing' ],
      type : 'areaspline'
    },
    company : {
      titles : [ 'Uber', 'Airbnb', 'Facebook', 'Google', 'Naftogaz', 'WhatsApp',
          'Yahoo!', 'New York Stock Exchange', 'Instagram', 'Theranos',
          'Elliott Management', 'Square', 'Twitter', 'Pinterest', 'GoPro',
          'White House', 'Snapchat', 'SolarCity', 'Blackstone', 'Severn Trent',
          'Nike', 'Annapurna Pictures', 'Micromax', 'Hampton Creek',
          'Lifeway Foods', 'Louis Vuitton', 'Momo', 'Kabam', 'Snapdeal', 'SBE',
          'Twitter', 'Nasty Gal', 'Libra Group', 'Columbia Pictures',
          'The Trump Organization', 'Ellen MacArthur Foundation' ],
      type : 'areaspline'
    }
  };

  $('#select-search').select2({
    ajax : {
      url : 'pagerank/titles/search',
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

  $('.btn-search-default').click(function() {
    var type = $(this).attr('title');
    type = types[type];
    reset(type.titles.join(',,'), type.type);
  });
$('#btn-search').click(function() { var titles = [];
    $('li.select2-selection__choice').each(function() {
      titles.push($(this).attr('title'));
    });
    reset(titles.join(',,'));
  });

  function reset(titles, type) {
    var url = 'pagerank/' + titles;
    $.getJSON(url,
        function(result) {
          var seriesOptions = [];
          var data = [];
          for ( var i in result) {
            data.push([ result[i]['pageTitle'],
                parseFloat(result[i]['pageRank']) ]);
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

  (function() {
      var type = 'cs';
      type = types[type];
      reset(type.titles.join(',,'), type.type);
  })();
});

