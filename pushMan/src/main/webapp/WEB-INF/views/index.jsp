<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
<title>PUSH MAN</title>
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/animate-custom.css" />
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>

<!-- My Js / CSS -->
<script src="js/login.js"></script>
<link rel="stylesheet" type="text/css" href="css/indexStyle.css" />
</head>
<body>
	<div class="container">
		<!-- Codrops top bar -->
		<div class="codrops-top">

			<span class="right">
				<a href="">
					<!-- <strong>temp refresh</strong> -->
				</a>
			</span>
			<div class="clr"></div>
		</div>
		<!--/ Codrops top bar -->
		<header>
			<h1>
				PUSH <span>MAN</span>
			</h1>
		</header>
		<section>
			<div id="container_demo">
				<!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
				<a class="hiddenanchor" id="toregister"></a> <a class="hiddenanchor"
					id="tologin"></a>
				<div id="wrapper">
					<div id="login" class="animate form">
						<form action="./login2.do" method="post"
							autocomplete="on">
							<h1>Log in</h1>
							<p>
								<label for="email" class="uname" data-icon="u"> 
								Your email </label>
								<span id="loginStatus">&nbsp;&nbsp;&nbsp;&nbsp;</span>
								<input id="email" name="email" required="required"
										<c:if test="${not empty cookie.email}">
											value="${cookie.email.value}"
									    </c:if>
										type="email" placeholder="mymail@mail.com"  />
							</p>
							<p>
								<label for="password" class="youpasswd" data-icon="p">
									Your password </label> <input id="password" name="password"
									required="required" type="password" placeholder="eg. X8df!90EO" />
							</p>
							<p class="keeplogin">
								<input type="checkbox" name="loginkeeping" id="loginkeeping"
									    <c:if test="${not empty cookie.email}">
									    	checked
									    </c:if>
									/> <label for="loginkeeping"> Save email</label>
							</p>
							<p class="login button">
								<input id="signinBtn" type="submit" value="Login" />
							</p>
							<p class="change_link">
								Not a member yet ? <a href="#toregister" class="to_register">Join
									us</a>
							</p>
						</form>
					</div>

					<div id="register" class="animate form">
						<form id="signInForm" method="post"
							autocomplete="on">
							<h1>Sign up</h1>
							<p>
								<label for="emailsignup" class="youmail" data-icon="e">
									Email  </label>
									<span id="duplOk">&nbsp;&nbsp;&nbsp;</span><br>
									<input id="emailsignup" name="emailsignup"
									required="required" type="email" maxlength="30"
									placeholder="sendMan@mail.com" style="width: 70%"/>
									<span id="duplEmail" class="simpleBtn">중복 검사</span>
							</p>
							<p>
								<label for="usernamesignup" class="uname" data-icon="u">Your
									Name</label> <br>
									<input id="usernamesignup" name="usernamesignup"
									required="required" type="text" maxlength="30"
									placeholder="Name" style="width: 70%"/>
							</p>
							<p>
								<label for="passwordsignup" class="youpasswd" data-icon="p">
									Password </label>
									<span id="passValid">&nbsp;&nbsp;&nbsp;</span><br>
									<input id="passwordsignup" 
									name="passwordsignup" maxlength="15"
									required="required" type="password" placeholder="eg. X8df!90EO" 
									style="width: 70%"/>
							</p>
							<p>
								<label for="passwordsignup_confirm"
									class="youpasswd pwd_confirm" data-icon="p">Please
									confirm your password </label><br>
									<input id="passwordsignup_confirm"
									name="passwordsignup_confirm" required="required"
									maxlength="15" type="password" placeholder="eg. X8df!90EO"
									style="width: 70%"/>
							</p>
							<p>
								<label for="mobilesignup" class="yourmobile">Your mobile</label><br>
							<input id="mobilesignup" name="mobilesignup" required="required"
								maxlength="11" type="text" placeholder="01099999999" 
								style="width: 30%; display: inline-block;
								padding: 10px 5px 10px 5px; letter-spacing: 2px;
								text-align: center;">
								<span id="sendAuthNo" class="simpleBtn">인증번호 발송</span>
								<span id="time" class="simpleText"></span>
							</p>
							
							<div>
							<input id="authNumber" name="authNumber" required="required"
								maxlength="4" type="text" placeholder="0000" 
								style="width: 20%; font-size: xx-large;
								       padding: 10px 5px 10px 5px; letter-spacing: 1px;
								       text-align: center;"/>
							<span id="checkAuthNo" class="simpleBtn">확 인</span>
							<span id="authNoCheck" class="simpleText"></span></div>
							
							<p class="signup button">
								<input id="signupBtn" type="submit" value="Sign up" />
							</p>
							<p class="change_link">
								Already a member ? <a href="#tologin" class="to_register">
									Go and log in </a>
							</p>
						</form>
					</div>

				</div>
			</div>
		</section>
	</div>
</body>
</html>