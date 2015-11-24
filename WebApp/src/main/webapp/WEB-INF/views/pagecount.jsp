<jsp:include page="header.jsp" flush="true" />

<div class="container">
	<div class="jumbotron">
		<h3>Wikipedia Page Count Statistics</h3>
		<input id="search-input" list="search-items" placeholder="Search..." onkeydown="refreshList()"/>
		<datalist id="search-items">
		</datalist>

		<button id="btn-search" class="btn btn-default">Search</button>
	</div>
	<div class="row">
		<div class="col-md-9">
			<div class="panel panel-default">
				<div class="panel-heading">Page Count Statistics Charts</div>
				<div class="panel-body">
					<div id="chart" style="height: 500px;"></div>
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
</div>
<!-- /.container -->
<jsp:include page="footer.jsp" flush="true" />
