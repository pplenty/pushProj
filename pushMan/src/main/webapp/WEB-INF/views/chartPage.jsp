<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>통계 보기</title>

	<!-- Bootstrap core CSS -->
	<link href="./css/report/bootstrap.min.css" rel="stylesheet">
	
	<!-- Custom styles for this template -->
	<link href="./css/report/dashboard.css" rel="stylesheet">
	
	<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
	<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
	<script src="./js/report/ie-emulation-modes-warning.js"></script>
	
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
	
	<!-- 파이 차트 -->
	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script>
	
	<script src="./js/pushChart.js"></script>


</head>

<body class="jui">
	<!-- 헤더 -->
	<jsp:include page="./reportMenu.jsp"></jsp:include>

    <div class="container-fluid">
		<div class="row">
			<!-- 사이드바 -->
	      	<jsp:include page="./reportSideBar.jsp"></jsp:include>
       		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          		<h2 class="sub-header"> 통계 보기 </h2>
          		<!-- <select>
          			<option value="1">전체</option>
         		</select>
          		<input type="date"/><input type="date"/> -->
	          	<br>
				<div id="container" style="min-width: 310px; height: 600px; max-width: 1150px; margin: 0 auto"></div>
			</div>
		</div>
	</div>
	
</body>
</html>