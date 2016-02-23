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
	
	
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>	
	
	
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
						<select>
						    <option value="1">전체</option>
						</select> 
          	<!-- 날짜선택 -->
						<!-- <input type="date" id="fromDate"/><input type="date" id="toDate"/><br> -->
						<label for="from">From</label>
						<input type="text" id="from" name="from">
						<label for="to">to</label>
						<input type="text" id="to" name="to">
						<input id="datePickBtn" type="button" value="show"/>
						<br> 
						
						
				<div id="container" style="min-width: 310px; height: 600px; max-width: 1150px; margin: 0 auto"></div>
			</div>
		</div>
	</div>

	<script>
		$(function() {
			$("#from").datepicker({
				defaultDate : "+1d",//1d, 1w, 1m, 1y
				changeMonth : true,
				numberOfMonths : 1,
				dateFormat: "yy-mm-dd",
				dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
				gotoCurrent: true,
				onClose : function(selectedDate) {
					$("#to").datepicker("option", "minDate", selectedDate);
				}
			});
			$("#to").datepicker({
				defaultDate : "+2d",
				changeMonth : true,
				numberOfMonths : 1,
				dateFormat: "yy-mm-dd",
		    dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
		    gotoCurrent: true,
				onClose : function(selectedDate) {
					$("#from").datepicker("option", "maxDate", selectedDate);
				}
			});
		});
	</script>
	<script type="text/javascript">
	  $('#datePickBtn').click(function() {
		  console.log($('#from').val());
		  console.log($("#from").datepicker("getDate"));
		// chart에 들어갈 값 ajax로 받아오기
	    $.ajax({
	      url : './pushChart.do',
	      method : 'POST',
	      dataType : 'json',
	      data : {
	        fromDate : $('#from').val(),
	        toDate : $("#to").val()
	      },
	      success : function(result) {
	        // 통계 '읽음' 차트 데이터 준비
	        readList = JSON.parse(result.readCntList);
	        console.log(readList);
	        
	        // 통계 '클릭' 차트 데이터 준비
	        clickList = JSON.parse(result.clickCntList);
	        console.log(clickList);
	        
	        // 00 ~ 23까지 '오픈' 카운트 담는 배열
	        readChartList = toExtendArray24time(readList);
	        // 00 ~ 23까지 '클릭' 카운트 담는 배열
	        clickChartList = toExtendArray24time(clickList);

	        
	        // 적은 시간대(00-06시) 'OPEN' 배열 합침
	        efficReadChartList = toEfficArrayByTime(readChartList);
	        // 적은 시간대(00-06시) 'CLICK' 배열 합침
	        efficClickChartList = toEfficArrayByTime(clickChartList);
	        
	        
	        // 차트를 지우고 다시 그리기 위한 데이터 배열 비우기
	        series = [];
	        
	        
	        // 차트 그리기 위한 Highchart Setting
	        chartDataOne = {};// 객체 초기화
	        chartDataOne.data = efficReadChartList;// 오픈 데이터
	        chartDataOne.name = '오픈';
	        chartDataOne.marker = {symbol: 'square'};
	        series.push(chartDataOne);
	        
	        // 클릭 추가 세팅
	        chartDataOne = {};// 객체 초기화
	        chartDataOne.data = efficClickChartList;// 클릭 데이터
	        chartDataOne.name = '클릭';
	        chartDataOne.marker = {symbol: 'circle'};
	        series.push(chartDataOne);
	        
	        showChart('container', series);
	      },
	      error : function(e) {
	        console.error('ajax 에러: ' + e.status);
	      }
	    });
	  }); 
	
	
	
	</script>
	
	
	
</body>
</html>