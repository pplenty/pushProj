//list
$(document).ready(function() {
	
	var errMap = JSON.parse($('.hiddenMap').attr('hm'));
	var campList = $('tbody.campaign>tr');
	var listSize = $('tbody.campaign>tr').length;

//	console.log(errMap);
	for (var i = 0; i < listSize; i++) {
		var cno = $(campList[i]).attr('cno');
		var trSelector = 'tbody.campaign>tr[cno="' + cno + '"]';
		if ($(trSelector).length != 0) {//데이터가 있을 때만
			var numSucc = errMap[cno].success;
			var numWait = errMap[cno].wait;
			//실패 = 전체 건수 - 성공 - 대기 
			var numFail = $(trSelector + ' td:nth-child(4)').text() - numSucc - numWait;
			
			$(trSelector + ' td:nth-child(5)').text(numSucc);// 성공
			$(trSelector + ' td:nth-child(6)').text(numWait);// 대기
			$(trSelector + ' td:nth-child(7)').text(numFail);// 실패
		}
	}
	
	
	
	$('.detailBtn').click(function (e) {
		targetCno = $(e.target).attr('cno');
		showDetail(targetCno);
	});
	

	
	
});

$(function() {
	$('[data-toggle="popover"]').popover();
})

// jui.ready([ "ui.modal" ], function(modal) {
//    $("#modal_1").("body");
//
//    modal_1 = modal("#modal_1", {
//        color: "black"
//    });
//});


function updateSendRate() {
	console.log('ajax동기화');
	$.ajax({
		url : './updateSendRate.do',
		method : 'POST',
		data : {
			cno : cno,
		},
		success : function(result) {
			console.log(result.status);
		},
		error : function(e) {
			console.error('ajax 에러: ' + e.status);
		}
	});
}


function showDetail(cno) {
	var url = './listDetail.do?cno=' + cno;
	
	launchCenter(url,'상세', 700, 500,'menubar=no, status=no, toolbar=no');
}

// popup
function launchCenter(url, name, width, height, att) {

	var str = "height=" + height + ",innerHeight=" + height;
	str += ",width=" + width + ",innerWidth=" + width;

	if (window.screen) {
		// screen.width : 현재 운영체제의 너비
		// screen.height : 현재 운영체제의 높이
		var ah = screen.availHeight - 30;
		var aw = screen.availWidth - 10;

		// 중앙위치 구해오기
		var xc = (aw - width) / 2;
		var yc = (ah - height) / 2;

		str += ",left=" + xc + ",screenX=" + xc;
		str += ",top=" + yc + ",screenY=" + yc;
		str += "," + att
	}
	
	return window.open(url, name, str);
}