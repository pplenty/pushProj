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

<title>클릭 통계</title>

<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>

<!-- Basic style components -->
<link rel="stylesheet" href="./js/report/jui/dist/ui.min.css" />
<link rel="stylesheet" href="./js/report/jui/dist/ui-jennifer.min.css" />

<!-- Grid style components -->
<link rel="stylesheet" href="./js/report/jui-grid/dist/grid.min.css" />
<link rel="stylesheet"
	href="./js/report/jui-grid/dist/grid-jennifer.min.css" />

<!-- Required script files JUI -->
<script src="./js/report/jui-core/dist/core.min.js"></script>

<!-- Basic script components -->
<script src="./js/report/jui/dist/ui.min.js"></script>

<!-- Grid script components -->
<script src="./js/report/jui-grid/dist/grid.min.js"></script>


<!-- 파이 차트 -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<script src="./js/plugin/viewTinted.js"></script>
<script src="./js/clickChart.js"></script>


</head>

<body class="jui">
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h2>클릭 상세 보기</h2>
				<ul id="tab_1" class="tab top">
				    <li class="active"><a href="#msgSection">인 앱 메시지</a></li>
				    <li><a href="#popupSection">푸시 팝업</a></li>
				</ul>
<!-- 				<div id="tab_contents_1" style="background: #dcdcdc;">
				    <div id="home">home</div>
				    <div id="css">css</div>
				    <div id="script">script</div>
				</div> -->
				
				<div class="table-responsive" id="msgSection">
					<!-- 파이차트 (앱 내 메시지 링크) -->
					<div id="container" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto" campId="${campId}"></div>
					<br>
					<h4 class="sub-header">메시지 링크 수 [ ${cntMsgLink}개 ]</h4>
					<table id="table_1" class="table classic">
						<thead>
							<tr>
								<th style="width: 10%; text-align: center;">번호</th>
								<th style="width: 80%; text-align: center;">링크</th>
								<th style="width: 10%; text-align: center;">횟수</th>
								<!-- <th style="width: 10%; text-align: center;">타입</th> -->
							</tr>
						</thead>
						<tbody>
							<c:if test="${msgLinkList == '[]'}">
								<tr>
									<td colspan=3 style="text-align: center;">데이터가 없습니다.</td>
								</tr>
							</c:if>
							<c:forEach items="${msgLinkList}" var="msgLink">
								<tr>
									<!-- 링크 번호 -->
									<td style="text-align: center;">${msgLink.link_seq}</td>
									<!-- 링크 주소 -->
									<td style="text-align: left;">${msgLink.link}</td>
<%-- 									<td style="text-align: left;"><a href="${msgLink.link}" target="_blank" viewtinted>${msgLink.link}</a></td> --%>
									<!-- 링크 클릭 횟수 -->
									<td style="text-align: center;">${msgLink.click_cnt}</td>
									<!-- 링크 타입 -->
									<%-- <td style="text-align: center;">${msgLink.msg_push_type}</td> --%>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
				
				<div class="table-responsive" id="popupSection" style="display: none;">
				<!-- 파이차트 (팝업 링크) -->
					<div id="container2" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto" campId="${campId}"></div>
					<br>
					<h4 class="sub-header">팝업 링크 수 [ ${cntpopupLink}개 ]</h4>
					<table id="table_1" class="table classic">
						<thead>
							<tr>
								<th style="width: 10%; text-align: center;">번호</th>
								<th style="width: 70%; text-align: center;">링크</th>
								<th style="width: 10%; text-align: center;">횟수</th>
								<!-- <th style="width: 10%; text-align: center;">타입</th> -->
							</tr>
						</thead>
						<tbody>
							<c:if test="${popupLinkList == '[]'}">
								<tr>
									<td colspan=3 style="text-align: center;">데이터가 없습니다.</td>
								</tr>
							</c:if>
							<c:forEach items="${popupLinkList}" var="popupLink">
								<tr>
									<!-- 링크 시퀀스 -->
									<td style="text-align: center;">${popupLink.link_seq}</td>
									<!-- 푸시 링크 주소 -->
									<td style="text-align: left;">${popupLink.link}</td>
									<!-- 푸시 클릭 카운트 -->
									<td style="text-align: center;">${popupLink.click_cnt}</td>
									<!-- 푸시 타입 -->
									<%-- <td style="text-align: center;">${popupLink.msg_push_type}</td> --%>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
