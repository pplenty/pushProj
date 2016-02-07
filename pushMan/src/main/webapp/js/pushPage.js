$(document).ready(function() {

	//문자 전송 버튼
	$('#pushBtn').click(function(e) {

		$.ajax({
			url : './push.do',
			method : 'POST',
			data : {
				pushCampTitle	 : $('#pushCampTitle').val(),
				pushPopupTitle 	 : $('#pushPopupTitle').val(),
				pushPopupContent : $('#pushPopupContent').val(),
				innerContent 	 : $('#innerContent').val()
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
function fnChkByte(obj, maxByte) {
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
		document.getElementById('byteInfo').innerText = rbyte;
	}
}

