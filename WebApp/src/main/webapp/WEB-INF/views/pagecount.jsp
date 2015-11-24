<jsp:include page="header.jsp" flush="true" />

<div class="jumbotron">
 <h2>Wikipedia Page Count Statistics</h2>
</div>

<div class="row">
 <div class="col-md-9">
  <div class="panel panel-default">
   <div class="panel-heading">Page Count Statistics Charts</div>
   <div class="panel-body">
    <div id="chart"></div>
   </div>
  </div>
 </div>
 <div class="col-md-3">
  <div class="panel panel-primary">
   <div class="panel-heading">Weekly Trends</div>
   <div class="panel-body"></div>
  </div>
 </div>
</div>

<script src="/js/sample.js"></script>
<script src="/js/pagecount.js"></script>
<jsp:include page="footer.jsp" flush="true" />