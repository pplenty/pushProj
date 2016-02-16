package com.pushman.util;

public class PushSetting {

	// 요청 URL 주소(고정 값)
	public static final String REQUEST_URL = "http://dev-api.pushpia.com/msg/send/realtime";
	
	// 페이로드에 넘겨줄 주소(고정 값)
	public static final String PUSHVALUE_URL = "http://www.pushman.com";
	
	// text push biz key (고정 값)
	public static final String TEXT_PUSH_BIZ_KEY = "06d388bd180a42018ba0da946d099d09";

	// html push biz key (고정 값)
	public static final String RICH_PUSH_BIZ_KEY = "f91e805198204094b753262839e3e551";

	// SMS(iHeart DB) 식별 키 - MSG_ETC_1
	public static final String RE_SMS_KEY = "kohPush";

}
