<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>SEND MAN</title>

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
	
	<!-- myJS -->
    <script src="./js/pushReport.js"></script>
</head>

  <body>

	<!-- 헤더 -->
	<jsp:include page="./reportMenu.jsp"></jsp:include>

    <div class="container-fluid">
      <div class="row">
      
		<!-- 사이드바 -->
      	<jsp:include page="./reportSideBar.jsp"></jsp:include>

      	
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

		<!-- 검색창 -->
		<form action='list.do' method='get' class="navbar-form navbar-right">
			<input class="form-control" type='text' name='word' 
				   value='${param.word}' placeholder="제목 or 내용">
			<button class="btn btn-default btn-sm">검색</button>
		</form>
          <h2 class="sub-header">발송 결과  [ ${countList}건 ] </h2>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>제목</th>
                  <th style="width: 20%; text-align: center;">발송시간</th>
                  <th style="width: 8%; text-align: center;">전체</th>
                  <th style="width: 8%; text-align: center;">성공</th>
                  <th style="width: 8%; text-align: center;">대기</th>
                  <th style="width: 8%; text-align: center;">실패</th>
                  <th style="width: 8%; text-align: center;">오픈</th>
                  <th style="width: 8%; text-align: center;">클릭</th>
                  <th style="width: 15%; text-align: center;">발송 결과</th>
                </tr>
              </thead>
              <tbody class="campaign">
              	<c:if test="${list == '[]'}">
              		<tr><td colspan=9 style="text-align: center;">데이터가 없습니다.</td></tr>
              	</c:if>
				<c:forEach items="${list}" var="pushCamp">
					<tr cno="${pushCamp.camp_id}">
							<td><a tabindex="0" data-toggle="popover" data-trigger="hover" 
								   title="{$pushCamp.push_title}" 
								   data-content="${pushCamp.inapp_content}"
								   style="text-decoration:none;">${pushCamp.push_title}</a></td>
							<td style="text-align: center;">${pushCamp.reg_date}</td>
							<td style="text-align: center;">${pushCamp.push_total}</td>
							<td style="text-align: center;">${pushCamp.push_succ}</td><!-- 성공 -->
							<td style="text-align: center;">${pushCamp.push_wait}</td><!-- 대기 -->
							<td style="text-align: center;">${pushCamp.push_fail}</td><!-- 실패 -->
							<td style="text-align: center;">${pushCamp.push_open}</td><!-- 오픈 -->
							<td style="text-align: center;">${pushCamp.push_click}</td><!-- 클릭 -->
							<td style="text-align: center;">
								<button class="btn detailBtn" 
										cno="${pushCamp.camp_id}"> 상세</button></td>
					</tr>
				</c:forEach>

              </tbody>
            </table>
					<div>
						<ul class="pager">
							<c:choose>
								<c:when test="${pageNo > 1}">
									<li><a
										href='list.do?pageNo=${pageNo-1}&pageSize=${pageSize}&word=${param.word}&order=${param.order}'>Previous</a></li>
								</c:when>
								<c:otherwise><li><a>Previous</a></li></c:otherwise>
							</c:choose>
							<span>&nbsp; ${pageNo} &nbsp;</span>
							<c:choose>
								<c:when test="${pageNo < maxPage}">
									<li><a
										href='list.do?pageNo=${pageNo+1}&pageSize=${pageSize}&word=${param.word}&order=${param.order}'>Next</a></li>
								</c:when>
								<c:otherwise><li><a>Next</a></li></c:otherwise>
							</c:choose>
						</ul>
						
					</div>

				</div>
        </div>
      </div>
    </div>
    

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="./js/report/bootstrap.min.js"></script>
    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->
    <script src="./js/report/holder.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="./js/report/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
