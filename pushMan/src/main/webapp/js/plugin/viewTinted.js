/*************
name : viewTinted.js
maker : play-ground.kr okhi1@naver.com
version : 1.1
date : 20150725

사용시 출처를 명시바랍니다.
************/
(function($){
	var opt,ele,viewon;

	$.fn.viewTinted = function(option){
		ele = $(this);
		opt = option;
		nowid = "viewtinted_"+$.now();
		
		ifrm = "<iframe id='"+nowid+"' src='' style='border:1px solid #ccc; width:100%; height:100%; position:absolute; left:-102px; top:-80px; z-index:100; -moz-transform: scale(0.1, 0.1); -webkit-transform: scale(0.1, 0.1); -o-transform: scale(0.1, 0.1); -ms-transform: scale(0.1, 0.1); transform: scale(0.1, 0.1); -moz-transform-origin: top left;  -webkit-transform-origin: top left;  -o-transform-origin: top left;  -ms-transform-origin: top left;  transform-origin: top left;' scrolling='no'></iframe>";

		$('[viewtinted]').hover(function(e){
			url = $(this).attr('href');

			$('body').append(ifrm);
			$('#'+nowid).attr('src',url).css({'left':(e.pageX+5)+'px','top':(e.pageY+5)+'px'});
			
		},function(e){
			$('#'+nowid).remove();
		});

		
	}

})(jQuery);