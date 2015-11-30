<jsp:include page="header.jsp" flush="true" />

<div class="jumbotron">
  <div class="container">
    <h3>Wikipedia PageRank</h3>
    <input id="search-input" list="search-items" placeholder="Search..."
      onkeydown="refreshList()" />
    <datalist id="search-items"></datalist>
    <button id="btn-search" class="btn btn-default">Search</button>
  </div>
</div>

<div class="container">
  <div class="row">
    <div class="col-md-9">
      <div class="panel panel-default">
        <div class="panel-heading">PageRank Index Charts</div>
        <div class="panel-body">
          <div id="chart" class="chart"></div>
        </div>
      </div>
    </div>
  </div>
</div><!-- /.container -->
<script src="/js/pagerank.js"></script>

<jsp:include page="footer.jsp" flush="true" />
