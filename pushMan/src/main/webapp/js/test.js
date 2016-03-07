$(window).load(function(e) {
	alert(e + ': 11');
});
$(document).ready(function(e) {
	alert(e + ': 22');
});


$(function () {// document.ready
	
	$('#btn01').click(function (e) {
		
		$.ajax({
			url : './test.do',
			method : 'POST',
			dataType : 'json',
	        data : {
		        fromDate : 'abc'
		    },
			success : function(result) {
				console.warn('ajax 성공: ' + result.fromDate);
			},
			error : function(e) {
				console.error('ajax 에러: ' + e.status);
			}
		});
	});
	
	
});

function Person (name, age, height, kor, eng, math) {
	this.name = name;
	this.age = age;
	this.height = height;
	this.kor = kor;
	this.eng = eng;
	this.math = math;
	
}

Person.prototype.getSum = function () {
	return this.kor + this.eng + this.math;
};
Person.prototype.getAverage = function () {
	return this.getSum() / 4;
};

function Rectangle (width, height) {
	var width = width;
	var height = height;
	
	this.getWidth = function () {
		return width;
	};
	this.getHeight = function () {
		return height;
	};
	this.setWidth = function (value) {
		if (value < 0) {
			throw '길이 음수 에러';
		} else {
			width = value;
		}
	};
	this.setHeight = function (value) {
		if (value < 0) {
			throw '길이 음수 에러';
		} else {
			height = value;
		}
	};
}

Rectangle.prototype.getArea = function () {
	return this.getWidth() * this.getHeight();
}

function Square(length) {
	this.parent = Rectangle;
	this.parent(length, length);
}
Square.prototype = Rectangle.prototype;

//Array.prototype.test = function () {
//	alert('TEST');
//}
var obj = function () {
	alert('TEST');
};
Object.defineProperty(obj, "test", { value: null, enumerable: false });


//날짜 프로토타입 함수 정의 - format 
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