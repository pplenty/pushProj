var msgChartList;
var popupChartList;

$(function () {
	//TAB 이벤트 등록
	$('a[href="#msgSection"]').click(function(e) {
		$('a[href="#msgSection"]').parent().addClass('active');
		$('a[href="#popupSection"]').parent().removeClass('active');
		$('#msgSection').show();
		$('#popupSection').hide();
//		showChart('container', msgChartList);
	})
	$('a[href="#popupSection"]').click(function(e) {
		$('a[href="#popupSection"]').parent().addClass('active');
		$('a[href="#msgSection"]').parent().removeClass('active');
		$('#popupSection').show();
		$('#msgSection').hide();
//		showChart('container2', popupChartList);
	})
	
    $(document).ready(function () {
    	var list;
    	var chartList;
    	showChart('', '');
    	
    	// chart에 들어갈 값 ajax로 받아오기
/*    	$.ajax({
			url : './clickChart.do',
			method : 'POST',
			dataType : 'json',
			data : {
				camp_id : $('#container').attr('campId')
			},
			success : function(result) {
				//메시지 클릭수 차트 데이터 준비
				list = JSON.parse(result.msgList);
				chartList = [];
				for (var i = 0; i < list.length; i++) {
			    	var chartItem = {};
			    	
			    	// 링크 갯수가 10개 이상이면 링크 주소 대신 시퀀스로 표현
			    	if (list.length > 10) {
			    		chartItem.name = list[i].linkSeq;
			    	} else {
			    		chartItem.name = list[i].linkAddr;
			    	}
			    	chartItem.y = list[i].clickCnt;
			    	chartList.push(chartItem);
				}
				showChart('container', chartList);
				msgChartList = chartList;
				
				//팝업 클릭수 차트 데이터 준비
				list = JSON.parse(result.popupList);
				chartList = [];
				for (var i = 0; i < list.length; i++) {
			    	var chartItem = {};
			    	chartItem.name = list[i].linkAddr;
			    	chartItem.y = list[i].clickCnt;
			    	chartList.push(chartItem);
				}
				showChart('container2', chartList);
				popupChartList = chartList;
			},
			error : function(e) {
				console.error('ajax 에러: ' + e.status);
			}
		});*/
    });
});



// 차트 보여주기(인자: 객체배열)
function showChart(target, chartData) {
	var targetContainer = '#' + target;
    $('#container').highcharts({
        chart: {
            type: 'spline'
        },
        title: {
            text: 'Monthly Average Temperature'
        },
        subtitle: {
            text: 'Source: WorldClimate.com'
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Temperature'
            },
            labels: {
                formatter: function () {
                    return this.value + '°';
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
        series: [{
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
        }]
    });
}