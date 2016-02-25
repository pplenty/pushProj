// 차트 x축에 들어갈 배열 차트데이터와 lenth가 같아야함
var xAxisForTime = ['00-06', '06-07', '07-08', '08-09', '09-10', '10-11', '11-12', 
                    '12-13', '13-14', '14-15', '15-16', '16-17', '17-18', '18-19', 
                    '19-20', '20-21', '21-22', '22-23', '23-00'];

// 시간대별 비교 배열
var timeList = ['00', '01', '02', '03', '04', '05', '06', '07', '08', 
                '09', '10', '11', '12', '13', '14', '15', '16', '17', 
                '18', '19', '20', '21', '22', '23'];

$(function () {// document.ready
	
	// 조건 선택 할 때 change 이벤트 등록 
	$('#selectCond').change(function() {
		var today = new Date();
		var toDate = today.format('yyyy-MM-dd');
		$('#toDate').datepicker('setDate', toDate);
		
		switch ($('#selectCond').val()) {
			
			case 'all':
				$('#toDate').datepicker('setDate', '');
				$('#fromDate').datepicker('setDate', '');
				break;
				
			case 'week':
				today.setDate(today.getDate() - 6);// 한 주 간의 데이터
				var fromDate = today.format('yyyy-MM-dd');
				$('#toDate').datepicker('setDate', toDate);
				$('#fromDate').datepicker('setDate', fromDate);
				break;
				
			case 'month':
				today.setMonth(today.getMonth() - 1);// 한 달 간의 데이터
				today.setDate(today.getDate() + 1);
				var fromDate = today.format('yyyy-MM-dd');
				$('#toDate').datepicker('setDate', toDate);
				$('#fromDate').datepicker('setDate', fromDate);
				break;
				
			case 'today':
				var fromDate = today.format('yyyy-MM-dd');
				$('#toDate').datepicker('setDate', toDate);
				$('#fromDate').datepicker('setDate', fromDate);
				break;
				
			case 'custom':
				$('#toDate').datepicker('setDate', '');
				$('#fromDate').datepicker('setDate', '');
				break;
	
			default:
				break;
		}

		$('#datePickBtn').trigger('click');
	});
	
	
	// 날짜 변경하면 조건이 '사용자 정의'로 변경
	$('#fromDate').change(function () {
		$('#selectCond').val('custom');
	});
	$('#toDate').change(function () {
		$('#selectCond').val('custom');
	});
	
	// DatePicker 세팅
	$("#fromDate").datepicker({
		defaultDate : "+1d",//1d, 1w, 1m, 1y
		changeMonth : true,
		numberOfMonths : 1,
		dateFormat: "yy-mm-dd",
		dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
		gotoCurrent: true,
		onClose : function(selectedDate) {
			$("#toDate").datepicker("option", "minDate", selectedDate);
		}
	});
	$("#toDate").datepicker({
		defaultDate : "+2d",
		changeMonth : true,
		numberOfMonths : 1,
		dateFormat: "yy-mm-dd",
    dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
    gotoCurrent: true,
		onClose : function(selectedDate) {
			$("#fromDate").datepicker("option", "maxDate", selectedDate);
		}
	});
	
	// chart에 들어갈 data ajax 요청 후 차트 그리기
	ajaxRequestChartData();
	

	// 날짜 선택 후 차트 보기 버튼 클릭 이벤트 등록
	$('#datePickBtn').click(function() {
		// chart에 들어갈 data ajax 요청 후 차트 그리기
		ajaxRequestChartData();
	});
});



// 차트 그리기(인자: 타겟차트, 객체배열, x축 도메인)
function drawByTimeChart(target, chartData, xAxisForDomain) {
	var targetContainer = '#' + target;
    $(targetContainer).highcharts({
        chart: {
            type: 'spline'
        },
        title: {
            text: '시간대별 통계'
        },
        subtitle: {
            text: 'Open/Click/Send'
        },
        xAxis: {
            categories: xAxisForDomain
        },
        yAxis: {
            title: {
                text: '반응  수(건)'
            },
            labels: {
                formatter: function () {
                    return this.value + '건';
                }
            }
        },
        tooltip: {
            crosshairs: true,
            shared: true
        },
        credits : {
        	enabled: false
        },
        plotOptions: {
            spline: {
                marker: {
                    radius: 4,
                    lineColor: '#666666',
                    lineWidth: 1
                }
            }
        },
        series: chartData
        	  /** 차트 데이터 예시 **/
	/*        [{
	            name: 'Tokyo',
	            marker: {
	                symbol: 'square'
	            },
	            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, {
	                y: 26.5,
	                marker: {
	                    symbol: 'url(https://www.highcharts.com/samples/graphics/sun.png)'
	                }
	            }, 23.3, 18.3, 13.9, 9.6]
	
	        }, {
	            name: 'London',
	            marker: {
	                symbol: 'diamond'
	            },
	            data: [{
	                y: 3.9,
	                marker: {
	                    symbol: 'url(https://www.highcharts.com/samples/graphics/snow.png)'
	                }
	            }, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
	        }]*/
    });
}

// ajax로 받아온 데이터 시간대로 가공
function dataFormatToByTime(ajaxResponseData) {

	// 응답으로 받은데이터를 시간대(총 24개) 나누어 담는 변수
	var extendedArray;
	
	// 24시간대별 배열을 적은 시간대(00-06시) 합침 -> 배열크기 24 >> 19
	// 총 줄어든 19개의 데이터를 담을 배열 준비
	var efficArray; 
	
	// 00 ~ 23까지 '오픈/클릭' 카운트 담는 배열
	extendedArray = toExtendArray24time(ajaxResponseData);

	// 적은 시간대(00-06시) 'OPEN' 배열 합침
	efficArray = toEfficArrayByTime(extendedArray);
	
	return efficArray;
}


// 필요한 데이터만 있는 (ajax 응답) 배열을 0~23으로 확장시켜 데이터(카운트)가 없는 부분은 0 대입
function toExtendArray24time(ajaxResponseData) {
	
	// 응답으로 받은데이터를 시간대(총 24개) 나누어 담는 변수
	var extendedArray = [];
	
	for (var i = 0; i < timeList.length; i++) {
		// 시간대별 카운트가 없는 경우 0 대입
		if (typeof ajaxResponseData[timeList[i]] == "undefined") {
			extendedArray.push(0);
		} else {
			extendedArray.push(ajaxResponseData[timeList[i]]);
		}
	}
	
	return extendedArray;
}

//적은 시간대(00-06시) 합쳐서 배열 크기 (24 -> 19) 줄이는 함수
function toEfficArrayByTime(extendedArray) {
	efficArray = [];
	efficArray[0] = 0; 
	for (var i = 0; i < extendedArray.length; i++) {
		if (i <= 5) {
			efficArray[0] += extendedArray[i];
		} else {
			efficArray[i - 5] = extendedArray[i];
		}
	}
	return efficArray;
}

// Ajax 요청 함수
function ajaxRequestChartData() {
	
	// chart에 들어갈 값 ajax로 받아오기
	$.ajax({
		url : './pushChart.do',
		method : 'POST',
		dataType : 'json',
        data : {
	        fromDate : $('#fromDate').val(),
	        toDate : $("#toDate").val()
	    },
		success : function(result) {
			// ajax 응답을 JSON 파싱해서 담을 변수
			// 통계 '읽음' 차트 데이터 준비
			var readList = JSON.parse(result.readCntList);
			var clickList = JSON.parse(result.clickCntList);
			var sendList = JSON.parse(result.sendCntList);
//				console.log(readList);
//				console.log(clickList);
//				console.log(sendList);
			
			// 가공된 데이터를 담을 변수
			// ajax로 받아온 데이터 차트에 맞춰서 가공
			var readChartList = dataFormatToByTime(readList);
			var clickChartList = dataFormatToByTime(clickList);
			var sendChartList = dataFormatToByTime(sendList);
			
			
			// 차트 그리기 위한 Highchart Setting
			// 실제 차트 그릴 때 필요한 배열과 데이터 객체
			var seriesArray = [];
			var seriesObject = {};
			
			// read 세팅
			seriesObject = {};// 객체 초기화
			seriesObject.data = readChartList;// '오픈' 데이터
			seriesObject.name = '오픈';
			seriesObject.marker = {symbol: 'square'};
			seriesArray.push(seriesObject);
			
			// 클릭 추가 세팅
			seriesObject = {};// 객체 초기화
			seriesObject.data = clickChartList;// '클릭' 데이터
			seriesObject.name = '클릭';
			seriesObject.marker = {symbol: 'circle'};
//			seriesObject.color = 'red';
			seriesArray.push(seriesObject);
			
			// 클릭 추가 세팅
			seriesObject = {};// 객체 초기화
			seriesObject.data = sendChartList;// '발송' 데이터
			seriesObject.name = '발송';
			seriesObject.marker = {symbol: 'triangle'};
			seriesArray.push(seriesObject);
			
			// 시간대별 차트 그리기
			drawByTimeChart('container', seriesArray, xAxisForTime);
		},
		error : function(e) {
			console.error('ajax 에러: ' + e.status);
		}
	});
	
}



// 날짜 프로토타입 함수 정의 - format 
Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
 
    var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    var d = this;
     
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};

// Date.format에서 사용할 함수 정의
String.prototype.string = function(len) {
	var s = '', i = 0;
	while (i++ < len) {
		s += this;
	}
	return s;
};
String.prototype.zf = function(len) {
	return "0".string(len - this.length) + this;
};
Number.prototype.zf = function(len) {
	return this.toString().zf(len);
};