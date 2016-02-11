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



<script src="//code.jquery.com/jquery-1.12.0.min.js">
<script src="./js/sendPage.js"></script>
</head>

<body>

	<!-- 헤더 -->
	<jsp:include page="./reportMenu.jsp"></jsp:include>

	<div class="container-fluid">
		<div class="row">


			<!-- 사이드바 -->
			<jsp:include page="./reportSideBar.jsp"></jsp:include>




			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<h2 class="sub-header">SMS 보내기</h2>
				<div class="form-group">
					<label for="campTitle">캠페인 제목</label> <input type="text"
						class="form-control" id="campTitle" maxlength="30"
						placeholder="캠페인 제목" style="width: 60%"> <label
						for="senderMobile">보내는 사람</label> <input type="text"
						class="form-control" id="senderMobile" style="width: 60%"
						value="${mobile}" readonly="readonly">
				</div>
				<div class="form-group">
					<label for="sendContent">내 용</label><br>
					<textarea id="sendContent" rows="15" cols="30" maxlength="90"
						onKeyUp="javascript:fnChkByte(this,'90')" style="resize: none;"
						placeholder="문자 내용"></textarea>
					<span id="byteInfo">0</span>/90Byte
				</div>

				<div class="new_sms_trgt">
					<span class="tit">받는 사람</span>
					<div class="new_sender_box mt10">

						<table cellpadding="0" cellspacing="0" border="0"
							class="new_sms_tb" width="348">
							<tbody>

								<tr>
									<td width="114" align="left"><input type="text"
										name="addr_name" placeholder="이름" class="inp"
										style="width: 90px;" onclick="this.value='';"
										onkeydown="javascript:if(event.keyCode==13){$('#addr_tel').focus();}"></td>
									<td width="140" align="left"><input type="text"
										name="addr_tel" id="addr_tel" placeholder="전화번호" class="inp"
										style="width: 115px;" onclick="this.value='';"
										onkeydown="javascript:if(event.keyCode==13){inputAddress();}"
										maxlength=11></td>
									<td width="50"><input id="targetAddBtn" type="button"
										value="추가" onclick="javascript:inputAddress();"
										class="btn btn-default btn-sm" /></td>
									<td width="50"><input id="clearBtn" type="button"
										value="clear" onclick="javascript:targetListDelete('all');"
										class="btn btn-default btn-sm" /></td>
									<!-- <td width="51"><span class="btn02"><a href="javascript:viewRecentlySendList('show');requestLastAddr('send_message_recently_list.jsp');" title="최근">최근</a></span></td> -->
								</tr>
							</tbody>
						</table>
						<table cellpadding="0" cellspacing="0" border="0"
							class="new_sms_tb" width="355">
							<tbody>
								<tr>
									<td width="255"><select name="target_list" id="target_box"
										multiple="multiple" class="add_list_area"
										style="width: 248px; height: 150px"></select></td>
									<td width="100">
										<form id="fileForm" enctype="multipart/form-data">
											<label for="targetListFile"></label> <input type="file"
												id="targetListFile" /> <input type="button" id="fileUpBtn"
												value="파일추가" class="btn btn-default btn-sm" />
										</form>
									</td>
								</tr>
							</tbody>
						</table>
						<input type="button" id="sendBtn" class="btn btn-success btn-lg"
							value="문자 전송" />
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
