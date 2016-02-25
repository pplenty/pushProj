<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>PUSH MAN</title>

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
    
    <style type="text/css">
		.pagination {
			 position: absolute; left: 50%; transform: translateX(-50%);
		}
	</style>
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
		<form action='pushList.do' method='get' class="navbar-form navbar-right">
			<input class="form-control" type='text' name='word' 
				   value='${param.word}' placeholder="제목 or 내용">
			<button class="btn btn-default btn-sm">검색</button>
		</form>
		
          <h2 class="sub-header"> PUSH 발송 결과  [ ${countList}건 ] </h2>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>제목</th>
                  <th style="width: 20%;text-align: center;">발송시간</th>
                  <th style="width: 7%; text-align: center;">전체</th>
                  <th style="width: 7%; text-align: center;">대기</th>
                  <th style="width: 7%; text-align: center;">성공</th>
                  <th style="width: 7%; text-align: center;">실패</th>
                  <th style="width: 7%; text-align: center;">오픈</th>
                  <th style="width: 7%; text-align: center;">클릭</th>
                  <th style="width: 10%; text-align: center;">SMS발송</th>
                  <th style="width: 10%; text-align: center;">발송결과</th>
                </tr>
              </thead>
              <tbody class="campaign">
              	<c:if test="${list == '[]'}">
              		<tr><td colspan=10 style="text-align: center;">데이터가 없습니다.</td></tr>
              	</c:if>
				<c:forEach items="${list}" var="pushCamp">
					<tr cno="${pushCamp.camp_id}">
							<td><a tabindex="0" data-toggle="popover" data-trigger="hover" 
								   title="${pushCamp.camp_reqUid} 푸시"
								   data-content="타겟 : ${pushCamp.targetType}" class="pushCampLoad"
								   style="text-decoration: none; cursor: pointer;">${pushCamp.push_camp_title}</a></td>
							<td style="text-align: center;">${pushCamp.reg_date}</td>
							<td style="text-align: center;">${pushCamp.push_total}</td>
							<td style="text-align: center;">${pushCamp.push_wait}</td><!-- 대기 -->
							<td style="text-align: center;">${pushCamp.push_succ}</td><!-- 성공 -->
							<td style="text-align: center;">${pushCamp.push_fail}</td><!-- 실패 -->
							<td style="text-align: center;">${pushCamp.push_open}</td><!-- 오픈 -->
							<!-- 클릭 BEGIN-->
							<c:set var="pushType" value="${pushCamp.camp_reqUid}" />
							<c:if test="${pushType == 'RICH'}">
								<td style="text-align: center; cursor: pointer;" class="pushClickReport" cno="${pushCamp.camp_id}">
									<a href="" class="pushClickReport" cno="${pushCamp.camp_id}">${pushCamp.push_click}</a></td>
							</c:if>
							<c:if test="${pushType == 'TEXT'}">
								<td style="text-align: center;">${pushCamp.push_click}</td>
							</c:if>
							<!-- 클릭 END-->
							
							<!-- SMS 발신 BEGIN-->
							<td style="text-align: center;">
								<c:set var="retargetFlag" value="${pushCamp.checkReTarget}" />
								<c:if test="${retargetFlag == 'Y'}">
								<a tabindex="0" data-toggle="popover" data-trigger="hover" 
								   data-content="재발송 : ${pushCamp.push_fail}건"
								   style="text-decoration: none; cursor: pointer;"
								   class="reTargetReport" cno="${pushCamp.camp_id}">YES</a>
								</c:if>
								<c:if test="${retargetFlag == 'N'}">
								<p>NO</p>
								</c:if>
								</td><!-- SMS 발신 END-->
							<td style="text-align: center;">
								<button class="btn detailBtn" cno="${pushCamp.camp_id}">상세</button></td>
							<td class="push_title" style="display: none;">${pushCamp.push_title}</td><!-- 제목 -->
							<td class="popup_content" style="display: none;">${pushCamp.popup_content}</td><!-- 내용 -->
							<td class="inapp_content" style="display: none;">${pushCamp.inapp_content}</td><!-- 내용 -->
					</tr>
				</c:forEach>

              </tbody>
            </table>
<%-- 					<div>
						<ul class="pager">
							<c:choose>
								<c:when test="${pageNo > 1}">
									<li><a
										href='pushList.do?pageNo=${pageNo-1}&pageSize=${pageSize}&word=${param.word}&order=${param.order}'>Previous</a></li>
								</c:when>
								<c:otherwise><li><a>Previous</a></li></c:otherwise>
							</c:choose>
							<span>&nbsp; ${pageNo} &nbsp;</span>
							<c:choose>
								<c:when test="${pageNo < maxPage}">
									<li><a
										href='pushList.do?pageNo=${pageNo+1}&pageSize=${pageSize}&word=${param.word}&order=${param.order}'>Next</a></li>
								</c:when>
								<c:otherwise><li><a>Next</a></li></c:otherwise>
							</c:choose>
						</ul>
					</div> --%>
					
					<nav class="pageNavi">
						<ul class="pagination">
					<!-- 		<li class="disabled"><a href="#">«</a></li>
							<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
							<li><a href="#">»</a></li> -->
						</ul>
					</nav>

				</div>
        </div>
        <!-- 페이징 처리 스크립트 -->
        <script type="text/javascript">
        	$(document).ready(function() {
            	var pageNo = ${pageNo};
            	var maxPage = ${maxPage};
            	
            	console.log(maxPage);
            	//$('.pagination').append('<li><a href="#">«</a></li>');
            	for (var i = 1; i <= maxPage; i++ ) {
            		if (i == pageNo) {
            			$('.pagination').append('<li class="active"><a class="pageNo" href="pushList.do?pageNo=' + i + '&pageSize=${pageSize}&word=${param.word}&order=${param.order}">' + i + '</a></li>');
            		} else {
		            	$('.pagination').append('<li><a class="pageNo" href="pushList.do?pageNo=' + i + '&pageSize=${pageSize}&word=${param.word}&order=${param.order}">' + i + '</a></li>');
            		}
            	}
            	//$('.pagination').append('<li><a href="#">»</a></li>');

            	// 페이지 이벤트
//            	$('.pageNo').click(function(e) {
//            		console.log(e.target);
//            	});
            	
            	
            	
            	
            	
            	
        	});
        
        
        </script>
        <!-- Button trigger modal -->
		<button id="modalBtn" type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" style="display: none;">
		</button>
        
        <!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
		      </div>
		      <div class="modal-body">
					<div id="richPreviewInAppMessageImg" style="position: relative; width: 58%; display: block; margin: 0 auto;">
						<img src="../images/preview_inapp.PNG" width="340px" height="500px">
						<div id="richPreviewInAppMessageText" class="richPreviewInAppMessageText" style="position: absolute; top: 30px; left: 15px; z-index: 1; border: none; resize: none; background-color: transparent; color: white; word-break: break-all; overflow: auto; height: 450px;">
						</div>
					</div>
			  </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		      </div>
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
