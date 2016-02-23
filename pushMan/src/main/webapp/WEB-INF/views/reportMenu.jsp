<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
		  
          <a class="navbar-brand" href="chartPage.do">PUSH MAN</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="chartPage.do">푸시 통계</a></li>
            <li><a href="pushList.do">푸시 결과</a></li>
            <li><a href="pushPage.do">푸시 보내기</a></li>
            <li><a> </a></li>
            <li><a href="list.do">SMS 발송 결과</a></li>
            <li><a href="senderPage.do">SMS 보내기</a></li>
            <li><a>${name} 님</a></li>
            <li><a href="logout.do">Logout</a></li>
          </ul>
        </div>
      </div>
    </nav>