var series = [];
var chartDataOne = {};
//var list;

$(function () {
    $(document).ready(function () {
    	var timeList = ['00', '01', '02', '03', '04', '05', '06', '07', '08', 
    	                '09', '10', '11', '12', '13', '14', '15', '16', '17', 
    	                '18', '19', '20', '21', '22', '23']
    	var list;
    	var chartList;
//    	showChart('container', chartList);
    	
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
				list = JSON.parse(result.readCntList);
				console.log(list);
				chartList = [];
				for (var i = 0; i < timeList.length; i++) {
					// 시간대별 카운트가 없는 경우 0 대입
					if (typeof list[timeList[i]] == "undefined") {
						chartList.push(0);
					} else {
						chartList.push(list[timeList[i]]);
					}
				}
				// 적은 시간대(00-06시) 합침
				var efficChartList = [];
				efficChartList[0] = 0; 
				for (var i = 0; i < chartList.length; i++) {
					if (i < 7) {
						efficChartList[0] += chartList[i];
					} else {
						efficChartList[i - 6] = chartList[i];
					}
				}
				console.log(chartList);
				chartDataOne.data = efficChartList;
				chartDataOne.name = '오픈';
				chartDataOne.marker = {symbol: 'square'};
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
            text: '시간대별 오픈률'
        },
        subtitle: {
            text: 'Open'
        },
        xAxis: {
            categories: ['00-06', '06-07', '07-08', '08-09', '09-10', '10-11',
                '11-12', '12-13', '13-14', '14-15', '15-16', '16-17', '17-18',
                '18-19', '19-20', '20-21', '21-22', '22-23']
        },
        yAxis: {
            title: {
                text: '오픈율'
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
//        [{
//            name: 'Tokyo',
//            marker: {
//                symbol: 'square'
//            },
//            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, {
//                y: 26.5,
//                marker: {
//                    symbol: 'url(https://www.highcharts.com/samples/graphics/sun.png)'
//                }
//            }, 23.3, 18.3, 13.9, 9.6]
//
//        }, {
//            name: 'London',
//            marker: {
//                symbol: 'diamond'
//            },
//            data: [{
//                y: 3.9,
//                marker: {
//                    symbol: 'url(https://www.highcharts.com/samples/graphics/snow.png)'
//                }
//            }, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
//        }]
    });
}