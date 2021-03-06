<%@ page language="java" contentType="text/html; charset=utf-8"
  pageEncoding="utf-8"%>
<%
    String path = String.format("%s://%s:%d%s", request.getScheme(), request.getServerName(),
          request.getServerPort(), request.getContextPath());
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="<%=path%>/">
<title>Hadoop-Driven Data Analysis System</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- 
<link href="/css/chosen.min.css" rel="stylesheet">
<link href="/css/prism.css" rel="stylesheet">
-->
<link href="css/select2.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-71687033-1', 'auto');
  ga('send', 'pageview');

</script>
<script src="js/lib/jquery.min.js"></script>
<script src="js/lib/bootstrap.min.js"></script>
<script src="js/lib/highcharts.js"></script>
<!--
<script src="/js/lib/highcharts-custom.js"></script>
<script src="/js/lib/d3.min.js"></script>
<script src="/js/lib/chosen.jquery.min.js"></script>
<script src="/js/lib/prism.js"></script>
-->
<script src="js/lib/select2.full.min.js"></script>
</head>
<body>
  <nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-header">
      <a class="navbar-brand">Hadoop-Driven Analysis</a>
    </div>
    <div class="container">
      <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
          <li>
            <a href="pagecount/"> 
              <span class="glyphicon glyphicon-home" aria-hidden="true"></span> 
              <span>&nbsp;PageCount</span>
            </a>
          </li>
          <li>
            <a href="pagerank/"> 
              <span class="glyphicon glyphicon-signal" aria-hidden="true"></span> 
              <span>&nbsp;PageRank</span>
            </a>
          </li>
        </ul>
      </div>
    </div><!-- /.container -->
  </nav>
