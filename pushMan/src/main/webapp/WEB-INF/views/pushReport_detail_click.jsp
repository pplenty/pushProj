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


</head>

<body class="jui">
	<div class="container-fluid">
		<div class="row">


			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="table-responsive">
					<h4 class="sub-header">메시지 링크 수 [ ${cntMsgLink}개 ]</h4>
					<table id="table_1" class="table classic">
						<thead>
							<tr>
								<th style="width: 10%; text-align: center;">번호</th>
								<th style="width: 70%; text-align: center;">링크</th>
								<th style="width: 10%; text-align: center;">횟수</th>
								<th style="width: 10%; text-align: center;">타입</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${list == '[]'}">
								<tr>
									<td colspan=4 style="text-align: center;">데이터가 없습니다.</td>
								</tr>
							</c:if>
							<c:forEach items="${msgLinkList}" var="msgLink">
								<tr>
									<!-- 유저 핸드폰 번호 -->
									<td style="text-align: center;">${msgLink.link_seq}</td>
									<!-- 발송 시간 -->
									<td style="text-align: left;">${msgLink.link}</td>
									<!-- 발송 시간 -->
									<td style="text-align: center;">${msgLink.click_cnt}</td>
									<!-- 발송 시간 -->
									<td style="text-align: center;">${msgLink.msg_push_type}</td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
					
					<h4 class="sub-header">팝업 링크 수 [ ${cntpopupLink}개 ]</h4>
					<table id="table_1" class="table classic">
						<thead>
							<tr>
								<th style="width: 10%; text-align: center;">번호</th>
								<th style="width: 70%; text-align: center;">링크</th>
								<th style="width: 10%; text-align: center;">횟수</th>
								<th style="width: 10%; text-align: center;">타입</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${list == '[]'}">
								<tr>
									<td colspan=4 style="text-align: center;">데이터가 없습니다.</td>
								</tr>
							</c:if>
							<c:forEach items="${popupLinkList}" var="popupLink">
								<tr>
									<!-- 유저 핸드폰 번호 -->
									<td style="text-align: center;">${popupLink.link_seq}</td>
									<!-- 발송 시간 -->
									<td style="text-align: left;">${popupLink.link}</td>
									<!-- 발송 시간 -->
									<td style="text-align: center;">${popupLink.click_cnt}</td>
									<!-- 발송 시간 -->
									<td style="text-align: center;">${popupLink.msg_push_type}</td>
								</tr>
							</c:forEach>

						</tbody>
					</table>

					<div>
						<div class="row" align="right"
							style="text-align: center; margin-top: 3px;">
							<div class="group">
								<c:choose>
									<c:when test="${pageNo > 1}">
										<button
											onclick="location.href='listDetail.do?pageNo=${pageNo-1}&pageSize=${pageSize}&word=${param.word}&order=${param.order}&cno=${cno}'"
											class="btn mini">Prev</button>
									</c:when>
									<c:otherwise>
										<button class="btn mini" disabled="disabled">Prev</button>
									</c:otherwise>
								</c:choose>
								${pageNo} &nbsp
								<c:choose>
									<c:when test="${pageNo < maxPage}">
										<button
											onclick="location.href='listDetail.do?pageNo=${pageNo+1}&pageSize=${pageSize}&word=${param.word}&order=${param.order}&cno=${cno}'"
											class="btn mini">Next</button>
									</c:when>
									<c:otherwise>
										<button class="btn mini" disabled="disabled">Next</button>
									</c:otherwise>
								</c:choose>
							</div>
						</div>


					</div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>
