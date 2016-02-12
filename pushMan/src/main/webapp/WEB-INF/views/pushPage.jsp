<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

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

<script src="./js/pushPage.js"></script>

<script src="./editor/ckeditor.js"></script>
<!-- <link rel="stylesheet" href="./editor/samples/css/samples.css"> -->
</head>

<body>

	<!-- 헤더 -->
	<jsp:include page="./reportMenu.jsp"></jsp:include>

	<div class="container-fluid">
		<div class="row">


			<!-- 사이드바 -->
			<jsp:include page="./reportSideBar.jsp"></jsp:include>




			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<h2 class="sub-header">PUSH 보내기</h2>
				<ul class="nav nav-tabs">
					<li role="presentation" id="textPush" class="active"><a
						href="#">텍스트 푸시</a></li>
					<li role="presentation" id="richPush"><a href="#">리치 푸시</a></li>
				</ul>
				<div class="form-group">
					<label for="pushCampTitle">관리용 제목</label> <input type="text"
						class="form-control" id="pushCampTitle" maxlength="30"
						placeholder="관리용 제목" style="width: 60%">
				</div>
				<div class="form-group">
					<!-- 푸시 작성 공통 내용 -->
					<label for="pushPopupTitle">팝업 제목</label> <input type="text"
						class="form-control" id="pushPopupTitle" maxlength="30"
						placeholder="팝업 제목" style="width: 60%"><br>
					<select name="받는 사람">
					    <option value="">대상자 선택</option>
					    <option value="전체 사용자">전체 사용자</option>
					    <option value="로그인 사용자">로그인 사용자</option>
					</select>

					<!-- 텍스트 푸시 내용 -->
					<p></p>
					<div id="textPushContent">
						<label for="pushPopupContent">내 용</label><br>
						<textarea id="pushPopupContent" rows="15" cols="30" maxlength="90"
							onKeyUp="javascript:fnChkByte(this,'90')" style="resize: none;"
							placeholder="푸시 팝업 내용을 입력해 주세요"></textarea>
						<span id="byteInfo">0</span>/90Byte <br> <br> <label
							for="innerContent">앱 내 메시지 내용</label><br>
						<textarea id="innerContent" rows="15" cols="30" maxlength="90"
							onKeyUp="javascript:fnChkByte(this,'90')" style="resize: none;"
							placeholder="앱 내 내용을 입력해 주세요"></textarea>
						<span id="byteInfo">0</span>/90Byte <br>
						<input type="button" id="textPushBtn" class="btn btn-success btn-lg"
					value="PUSH!!" />
					</div>

					<!-- 리치 푸시 내용 -->
					<div id="richPushContent" style="display: none;">
						<label for="pushPopupContent">내 용</label><br>
						<textarea class="ckeditor" cols="1"
							id="richPushPopupContentEditor" name="richPushPopupContentEditor"
							rows="15">
					</textarea>
						<span id="byteInfo">0</span>/90Byte <br> <label
							for="innerContent">앱 내 메시지 내용</label><br>
						<textarea class="ckeditor" cols="1" id="richInnerContentEditor"
							name="richInnerContentEditor" rows="15">
					</textarea>
						<span id="byteInfo">0</span>/90Byte <br>
						<input type="button"
							id="richPushBtn" class="btn btn-success btn-lg" value="PUSH!!" />
					</div>
				</div>

				
			</div>




		</div>
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="./js/report/bootstrap.min.js"></script>
	<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
	<script src="./js/report/holder.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="./js/report/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
