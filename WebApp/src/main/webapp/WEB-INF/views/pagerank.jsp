<jsp:include page="header.jsp" flush="true" />

<div class="jumbotron">
  <div class="container">
    <h2>Interesting Findings about Wikipedia PageRank Index</h2>
    <div class="btn-group">
      <button title="country" type="button" class="btn btn-default btn-item btn-search-default">Country</button>
      <button title="state" type="button" class="btn btn-default btn-item btn-search-default">US State</button>
      <button title="year" type="button" class="btn btn-default btn-item btn-search-default">Year</button>
      <button title="major" type="button" class="btn btn-default btn-item btn-search-default">Major</button>
      <button title="cs" type="button" class="btn btn-default btn-item btn-search-default">CS</button>
      <button title="company" type="button" class="btn btn-default btn-item btn-search-default">Company</button>
    </div>
    <div style="clear:both;margin-top:5px;">
    <select id="select-search" class="search" multiple="multiple"></select>
    <div class="btn-group">
      <button id="btn-search" type="button" class="btn btn-primary btn-item">Search</button>
    </div>
    <p style="font-size:14px;">Add ";" to the end of keyword for accurate search!</p>
    </div>
  </div>
</div>

<div class="container">
  <div class="row">
    <div class="col-md-12">
      <div class="panel panel-default">
        <div class="panel-heading">PageRank Index Charts</div>
        <div class="panel-body">
          <div id="chart" class="chart"></div>
        </div>
      </div>
    </div>
  </div>
</div><!-- /.container -->
<script src="js/pagerank.js"></script>

<jsp:include page="footer.jsp" flush="true" />
