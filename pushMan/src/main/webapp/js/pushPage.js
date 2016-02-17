
$(document).ready(function() {

	//텍스트 푸시 전송 버튼
	$('#textPushBtn').click(function(e) {
	if($('#pushCampTitle').val()==''){
		alert('관리용 제목을 입력하세요');
		$('#pushCampTitle').focus();
	}else if ($('#pushPopupTitle').val() == '') {
		alert('상태창 제목을 입력하세요');
		$('#pushPopupTitle').focus();
	}else if ($('#pushPopupContent').val() == '') {
		alert('팝업 내용을 입력하세요');
		$('#pushPopupContent').focus();
	}else if ($('#innerContent').val() == '') {
		alert('인앱 메시지 내용을 입력하세요');
		$('#innerContent').focus();
	}else if(confirm("푸시를 발송하시겠습니까?")==true){
		$.ajax({
			url : './push.do',
			method : 'POST',
			data : {
				pushCampTitle	 : $('#pushCampTitle').val(),	//푸시 캠페인 이름
				pushPopupTitle 	 : $('#pushPopupTitle').val(),	//푸시  팝업 제목
				targetType		 : $('#targetType').val(),		//푸시 대상자
				checkReTarget	 : $('#checkReTarget').val(),	//SMS 리타켓 여부
				smsContent		 : $('#smsContent').val(),		//SMS 내용
				pushPopupContent : $('#pushPopupContent').val(),//푸시 팝업 내용
				innerContent 	 : $('#innerContent').val(),	//푸시 인앱 내용
				pushType		 : 'text'
			},
			success : function(result) {
				console.log(result);
				window.location.replace("list.do");
			},
			error : function(e) {
				console.error('ajax 에러: ' + e.status);
			}
		});
		
	}else{
		alert("푸시 발송이 취소되었습니다.");
		return;
	}
	});
	
	//리치 푸시 전송 버튼
	$('#richPushBtn').click(function(e) {
		if($('#pushCampTitle').val()==''){
			alert('관리용 제목을 입력하세요');
			$('#pushCampTitle').focus();
		}else if ($('#pushPopupTitle').val() == '') {
			alert('상태창 제목을 입력하세요');
			$('#pushPopupTitle').focus();
		}else if ($('#richPushNotiContent').val() == '') {
			alert('상태창 내용을 입력하세요');
			$('#richPushNotiContent').focus();
		}else if (CKEDITOR.instances.richPushPopupContentEditor.getData() == '') {
			alert('팝업 내용을 입력하세요');
			CKEDITOR.instances.richPushPopupContentEditor.focus();
		}else if (CKEDITOR.instances.richInnerContentEditor.getData() == '') {
			alert('인앱 메시지 내용을 입력하세요');
			CKEDITOR.instances.richInnerContentEditor.focus();
		}else if(confirm("푸시를 발송하시겠습니까?")==true){
		$.ajax({
			url : './push.do',
			method : 'POST',
            dataType: 'html',
			data : {
				pushCampTitle	 : $('#pushCampTitle').val(),		//푸시 캠페인 이름
				pushPopupTitle 	 : $('#pushPopupTitle').val(),		//푸시  팝업 제목
				targetType		 : $('#targetType').val(),			//푸시 대상자
				checkReTarget	 : $('#checkReTarget').val(),		//SMS 리타켓 여부
				smsContent		 : $('#smsContent').val(),			//SMS 내용
				pushMsg		     : $('#richPushNotiContent').val(), //푸시 상태창 내용
				pushPopupContent : CKEDITOR.instances.richPushPopupContentEditor.getData(),	//푸시 팝업 내용
				innerContent 	 : CKEDITOR.instances.richInnerContentEditor.getData(),		//푸시 인앱 내용
				pushType		 : 'html'
			},
			success : function(result) {
				console.log(result);
				window.location.replace("list.do");
			},
			error : function(e) {
				console.error('ajax 에러: ' + e.status);
			}
		});
		}else{
			alert("푸시 발송이 취소되었습니다.");
			return;
		}
	});
});

//TEXT/RICH 스왑
$(document).ready(function(){
	//텍스트-리치 탭 버튼 이벤트
    $("#textPush").click(function(){
        $("#richPushContent").hide();
        $("#textPushContent").show();
        $("#textPush").addClass("active");
        $("#richPush").removeClass("active");
    });
    $("#richPush").click(function(){
    	$("#richPushContent").show();
        $("#textPushContent").hide();
        $("#richPush").addClass("active");
        $("#textPush").removeClass("active");
    });
    
    //텍스트 푸시 미리보기 버튼 이벤트
    //팝업푸시 미리보기 버튼 클릭 시 
    $("#previewPopupPush").click(function(){
    	$("#previewPopupPushImg").show();
    	$("#previewNotiImg").hide();
    	$("#previewInAppMessageImg").hide();
    	$("#previewPopupPush").addClass("active");
        $("#previewNoti").removeClass("active");
        $("#previewInAppMessage").removeClass("active");
    })
    //Noti 미리보기 버튼 클릭 시
    $("#previewNoti").click(function(){
    	$("#previewPopupPushImg").hide();
    	$("#previewNotiImg").show();
    	$("#previewInAppMessageImg").hide();
    	$("#previewPopupPush").removeClass("active");
        $("#previewNoti").addClass("active");
        $("#previewInAppMessage").removeClass("active");
    })
    //인앱메시지 미리보기 버튼 클릭 시
    $("#previewInAppMessage").click(function(){
    	$("#previewPopupPushImg").hide();
    	$("#previewNotiImg").hide();
    	$("#previewInAppMessageImg").show();
    	$("#previewPopupPush").removeClass("active");
        $("#previewNoti").removeClass("active");
        $("#previewInAppMessage").addClass("active");
    })
    
    //리치푸시 미리보기 버튼 이벤트
    //팝업푸시 미리보기 버튼 클릭 시 
    $("#richPreviewPopupPush").click(function(){
    	$("#richPreviewPopupPushImg").show();
    	$("#richPreviewNotiImg").hide();
    	$("#richPreviewInAppMessageImg").hide();
    	$("#richPreviewPopupPush").addClass("active");
        $("#richPreviewNoti").removeClass("active");
        $("#richPreviewInAppMessage").removeClass("active");
    })
    //Noti 미리보기 버튼 클릭 시
    $("#richPreviewNoti").click(function(){
    	$("#richPreviewPopupPushImg").hide();
    	$("#richPreviewNotiImg").show();
    	$("#richPreviewInAppMessageImg").hide();
    	$("#richPreviewPopupPush").removeClass("active");
        $("#richPreviewNoti").addClass("active");
        $("#richPreviewInAppMessage").removeClass("active");
    })
    //인앱메시지 미리보기 버튼 클릭 시
    $("#richPreviewInAppMessage").click(function(){
    	$("#richPreviewPopupPushImg").hide();
    	$("#richPreviewNotiImg").hide();
    	$("#richPreviewInAppMessageImg").show();
    	$("#richPreviewPopupPush").removeClass("active");
        $("#richPreviewNoti").removeClass("active");
        $("#richPreviewInAppMessage").addClass("active");
    })
    
 // CKEDITOR
    editor = CKEDITOR.replace('richPushPopupContentEditor');
    CKEDITOR.instances["richPushPopupContentEditor"].on("instanceReady", function(){
        this.document.on("keyup", richKeydownFunction);
   });
    editor = CKEDITOR.replace('richInnerContentEditor');
    CKEDITOR.instances["richInnerContentEditor"].on("instanceReady", function(){
        this.document.on("keyup", richKeydownFunction_inapp);
   });
});

$(document).ready(function(){
    $("#checkReTarget").change(function(){
        if($("#checkReTarget").is(":checked")){
        	$("#smsTarget").show();
        }else{
        	$("#smsTarget").hide();
        }
    });
});

//텍스트 푸시 미리보기
function keyUpFunction(obj, previewName){
	var previewText = obj.value;
	var previewSelector = '.'+previewName;
	$(previewSelector).text(previewText);
}

// CKEDITOR HTML 파싱 함수
function richKeydownFunction(){
	$('.richPreview').html($.parseHTML(CKEDITOR.instances.richPushPopupContentEditor.getData()));
}
function richKeydownFunction_inapp(){
	$('.richPreviewInAppMessageText').html($.parseHTML(CKEDITOR.instances.richInnerContentEditor.getData()));
}

// 텍스트 푸시의 Textarea 내용 바이트 제한
function fnChkByte(obj, maxByte, byteInfo) {
	//객체 값
	var str = obj.value;
	//객체 값의 길이
	var str_len = str.length;
	
	var rbyte = 0;
	var rlen = 0;
	var one_char = "";
	var str2 = "";

	for (var i = 0; i < str_len; i++) {
		one_char = str.charAt(i);
		if (escape(one_char).length > 4) {
			rbyte += 2; //한글2Byte
		} else {
			rbyte++; //영문 등 나머지 1Byte
		}

		if (rbyte <= maxByte) {
			rlen = i + 1; //return할 문자열 갯수
		}
	}

	if (rbyte > maxByte) {
		alert("한글 " + (maxByte / 2) + "자 / 영문 " + maxByte + "자를 초과 입력할 수 없습니다.");
		str2 = str.substr(0, rlen); //문자열 자르기
		obj.value = str2;
		fnChkByte(obj, maxByte);
	} else {
		document.getElementById(byteInfo).innerText = rbyte;
	} 
}