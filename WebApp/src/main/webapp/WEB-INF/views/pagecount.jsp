<jsp:include page="header.jsp" flush="true" />
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<div class="jumbotron">
  <div class="container">
    <h2>Wikipedia Page Count Statistics</h2>
    <select id="select-search" class="search" multiple="multiple"></select>
    <div class="btn-group">
      <button id="btn-search" type="button" class="btn btn-primary btn-item">Search</button>
    </div>
    <p style="font-size:14px;">Add ";" to the end of keyword for accurate search!</p>
  </div>
</div>

<div class="container">
  <div class="row">
    <div class="col-md-6">
      <div class="panel panel-default">
        <div class="panel-heading">Page Count Statistics Charts</div>
        <div class="panel-body">
          <div style="height:20px;"></div>
          <div id="chart1" class="chart"></div>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <div class="panel panel-default">
        <form class="form-horizontal" style="margin:15px;">
          <div class="form-group">
          <span class="col-md-4" style="margin-top:4px;">Select One Title:</span>
          <div class="col-md-8">
          <select id="select-title" class="form-control">
            <option>--SELECT--</option>
          </select>
          </div>
          </div>
        </form>
        <div class="panel-body">
          <div id="chart2" class="chart"></div>
        </div>
      </div>
  </div>
</div><!-- /.container -->
<script src="js/pagecount.js"></script>

<jsp:include page="footer.jsp" flush="true" />
