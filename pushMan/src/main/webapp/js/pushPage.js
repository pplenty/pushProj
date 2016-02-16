
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

//TEXT/RICH 스왑
$(document).ready(function(){
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
    
    
 // CKEDITOR
    editor = CKEDITOR.replace('richInnerContentEditor');
    CKEDITOR.instances["richInnerContentEditor"].on("instanceReady", function(){
        this.document.on("keyup", updateHtmls);
   });
});

// CKEDITOR
function updateHtmls(){
    /*alert(editor.getData());*/
}

