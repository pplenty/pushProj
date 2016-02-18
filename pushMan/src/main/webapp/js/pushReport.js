//list
$(document).ready(function() {
	// 상세보기 버튼 클릭 이벤트
	$('.detailBtn').click(function (e) {
		targetCno = $(e.target).attr('cno');
		showDetail(targetCno);
	});
	
	// 클릭보기 버튼 클릭 이벤트
	/*$('#pushClickReport').click(function(e) {
		targetCnoForCilick = $(e.target).attr('cno');
		showDetail_click(targetCnoForCilick);
	});*/
	
	// SMS발송 버튼 클릭 이벤트
	$('.reTargetReport').click(function(e){
		targetCnoForReTarget = $(e.target).attr('cno');
		showDetail_reTarget(targetCnoForReTarget);
	});
	
	// 제목 HOVER: 팝오버
	$('[data-toggle="popover"]').popover();
});

//$(function() {
//	$('[data-toggle="popover"]').popover();
//})

function showDetail(cno) {
	var url = './pushListDetail.do?cno=' + cno;
	launchCenter(url,'상세', 700, 500,'menubar=no, status=no, toolbar=no');
}

/*function showDetail_click(cno) {
	var url_click = './pushListDetail_click.do?cno=' + cno;
	launchCenter(url_click,'상세', 700, 500,'menubar=no, status=no, toolbar=no');
}*/

function showDetail_reTarget(cno) {
	var url_reTarget = './pushListDetail_reTarget.do?cno=' + cno;
	launchCenter(url_reTarget,'상세', 400, 400,'menubar=no, status=no, toolbar=no');
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