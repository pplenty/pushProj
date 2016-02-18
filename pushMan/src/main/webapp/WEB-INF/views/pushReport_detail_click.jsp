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

<title>SMS Sender</title>

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
				<h2 class="sub-header">상세 결과 [ ${countList}건 ]</h2>
				<!-- 검색창 -->
				<%-- <form action='pushListDetail.do' method='get' class="navbar-form navbar-right">
			<input class="form-control" type='text' name='word' value='${param.word}' placeholder="이름 or 번호">
			<input type='text' name='cno' value='${cno}' hidden="hidden">
			<button class="btn btn-default btn-sm">검색</button>
		</form><br> --%>
				<div class="table-responsive">
					<table id="table_1" class="table classic">
						<thead>
							<tr>
								<th style="width: 20%; text-align: center;">받는 사람</th>
								<th style="width: 20%; text-align: center;">발송 시간</th>
								<th style="width: 30%; text-align: center;">발송 성공/실패/오류</th>
								<th style="width: 15%; text-align: center;">오픈</th>
								<th style="width: 15%; text-align: center;">클릭</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${list == '[]'}">
								<tr>
									<td colspan=4 style="text-align: center;">데이터가 없습니다.</td>
								</tr>
							</c:if>
							<c:forEach items="${list}" var="pushCampaignDetail">
								<tr>
									<!-- 유저 핸드폰 번호 -->
									<td style="text-align: center;">${pushCampaignDetail.mobile}</td>
									<!-- 발송 시간 -->
									<td style="text-align: center;">${pushCampaignDetail.reg_date}</td>
									<!-- 수신 성공 여부 -->
									<c:set var="resultCode" value="${pushCampaignDetail.res_cd}" />
									<c:choose>
										<c:when test="${resultCode eq '1000'}">
											<td style="color: blue; text-align: center;">성공</td>
										</c:when>
										<c:when test="${resultCode eq '1001'}">
											<td style="color: red; text-align: center;">디바이스 중복 에러</td>
										</c:when>
										<c:when test="${resultCode eq '1002'}">
											<td style="color: red; text-align: center;">토큰 형식 오류</td>
										</c:when>
										<c:when test="${resultCode eq '1003'}">
											<td style="color: red; text-align: center;">토큰 정보가 비어 있음</td>
										</c:when>
										<c:when test="${resultCode eq '1004'}">
											<td style="color: red; text-align: center;">아이디 누락 오류</td>
										</c:when>
										<c:when test="${resultCode eq '1005'}">
											<td style="color: red; text-align: center;">알수없는 디바이스 오류</td>
										</c:when>
										<c:when test="${resultCode eq '1006'}">
											<td style="color: red; text-align: center;">통신중 오류</td>
										</c:when>
										<c:when test="${resultCode eq '1007'}">
											<td style="color: red; text-align: center;">Payload 사이즈 초과 오류</td>
										</c:when>
										<c:when test="${resultCode eq '1008'}">
											<td style="color: red; text-align: center;">푸시 내용 누락 오류</td>
										</c:when>
										<c:when test="${resultCode eq '1111'}">
											<td style="color: red; text-align: center;">알수 없는 오류</td>
										</c:when>
										<c:when test="${resultCode eq '2001'}">
											<td style="color: red; text-align: center;">GCM 통신 허용량 초과 오류</td>
										</c:when>
										<c:when test="${resultCode eq '2002'}">
											<td style="color: red; text-align: center;">디바이스 용량 초과 오류</td>
										</c:when>
										<c:when test="${resultCode eq '2003'}">
											<td style="color: red; text-align: center;">잘못된 푸시 토큰 정보 오류</td>
										</c:when>
										<c:when test="${resultCode eq '2004'}">
											<td style="color: red; text-align: center;">유효하지 않은 토큰 정보 오류</td>
										</c:when>
										<c:when test="${resultCode eq '2005'}">
											<td style="color: red; text-align: center;">발신자 아이디 오류</td>
										</c:when>
										<c:when test="${resultCode eq '2006'}">
											<td style="color: red; text-align: center;">등록되지 않은 토큰 오류</td>
										</c:when>
										<c:when test="${resultCode eq '2007'}">
											<td style="color: orange; text-align: center;">메시지 용량 초과</td>
										</c:when>
										<c:when test="${resultCode eq '2008'}">
											<td style="color: red; text-align: center;">MISSING COLLAPSE KEY ERROR</td>
										</c:when>
										<c:when test="${resultCode eq '2009'}">
											<td style="color: red; text-align: center;">일시적인 GCM 서버 접근 오류</td>
										</c:when>
										<c:when test="${resultCode eq '2100'}">
											<td style="color: red; text-align: center;">존재하지 않은 토큰 정보 오류</td>
										</c:when>
										<c:when test="${resultCode eq '4444'}">
											<td style="color: orange; text-align: center;">푸시 수신 거부</td>
										</c:when>
										<c:when test="${resultCode eq '4000'}">
											<td style="color: red; text-align: center;">GCM 기타 오류</td>
										</c:when>
										<c:when test="${resultCode eq '1006'}">
											<td style="color: red; text-align: center;">APNS 푸시 메시지 길이 초과 오류</td>
										</c:when>
										<c:when test="${resultCode eq '3000'}">
											<td style="color: orange; text-align: center;">결과코드 대기중</td>
										</c:when>
										<c:when test="${resultCode eq '3101'}">
											<td style="color: red; text-align: center;">전송 실패(private)</td>
										</c:when>
										<c:when test="${resultCode eq '3102'}">
											<td style="color: orange; text-align: center;">미 접속 사용자(private)</td>
										</c:when>
										<c:when test="${resultCode eq '3103'}">
											<td style="color: orange; text-align: center;">전송 시간 초과(private)</td>
										</c:when>
										<c:when test="${resultCode eq '3104'}">
											<td style="color: orange; text-align: center;">수신거부(private)</td>
										</c:when>
										<c:when test="${resultCode eq '3105'}">
											<td style="color: red; text-align: center;">기타오류(private)</td>
										</c:when>
										<c:when test="${resultCode eq '5000'}">
											<td style="color: red; text-align: center;">등록되지 않은 사용자</td>
										</c:when>
										<c:otherwise>
											<td style="color: red; text-align: center;">Unknown Error</td>
										</c:otherwise>
									</c:choose>
									<!-- 읽음 처리 -->
									<c:set var="read" value="${pushCampaignDetail.read}" />
									<c:choose>
										<c:when test="${read eq 'R'}">
											<td style="color: green; text-align: center;">O</td>
										</c:when>
										<c:otherwise>
											<td style="color: black; text-align: center;">X</td>
										</c:otherwise>
									</c:choose>
									<!-- 클릭 처리 -->
									<c:set var="click" value="${pushCampaignDetail.click}" />
									<c:choose>
										<c:when test="${click eq 'C'}">
											<td style="color: green; text-align: center;">O</td>
										</c:when>
										<c:otherwise>
											<td style="color: black; text-align: center;">X</td>
										</c:otherwise>
									</c:choose>
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
