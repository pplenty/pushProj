var sendAuthNoBtnClick = true;
var timeID;
var emailFlag = false;
var pwdVaildFlag = false;
var pwdFlag = false;
var authNumFlag = false;

var authNumber;



// 로그인 부분
$(document).ready(function() {
	
	
	// 로그인 버튼
	$('#signinBtn').click(function(e) {
		console.log('hi');
		
		var email = $('#email').val();
		var password = $('#password').val();
		var loginkeeping = $('#loginkeeping:checked').val();//checked 안되면 undefined
		
		
		if (email == '') {
			$('#loginStatus').text('이메일을 입력하세요.');
			$('#loginStatus').css('color', 'red');
			$('#email').focus();
		} else if (password == '') {
			$('#loginStatus').text('비밀번호를 입력하세요.');
			$('#loginStatus').css('color', 'red');
			$('#password').focus();
		} else {
			
			$.ajax({
				url : './login2.do',
				method : 'POST',
				data : {
					email : email,
					password : password,
					loginkeeping : loginkeeping
				},
				success : function(result) {
					console.log(result.status);
					if (result.status == "noEmail") {
						$('#loginStatus').text('가입되지 않은 이메일입니다.');
						$('#loginStatus').css('color', 'red');
						$('#email').select();
					} else if (result.status == "wrongPwd") {
						$('#loginStatus').text('비밀번호가 일치하지 않습니다.');
						$('#loginStatus').css('color', 'red');
						$('#password').select();
					} else {
						location.href = 'chartPage.do'
					}
				},
				error : function(e) {
					console.error('ajax 에러: ' + e.status);
				}
			});
			
		}
		e.preventDefault();
		
	});
	
});

//회원가입 부분
$(document).ready(function() {
	
	// 비밀번호 유효성 검사 
	$('#passwordsignup').keyup(function() {
		
		var pwd = $('#passwordsignup').val();
		var pwd_confirm = $('#passwordsignup_confirm').val();
		
		pwdVaildFlag = checkPassword(pwd);
		
		if (pwd != pwd_confirm) {
			$('#passwordsignup_confirm').css({
				'border' : 'red solid 1px'
			});
			pwdFlag = false;
		} else {
			$('#passwordsignup_confirm').css({
				'border' : 'rgb(178,178,178) solid 1px'
			});
			pwdFlag = true;
		}
		
		
		
	});
	
	// 비밀번호 재확인
	$('#passwordsignup_confirm').keyup(function() {
		var pwd = $('#passwordsignup').val();
		var pwd_confirm = $('#passwordsignup_confirm').val();

		if (pwd != pwd_confirm) {
			$('#passwordsignup_confirm').css({
				'border' : 'red solid 1px'
			});
			pwdFlag = false;
		} else {
			$('#passwordsignup_confirm').css({
				'border' : 'rgb(178,178,178) solid 1px'
			});
			pwdFlag = true;
		}
	});
	
	// 이메일 인증 후 재입력 방지
	$('#emailsignup').keyup(function() {
		$('#duplOk').text('');
		emailFlag = false;
	});

	// 인증 후 번호 재입력 방지
	$('#mobilesignup').keyup(function() {
		$('#authNoCheck').text('');
		authNumFlag = false;
		$('#checkAuthNo').css('display','inline-block');
	});
	

	// 이메일 중복검사
	$('#duplEmail').click(function(e) {
		var email = $('#emailsignup').val();

		if (!email) {
			$('#duplOk').text('이메일을 입력하세요.');
			$('#duplOk').css('color', 'red');
			$('#emailsignup').focus();
			emailFlag = false;
		} else if (!validateEmail(email)){
			$('#duplOk').text("이메일 형식이 올바르지 않습니다.");
			$('#duplOk').css('color', 'red');
			$('#emailsignup').focus();
			emailFlag = false;
		} else {
			$.ajax({
				url : './duplEmail.do',
				method : 'POST',
				data : {
					email : email
				},
				success : function(result) {
					console.log(result.checkEmail);
					if (result.checkEmail == "ok") {
						$('#duplOk').text("사용가능합니다.");
						$('#duplOk').css('color', 'blue');
						$('#usernamesignup').focus();
						emailFlag = true;
					} else {
						$('#duplOk').text("이미 사용 중인 이메일입니다.");
						$('#duplOk').css('color', 'red');
						$('#emailsignup').select();
						emailFlag = false;
					}

				},
				error : function(e) {
					console.error('ajax 에러: ' + e.status);
				}
			});
		}
	});
	
	
	//회원가입 버튼
	$('#signupBtn').click(function(e) {
		
		//기본 submit 버튼 이벤트 
		e.preventDefault();

		doubleSumitFlag = false;

		if (!emailFlag) {
			alert("이메일 중복 체크를 해주세요.");
		} else if (!pwdVaildFlag) {
			alert("비밀번호는 영문,숫자 조합 6 ~ 15자만 가능합니다.");
		} else if (!pwdFlag) {
			alert("비밀번호를 확인 해주세요.");
		} else if (!authNumFlag) {
			alert("모바일 인증이 필요합니다.");
		} else {
			alert('가입이 완료되었습니다.');
			$('#signInForm').attr('action', './signup.do');
			$('#signInForm').submit();
		}

	});

	// 인증번호 전송
	$('#sendAuthNo').click(function(e) {

		if (sendAuthNoBtnClick) {

			var mobile = $('#mobilesignup').val();

			if (!mobile) {
				alert("모바일 번호를 입력하세요.");
			} else {
				sendAuthNoBtnClick = false;
				$.ajax({
					url : './authNo.do',
					method : 'GET',
					data : {
						mobile : mobile
					},
					success : function(result) {
						authNumber = result.authNum
						console.log(authNumber);
						alert('인증번호가 전송되었습니다.');
						$('#mobilesignup').attr('readonly', 'readonly');
						$('#authNumber').select();
					},
					error : function(e) {
						console.error('ajax 에러: ' + e.status);
					}
				});
				timerStart();
			}
		}
	});
	
	// 인증번호 확인
	$('#checkAuthNo').click(function(e) {

		var inputAuthNumber = ""; 
		inputAuthNumber = $('#authNumber').val();
		
		if (inputAuthNumber == "") {
			$('#authNoCheck').text('인증번호를 입력하세요.');
			$('#authNoCheck').css('color', 'red');
			$('#sendAuthNo').focus();
		} else if (inputAuthNumber != authNumber) {
			$('#authNoCheck').text("인증 실패!");
			$('#authNoCheck').css('color', 'red');
			$('#sendAuthNo').select();
		} else {
			authNumFlag = true;
			$('#authNoCheck').text("인증 완료!");
			$('#authNoCheck').css('color', 'blue');
			$('#checkAuthNo').hide();

			// 인증번호 삭제 요청
			clearInterval(timeID);
			$('#time').text("");
//			sendAuthNoBtnClick = true;
			deleteAuthNumber();
			
		}
		
//		sendAuthNoBtnClick = true;
		
	});

})

// $(document).on('click', '#sendAuthNo', function(e) {});

// 인증번호 3분 타이머 시작
function startTimer(duration, display) {
	var timer = duration, minutes, seconds;
	timeID = setInterval(function() {
		minutes = parseInt(timer / 60, 10);
		seconds = parseInt(timer % 60, 10);

		minutes = minutes < 10 ? "0" + minutes : minutes;
		seconds = seconds < 10 ? "0" + seconds : seconds;

		display.text(minutes + ":" + seconds);

		if (--timer < 0) {
			clearInterval(timeID);
			display.text("");
			sendAuthNoBtnClick = true;
			// 인증번호 삭제 요청
			$('#mobilesignup').removeAttr('readonly');
			deleteAuthNumber();
		}
	}, 1000);
}
function timerStart() {
	var time = 60 * 2;
//	var time = 5;
	display = $('#time');
	startTimer(time, display);
};

// 인증번호 3분 타이머 리셋
function resetTimer() {
	clearInterval(timeID);
}

// 이메일 형식 체크
function validateEmail(strValue) {
	var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
	// 이메일 형식에 맞지않으면
	if (!strValue.match(regExp)) {
		return false;
	}

	return true;
}

//비밀번호 유효성
function checkPassword(password) {
	// 비밀번호 영문, 숫자, 특수기호 조합 8~15
//	var RegexPw   = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/; 
	var RegexPw   = /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,15}$/; 
	

	if (!RegexPw.test(password)) {
		$('#passValid').text('영문,숫자 조합 6 ~ 15자').css('color', 'red');
		return false;
	}

	$('#passValid').text('사용 가능합니다.').css('color', 'blue');
	return true;
}


function deleteAuthNumber() {
	$.ajax({
		url : './authNoDelete.do',
		method : 'GET',
		data : {
			authNumber : authNumber
		},
		success : function(result) {
			authNumber = "";//인증번호 만료
		},
		error : function(e) {
			console.error('ajax 에러: ' + e.status);
		}
	});
}

//커서 범위 정하는 함수
$.fn.selectRange = function(start, end) {
	return this.each(function() {
		if(this.setSelectionRange) {
			this.focus();
			this.setSelectionRange(start, end);
		}
		else if(this.createTextRange) {
			var range = this.createTextRange();
			range.collapse(true);
			range.moveEnd('character', end);
			range.moveStart('character', start);
			range.select();
		}
	});
};

