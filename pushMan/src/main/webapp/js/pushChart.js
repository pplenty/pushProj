// 실제 차트 그릴 때 필요한 배열과 데이터 객체
var series = [];
var chartDataOne = {};

// 시간대별 비교 배열
var timeList = ['00', '01', '02', '03', '04', '05', '06', '07', '08', 
                '09', '10', '11', '12', '13', '14', '15', '16', '17', 
                '18', '19', '20', '21', '22', '23'];

$(function () {
    $(document).ready(function () {
    	
    	// ajax 응답을 담을 변수
    	var readList;
    	var clickList;
    	
    	// 응답으로 받은데이터를 시간대(총 24개) 나누어 담는 변수
    	var readChartList;
    	var clickChartList;
    	
    	// 24시간대별 배열을 적은 시간대(00-06시) 합침 -> 배열크기 24 >> 19
    	// 총 줄어든 19개의 데이터를 담을 배열 준비
		var efficReadChartList;
		var efficClickChartList;
    	
    	
    	
    	// chart에 들어갈 값 ajax로 받아오기
    	$.ajax({
			url : './pushChart.do',
			method : 'POST',
			dataType : 'json',
//			data : {
//				camp_id : $('#container').attr('campId')
//			},
			success : function(result) {
				// 통계 '읽음' 차트 데이터 준비
				readList = JSON.parse(result.readCntList);
				console.log(readList);
				
				// 통계 '클릭' 차트 데이터 준비
				clickList = JSON.parse(result.clickCntList);
				console.log(clickList);
				
				// 00 ~ 23까지 '오픈' 카운트 담는 배열
				readChartList = toExtendArray24time(readList);
				// 00 ~ 23까지 '클릭' 카운트 담는 배열
				clickChartList = toExtendArray24time(clickList);

				
				// 적은 시간대(00-06시) 'OPEN' 배열 합침
				efficReadChartList = toEfficArrayByTime(readChartList);
				// 적은 시간대(00-06시) 'CLICK' 배열 합침
				efficClickChartList = toEfficArrayByTime(clickChartList);
				
				
				// 차트 그리기 위한 Highchart Setting
				chartDataOne = {};// 객체 초기화
				chartDataOne.data = efficReadChartList;// 오픈 데이터
				chartDataOne.name = '오픈';
				chartDataOne.marker = {symbol: 'square'};
				series.push(chartDataOne);
				
				// 클릭 추가 세팅
				chartDataOne = {};// 객체 초기화
				chartDataOne.data = efficClickChartList;// 클릭 데이터
				chartDataOne.name = '클릭';
				chartDataOne.marker = {symbol: 'circle'};
				series.push(chartDataOne);
				
				showChart('container', series);
			},
			error : function(e) {
				console.error('ajax 에러: ' + e.status);
			}
		});
    });
});



// 차트 보여주기(인자: 객체배열)
function showChart(target, chartData) {
	var targetContainer = '#' + target;
    $(targetContainer).highcharts({
        chart: {
            type: 'spline'
        },
        title: {
            text: '시간대별 오픈/클릭 수'
        },
        subtitle: {
            text: 'Open/Click'
        },
        xAxis: {
            categories: ['00-06', '06-07', '07-08', '08-09', '09-10', '10-11',
                '11-12', '12-13', '13-14', '14-15', '15-16', '16-17', '17-18',
                '18-19', '19-20', '20-21', '21-22', '22-23', '23-00']
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
        	  // 차트 데이터 예시
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

// 필요한 데이터만 있는 (ajax 응답) 배열을 0~23으로 확장시켜 데이터(카운트)가 없는 부분은 0 대입
function toExtendArray24time(resJsonList) {
	
	// 응답으로 받은데이터를 시간대(총 24개) 나누어 담는 변수
	var extendArray = [];
	
	for (var i = 0; i < timeList.length; i++) {
		// 시간대별 카운트가 없는 경우 0 대입
		if (typeof resJsonList[timeList[i]] == "undefined") {
			extendArray.push(0);
		} else {
			extendArray.push(resJsonList[timeList[i]]);
		}
	}
	
	return extendArray;
}

//적은 시간대(00-06시) 합쳐서 배열 크기 (24 -> 19) 줄이는 함수
function toEfficArrayByTime(extendArray) {
	efficArray = [];
	efficArray[0] = 0; 
	for (var i = 0; i < extendArray.length; i++) {
		if (i <= 5) {
			efficArray[0] += extendArray[i];
		} else {
			efficArray[i - 5] = extendArray[i];
		}
	}
	
	return efficArray;
}
