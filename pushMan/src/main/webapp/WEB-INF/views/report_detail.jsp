<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>PUSH MAN</title>
	
	<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
	
	
	
	<!-- Basic style components -->
	<link rel="stylesheet" href="./js/report/jui/dist/ui.min.css" />
	<link rel="stylesheet" href="./js/report/jui/dist/ui-jennifer.min.css" />
	
	<!-- Grid style components -->
	<link rel="stylesheet" href="./js/report/jui-grid/dist/grid.min.css" />
	<link rel="stylesheet" href="./js/report/jui-grid/dist/grid-jennifer.min.css" />
	
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
          <h2 class="sub-header">상세 결과  [ ${countList}건 ] </h2>
		<!-- 검색창 -->
		<form action='listDetail.do?&word' method='get' class="navbar-form navbar-right">
			<input class="form-control" type='text' name='word' value='${param.word}' placeholder="이름 or 번호">
			<input type='text' name='cno' value='${cno}' hidden="hidden">
			<button class="btn btn-default btn-sm">검색</button>
		</form><br>
          <div class="table-responsive">
            <table id="table_1" class="table classic">
              <thead>
                <tr>
                  <th style="text-align: center;">이름</th>
                  <th style="text-align: center;">전화번호</th>
                  <th style="text-align: center;">발송시간</th>
                  <c:choose>
                  		<c:when test="${param.order == 'error'}">
			                <th style="text-align: center;" onclick="location.href='listDetail.do?pageNo=${pageNo}&pageSize=${pageSize}&word=${param.word}&cno=${cno}'">
	          				결과  ▲</th>
        				</c:when>
                  		<c:otherwise>
				            <th style="text-align: center;" onclick="location.href='listDetail.do?pageNo=${pageNo}&pageSize=${pageSize}&word=${param.word}&order=error&cno=${cno}'">
		          			결과  ▼</th>
          				</c:otherwise>
				  </c:choose>
                </tr>
              </thead>
              <tbody>
              	<c:if test="${list == '[]'}">
              		<tr><td colspan=4 style="text-align: center;">데이터가 없습니다.</td></tr>
              	</c:if>
					<c:forEach items="${list}" var="campaignDetail">
						<tr>
							<td style="text-align: center;">${campaignDetail.rcv_name}</td>
							<td style="text-align: center;">${campaignDetail.rcv_mob}</td>
							<td style="text-align: center;">${campaignDetail.send_time}</td>
							
							<c:set var="error" value="${campaignDetail.send_error}" />
							<c:choose>
								<c:when test="${error == '9'}">
									<td style="color: black; text-align: center;">발송 중</td>
								</c:when>
								<c:when test="${error == '0'}">
									<td style="color: blue; text-align: center;">발송 완료</td>
								</c:when>
								<c:when test="${error == '2'}">
									<td style="color: red; text-align: center;">잘못된 전화번호</td>
								</c:when>
								<c:when test="${error == 'p'}">
									<td style="color: red; text-align: center;">번호 형식 오류</td>
								</c:when>
								<c:when test="${error == '1'}">
									<td style="color: red; text-align: center;">시간 초과</td>
								</c:when>
								<c:when test="${error == 'A'}">
									<td style="color: red; text-align: center;">핸드폰 호 처리중</td>
								</c:when>
								<c:when test="${error == 'B'}">
									<td style="color: red; text-align: center;">음영 지역</td>
								</c:when>
								<c:when test="${error == 'C'}">
									<td style="color: red; text-align: center;">전원 꺼짐</td>
								</c:when>
								<c:when test="${error == 'D'}">
									<td style="color: red; text-align: center;">메시지 저장용량 초과</td>
								</c:when>
								<c:when test="${error == 'a'}">
									<td style="color: red; text-align: center;">기타 단말기 문제</td>
								</c:when>
								<c:when test="${error == 'b'}">
									<td style="color: red; text-align: center;">기타 단말기 문제</td>
								</c:when>
								<c:when test="${error == 'c'}">
									<td style="color: red; text-align: center;">착신 거절</td>
								</c:when>
								<c:when test="${error == 'd'}">
									<td style="color: red; text-align: center;">기 타</td>
								</c:when>
								<c:when test="${error == 'e'}">
									<td style="color: red; text-align: center;">이통사 형식오류</td>
								</c:when>
								<c:when test="${error == 's'}">
									<td style="color: red; text-align: center;">메시지 스팸 차단</td>
								</c:when>
								<c:when test="${error == 'n'}">
									<td style="color: red; text-align: center;">수신번호 스팸차단</td>
								</c:when>
								<c:when test="${error == 'r'}">
									<td style="color: red; text-align: center;">회신번호 스팸차단</td>
								</c:when>
								<c:when test="${error == 't'}">
									<td style="color: red; text-align: center;">스팸 차단</td>
								</c:when>
								<c:when test="${error == 'Z'}">
									<td style="color: red; text-align: center;">기타 오류</td>
								</c:when>
								<c:when test="${error == 'f'}">
									<td style="color: red; text-align: center;">자체 형식 오류</td>
								</c:when>
								<c:when test="${error == 'g'}">
									<td style="color: red; text-align: center;">메시지 서비스 불가 단말기</td>
								</c:when>
								<c:when test="${error == 'i'}">
									<td style="color: red; text-align: center;">운영자가 삭제</td>
								</c:when>
								<c:when test="${error == 'k'}">
									<td style="color: red; text-align: center;">이통사 스팸처리</td>
								</c:when>
								<c:when test="${error == 'l'}">
									<td style="color: red; text-align: center;">중계사 스팸처리</td>
								</c:when>
								<c:when test="${error == 'm'}">
									<td style="color: red; text-align: center;">중계사 스팸처리</td>
								</c:when>
								<c:when test="${error == 'n'}">
									<td style="color: red; text-align: center;">건수 제한</td>
								</c:when>
								<c:when test="${error == 'o'}">
									<td style="color: red; text-align: center;">메시지 길이 초과</td>
								</c:when>
								<c:when test="${error == 'Q'}">
									<td style="color: red; text-align: center;">필드 형식 오류</td>
								</c:when>
								<c:when test="${error == 'y'}">
									<td style="color: red; text-align: center;">하루 메시지 수량 초과</td>
								</c:when>
								<c:when test="${error == 'w'}">
									<td style="color: red; text-align: center;">키워드 스팸처리</td>
								</c:when>
								<c:when test="${error == 'l'}">
									<td style="color: red; text-align: center;">중계사 스팸처리</td>
								</c:when>
								<c:otherwise>
									<td style="color: red;">발송 기타 오류</td>
								</c:otherwise>
							</c:choose>
							
						</tr>
					</c:forEach>
                
              </tbody>
            </table>

					<div>
						<div class="row" align="right" style="text-align: center; margin-top: 3px;">
						    <div class="group">
						<c:choose>
							<c:when test="${pageNo > 1}">
								<button onclick="location.href='listDetail.do?pageNo=${pageNo-1}&pageSize=${pageSize}&word=${param.word}&order=${param.order}&cno=${cno}'" class="btn mini">Prev</button>
							</c:when>
							<c:otherwise><button class="btn mini" disabled="disabled">Prev</button></c:otherwise>
						</c:choose>
							${pageNo} &nbsp
						<c:choose>
							<c:when test="${pageNo < maxPage}">
									<button onclick="location.href='listDetail.do?pageNo=${pageNo+1}&pageSize=${pageSize}&word=${param.word}&order=${param.order}&cno=${cno}'" class="btn mini">Next</button>
							</c:when>
							<c:otherwise><button class="btn mini" disabled="disabled">Next</button></c:otherwise>
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
