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

<script src="./js/pushPage.js"></script>

<script src="../editor/ckeditor.js"></script>

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
				<br>
				<div class="col-md-6">
				<div class="form-group">
					<label for="pushCampTitle">관리용 제목</label> <input type="text"
						class="form-control" id="pushCampTitle" maxlength="30"
						placeholder="관리용 제목" style="width: 100%" value="${pushCampTitle}">
				</div>
				<div class="form-group">
					<!-- 푸시 작성 공통 내용 -->
					<label for="pushPopupTitle">상태창 제목</label> <input type="text"
						class="form-control" id="pushPopupTitle" maxlength="20"
						placeholder="팝업 제목" style="width: 100%"
						onkeyUp="keyUpFunction(this, 'previewNotiTitle')" value="${pushPopupTitle}"><br>
					<select id="targetType">
						<option value="all">전체 사용자</option>
						<option value="loginToday">오늘 로그인 사용자</option>
					</select>
					<div>
						<input type="checkbox" id="checkReTarget" value="N"> SMS
						리타겟팅 <br>
						<div id="smsTarget" style="display: none;">
							<textarea id="smsContent" rows="5" cols="30"
								style="resize: none;" placeholder="푸시 실패시 발송할 SMS의 내용을 입력해주새요."
								onKeyUp="javascript:fnChkByte(this,'90', 'byteInfoSms')"></textarea>
							<span id="byteInfoSms">0</span>/90Byte
						</div>
					</div>
				</div>
				</div>
				<!-- 푸시 작성 공통 내용 -->

				<!-- 텍스트 푸시 내용 -->
				<p></p>
				<div id="textPushContent">
					<div class="col-md-7" style="float: left">
						<label for="pushPopupContent">팝업 내용</label><br>
						<textarea id="pushPopupContent" rows="8" cols="42" maxlength="80"
							onKeyUp="javascript:fnChkByte(this,'80', 'byteInfoPopup'), keyUpFunction(this, 'preview')"
							style="resize: none;" placeholder="푸시 팝업 내용을 입력해 주세요"></textarea>
						<span id="byteInfoPopup">0</span>/80Byte <br> <br> 
						
						<label for="innerContent">앱 내 메시지 내용</label><br>
						<textarea id="innerContent" rows="12" cols="42" maxlength="3500"
							onKeyUp="javascript:fnChkByte(this,'3500', 'byteInfoInner'), keyUpFunction(this, 'previewInAppMessageText')"
							style="resize: none;" placeholder="앱 내 내용을 입력해 주세요"></textarea>
						<span id="byteInfoInner">0</span>/3500Byte <br> <input
							type="button" id="textPushBtn" class="btn btn-success btn-lg"
							value="PUSH!!" />
					</div>

					<!-- 텍스트 푸시 미리보기 -->
					<div id="previewScreen" class="col-md-5" style="float: left">
						<div>
							<button id="previewPopupPush" type="button"
								class="btn btn-default btn-lg active">팝업 푸시</button>
							<button id="previewNoti" type="button"
								class="btn btn-default btn-lg">상태창</button>
							<button id="previewInAppMessage" type="button"
								class="btn btn-default btn-lg">앱 내 메시지</button>
						</div>
						<!-- 텍스트 푸시 팝업 메시지 미리보기 -->
						<div id="previewPopupPushImg" style="position: relative;">
							<img src="../images/preview_push.PNG" width="340px"
								height="500px">
							<textarea id="previewPushPopupText" class="preview" cols="30"
								rows="6" readonly="readonly"
								style='position: absolute; top: 70px; left: 50px; z-index: 1; border: none; resize: none; background-color: transparent;'>
								</textarea>
						</div>
						<!-- 텍스트 푸시 Notification 미리보기 -->
						<div id="previewNotiImg" style="position: relative; display: none">
							<img src="../images/preview_noti.PNG" width="340px"
								height="500px">
							<textarea id="previewPushNotiTitle" class="previewNotiTitle"
								cols="35" rows="1" readonly="readonly"
								style='position: absolute; top: 106px; left: 60px; z-index: 1; border: none; resize: none; background-color: transparent; color: white;'>
								</textarea>
							<textarea id="previewPushNotiText" class="preview" cols="35"
								rows="3" readonly="readonly"
								style='position: absolute; top: 125px; left: 60px; z-index: 1; border: none; resize: none; background-color: transparent; color: white;'>
								</textarea>
						</div>
						<!-- 텍스트 푸시 앱 내 메시지 미리보기 -->
						<div id="previewInAppMessageImg"
							style="position: relative; display: none">
							<img src="../images/preview_inapp.PNG" width="340px"
								height="500px">
							<textarea id="previewInAppMessageText"
								class="previewInAppMessageText" cols="40" rows="22"
								readonly="readonly"
								style='position: absolute; top: 30px; left: 20px; z-index: 1; border: none; resize: none; background-color: transparent; color: white;'>
								</textarea>
						</div>
					</div>
				</div>
				<!-- 텍스트 푸시 내용 -->

				<!-- 리치 푸시 내용 -->
				<div id="richPushContent" style="display: none">
					<div class="col-md-7" style="float: left">
						<label for="pushPopupContent">상태창 내용</label><br>
						<textarea id="richPushNotiContent" rows="3" cols="62"
							maxlength="90"
							onKeyUp="javascript:fnChkByte(this,'90', 'byteInfoRichNoti'), keyUpFunction(this, 'richPreviewNoti')"
							style="resize: none;" placeholder="상태창 내용을 입력해 주세요"></textarea>
						<br> <span id="byteInfoRichNoti">0</span>/90Byte <br> <br>
						<label for="pushPopupContent">팝업 내용</label><br>
						<textarea class="ckeditor" cols="1"
							id="richPushPopupContentEditor" name="richPushPopupContentEditor"
							rows="15"
							onKeyUp="javascript:fnChkByteEditor(this,'3400', 'byteInfoRichPopup')"
							onkeydown="richKeydownFunction(this, 'richPreview')">
							</textarea>
						<label for="innerContent">앱 내 메시지 내용</label><br>
						<textarea class="ckeditor" cols="1" id="richInnerContentEditor"
							name="richInnerContentEditor" rows="15"
							onKeyUp="javascript:fnChkByteEditor(this,'3400', 'byteInfoRichInner')"
							onkeydown="richKeydownFunction(this, 'richPreviewInAppMessageText')">
							</textarea>
						 <input type="button" id="richPushBtn" class="btn btn-success btn-lg" value="PUSH!!" />
					</div>

					<!-- 리치푸시 미리보기 -->
					<div id="previewScreen" class="col-md-5" style="float: left">
						<div>
							<button id="richPreviewPopupPush" type="button"
								class="btn btn-default btn-lg active">팝업 푸시</button>
							<button id="richPreviewNoti" type="button"
								class="btn btn-default btn-lg">상태창</button>
							<button id="richPreviewInAppMessage" type="button"
								class="btn btn-default btn-lg">앱 내 메시지</button>
						</div>

						<div id="richPreviewPopupPushImg"
							style="position: relative; width: 75%">
							<img src="../images/preview_push.PNG" width="340px"
								height="500px">
							<div id="richPreviewPushPopupText" class="richPreview"
								style='position: absolute; top: 70px; left: 50px; z-index: 1; border: none; resize: none; background-color: transparent; word-break: break-all; overflow: auto; height: 120px;'>
							</div>
						</div>


						<div id="richPreviewNotiImg"
							style="position: relative; display: none; width: 70%">
							<img src="../images/preview_noti.PNG" width="340px"
								height="500px">
							<textarea id="richPreviewPushNotiTitle" class="previewNotiTitle"
								cols="35" rows="1" readonly="readonly"
								style='position: absolute; top: 106px; left: 60px; z-index: 1; border: none; resize: none; background-color: transparent; color: white; word-break: break-all;'>
								</textarea>
							<textarea id="richPreviewPushNotiText" class="richPreviewNoti"
								cols="35" rows="3" readonly="readonly"
								style='position: absolute; top: 125px; left: 60px; z-index: 1; border: none; resize: none; background-color: transparent; color: white; word-break: break-all;'>
								</textarea>
						</div>


						<div id="richPreviewInAppMessageImg"
							style="position: relative; display: none; width: 85%">
							<img src="../images/preview_inapp.PNG" width="340px"
								height="500px">
							<div id="richPreviewInAppMessageText"
								class="richPreviewInAppMessageText"
								style='position: absolute; top: 30px; left: 20px; z-index: 1; border: none; resize: none; background-color: transparent; color: white; word-break: break-all; overflow: auto; height: 450px;'>
							</div>
						</div>
					</div>
				</div>
				<!-- 리치 푸시 내용 -->

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
