
$(document).ready(function() {

	//텍스트 푸시 전송 버튼
	$('#textPushBtn').click(function(e) {

		$.ajax({
			url : './push.do',
			method : 'POST',
			data : {
				pushCampTitle	 : $('#pushCampTitle').val(),
				pushPopupTitle 	 : $('#pushPopupTitle').val(),
				pushPopupContent : $('#pushPopupContent').val(),
				innerContent 	 : $('#innerContent').val(),
				pushType		 : 'text'
			},
			success : function(result) {
				console.log(result);
//				window.location.replace("list.do");
			},
			error : function(e) {
				console.error('ajax 에러: ' + e.status);
			}
		});
	});
	
	//리치 푸시 전송 버튼
	$('#richPushBtn').click(function(e) {

		$.ajax({
			url : './push.do',
			method : 'POST',
            dataType: 'html',
			data : {
				pushCampTitle	 : $('#pushCampTitle').val(),
				pushPopupTitle 	 : $('#pushPopupTitle').val(),
				pushPopupContent : CKEDITOR.instances.richPushPopupContentEditor.getData(),
				innerContent 	 : CKEDITOR.instances.richInnerContentEditor.getData(),
				pushType		 : 'html'
			},
			success : function(result) {
				console.log(result);
//				window.location.replace("list.do");
			},
			error : function(e) {
				console.error('ajax 에러: ' + e.status);
			}
		});

	});
});


//내용 바이트 제한
function fnChkByte(obj, maxByte, byteInfo) {
	var str = obj.value;
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



function keydownFunction(obj, previewName){
	var previewText = obj.value;
	var previewSelector = '.'+previewName;
	$(previewSelector).text(previewText);
}


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
    })
    //Noti 미리보기 버튼 클릭 시
    $("#previewNoti").click(function(){
    	$("#previewPopupPushImg").hide();
    	$("#previewNotiImg").show();
    	$("#previewInAppMessageImg").hide();
    })
    //인앱메시지 미리보기 버튼 클릭 시
    $("#previewInAppMessage").click(function(){
    	$("#previewPopupPushImg").hide();
    	$("#previewNotiImg").hide();
    	$("#previewInAppMessageImg").show();
    })
    
    //리치푸시 미리보기 버튼 이벤트
    //팝업푸시 미리보기 버튼 클릭 시 
    $("#richPreviewPopupPush").click(function(){
    	$("#richPreviewPopupPushImg").show();
    	$("#richPreviewNotiImg").hide();
    	$("#richPreviewInAppMessageImg").hide();
    })
    //Noti 미리보기 버튼 클릭 시
    $("#richPreviewNoti").click(function(){
    	$("#richPreviewPopupPushImg").hide();
    	$("#richPreviewNotiImg").show();
    	$("#richPreviewInAppMessageImg").hide();
    })
    //인앱메시지 미리보기 버튼 클릭 시
    $("#richPreviewInAppMessage").click(function(){
    	$("#richPreviewPopupPushImg").hide();
    	$("#richPreviewNotiImg").hide();
    	$("#richPreviewInAppMessageImg").show();
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

// CKEDITOR
function richKeydownFunction(){
	$('.richPreview').html(CKEDITOR.instances.richPushPopupContentEditor.getData());
}

function richKeydownFunction_inapp(){
	$('.richPreviewInAppMessageText').html(CKEDITOR.instances.richInnerContentEditor.getData());
}

