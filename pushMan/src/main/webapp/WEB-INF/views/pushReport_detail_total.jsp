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

    <title>SMS Sender</title>
	
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
		<%-- <form action='pushListDetail.do' method='get' class="navbar-form navbar-right">
			<input class="form-control" type='text' name='word' value='${param.word}' placeholder="이름 or 번호">
			<input type='text' name='cno' value='${cno}' hidden="hidden">
			<button class="btn btn-default btn-sm">검색</button>
		</form><br> --%>
          <div class="table-responsive">
            <table id="table_1" class="table classic">
              <thead>
                <tr>
                  <th style="text-align: center;">User ID</th>
                  <th style="text-align: center;">수신시간</th>
                  <th style="text-align: center;">성공/실패</th>
                  <th style="text-align: center;">오픈/클릭</th>
                  <th style="text-align: center;">SMS 성공/실패</th>
                </tr>
              </thead>
              <tbody>
              	<c:if test="${list == '[]'}">
              		<tr><td colspan=4 style="text-align: center;">데이터가 없습니다.</td></tr>
              	</c:if>
					<c:forEach items="${list}" var="pushCampaignDetail">
						<tr>
							<td style="text-align: center;">${pushCampaignDetail.mobile}</td>
							<td style="text-align: center;">${pushCampaignDetail.reg_date}</td>
							
							<c:set var="resultCode" value="${pushCampaignDetail.res_cd}" />
							<c:choose>
								<c:when test="${resultCode eq '1000'}">
									<td style="color: blue; text-align: center;">성공</td>
								</c:when>
								<c:when test="${resultCode eq '5000'}">
									<td style="color: red; text-align: center;">실패</td>
								</c:when>
								<c:otherwise>
									<td style="color: orange; text-align: center;">기타 오류</td>
								</c:otherwise>
							</c:choose>
							
							<%-- <c:set var="returnType" value="${pushCampaignDetail.rtn_type}" />
							<c:choose>
								<c:when test="${returnType eq S}">
									<td>발송 완료</td>
								</c:when>
								<c:when test="${returnType eq R}">
									<td>푸시 읽음</td>
								</c:when>
							</c:choose> --%>

							<td style="text-align: center;">${pushCampaignDetail.rtn_type}</td>
							<td style="text-align: center;"></td>
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
