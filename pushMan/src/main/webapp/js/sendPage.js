var targetList = [];//json
var list = '';

//list
$(document).ready(function() {
	
	// 파일 타켓팅
	$('#fileUpBtn').click(function (e) {
		
		if ($('#targetListFile').val() == '') return;
		
		var tmpArray;
		var fileName;
		var data = new FormData();
        $.each($('#targetListFile')[0].files, function(i, file) {          
            data.append('file-' + i, file);
            fileName = file.name;
            
        });
         
        $.ajax({
            url: './fileTarget.do',
            type: "post",
            dataType: "json",
            data: data,
            // cache: false,
            processData: false,
            contentType: false,
            success: function(data, textStatus, jqXHR) {
            	tmpArray = $.parseJSON(data.fileList);

            	var listMax = 5;
            	for (var int = 0; int < tmpArray.length; int++) {
            		targetList.push(tmpArray[int]);
            		
            		//파일 추가시 상위 5명만 목록에 추가
            		if (listMax-- > 0) {
            			var target = tmpArray[int].name + '(' + tmpArray[int].mobile + ')';
            			$('#target_box').append('<option targetType="file">' + 
            					target + '-' + fileName + '</option>');
            		}
				}
            	console.log(targetList);//타겟배열 콘솔에 출력
            	
            	//selectBox에 파일명으로 추가
//            	$('#target_box').append('<option targetType="file">' + fileName + '</option>');
            	
            	
            	
            }, 
            error: function(jqXHR, textStatus, errorThrown) {}
        });
		
	});
	
	
	
	//문자 전송 버튼
	$('#sendBtn').click (function (e) {

		list = JSON.stringify(targetList);
		
		console.log(list);
		var msg = '총 ' + targetList.length + '명\n발송하시겠습니까?';

		

		fnChkByte($('#sendContent')[0],'90');
		if ($('#campTitle').val() == '') {
			alert('캠페인 제목을 입력하세요');
		} else if ($('#sendContent').val() == '') {
			alert('문자 내용을 입력하세요');
		} else if (targetList.length == 0) {
			alert('1명 이상에게 보내야 합니다.');
		} else if (confirm(msg) == 0) {// No click
			return;
		} else {// Yes click
			$.ajax({
				url : './send.do',
				method : 'POST',
				data : {
					campTitle : $('#campTitle').val(),
					senderMobile : $('#senderMobile').val(),
					list : list,
					content : $('#sendContent').val()
				},
				success : function(result) {
					console.log(result);
					window.location.replace("list.do");
				},
				error : function(e) {
					console.error('ajax 에러: ' + e.status);
				}
			});		
		}

		
		
		
	});
	
});

// $('.addRecipBtn').click
// 타겟(개인) 추가 함수
function inputAddress() {
	
	var targetName;
	var targetMobile;
	
	targetName = $('.inp[name="addr_name"]').val();
	targetMobile = $('.inp[name="addr_tel"]').val();

	if (targetName == '') {
		targetName = '고객';
	}
	if (targetMobile == '') {
		alert('전화번호를 입력해 주세요.');
		return;
	}
	if (!mobileValidCheck(targetMobile)) {
	    alert("잘못된 모바일 번호입니다.");
		$('.inp[name="addr_tel"]').focus().val('');
		return;
	}
	
	
	targetList.push({'name': targetName, 'mobile': targetMobile});
	
	
	var target = targetName + '(' + targetMobile + ')';
	
	$('#target_box').append('<option targetType="person">' + target + '</option>');
	$('.inp[name="addr_name"]').focus();
	$('.inp[name="addr_name"]').val('');
	$('.inp[name="addr_tel"]').val('');
}

// 개별, 전체 삭제
function targetListDelete(type) {
	if (type == 'all') {
		$('#target_box').empty();
		targetList = [];
	} else {
		$('#target_box option:selected').detach();
		//
	}
} 

function targetToJson (targetBox) {
	var targetList;
	
}

// 내용 바이트 제한
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

// 모바일 번호 유효성 검사
function mobileValidCheck(mobile) {
	var regExp = /^01([0|1|6|7|8|9]?)([0-9]{3,4})([0-9]{4})$/;
	if ( !regExp.test(mobile) ) {
	      return false;
	}
	return true;
}



