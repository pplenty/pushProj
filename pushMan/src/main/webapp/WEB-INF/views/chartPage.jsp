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
	
	
	<!-- jQuery UI -->
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
	<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>	
	
	
	<!-- 하이 차트 -->
	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script>
	
	<!-- 차트 데이터 요청 JS -->
	<script src="./js/pushChartTheme.js"></script>
	<script src="./js/pushChart.js"></script>


</head>

<body>
	<!-- 헤더 -->
	<jsp:include page="./reportMenu.jsp"></jsp:include>

    <div class="container-fluid">
		<div class="row">
			<!-- 사이드바 -->
	      	<jsp:include page="./reportSideBar.jsp"></jsp:include>
       		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            	<h2 class="sub-header"> 통계 보기 </h2>
            	<div>
            	<!-- 조건 선택 -->
            	<select id="selectCond">
				    <option value="all" selected="selected">전 체</option>
				    <option value="today">오 늘</option>
				    <option value="week">최근 1주일</option>
				    <option value="month">최근 한 달</option>
				    <option value="custom">직접 입력</option>
				</select>
				
				 
				<!-- 날짜선택 -->
				<br><br>
				<label for="fromDate"> From</label>
				<input type="text" id="fromDate" name="fromDate"
					   style="width: 100px; text-align: center;" readonly>
				<label for="toDate"> To</label>
				<input type="text" id="toDate" name="toDate"
					   style="width: 100px; text-align: center;" readonly>
				<input id="datePickBtn" type="button" value="차트 보기"
				       class="btn btn-default btn-sm"/>
				<br><br>
				</div>	
						
				<div id="container" style="min-width: 620px; height: 400px; max-width: 1150px; margin: 0 auto"></div>
			</div>
		</div>
	</div>
</body>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script> -->
    <script src="./js/report/bootstrap.min.js"></script>
    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->
    <script src="./js/report/holder.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="./js/report/ie10-viewport-bug-workaround.js"></script>
</html>