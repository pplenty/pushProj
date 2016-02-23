package com.pushman.util;

public class PushSetting {

	// 요청 URL 주소(고정 값)
	public static final String REQUEST_URL = "http://dev-api.pushpia.com/msg/send/realtime";
	
	// 요청 파라미터 명 (고정 값)
	public static final String REQUEST_PARAMETER_NAME = "d";
	
	// 페이로드에 넘겨줄 주소(고정 값)
	public static final String PUSHVALUE_URL = "http://www.pushman.com";
	
	// text push biz key (고정 값)
	public static final String TEXT_PUSH_BIZ_KEY = "06d388bd180a42018ba0da946d099d09";

	// html push biz key (고정 값)
	public static final String RICH_PUSH_BIZ_KEY = "f91e805198204094b753262839e3e551";

	// PUSH - SMS(iHeart DB) 재발송 식별 키 - MSG_ETC_1
	public static final String RE_SMS_KEY = "kohPush";
	
	// SMS(iHeart DB) 식별 키 - MSG_ETC_1
	public static final String SMS_SENDER_KEY = "koh";

	// 고정 값(과거 값)
	public static final String PUSH_RESERVED_TIME = "20160201120000";
	
	// 고정 값 (소문자 L로 고정되어야 하며 변경 시 발송 불가)
	public static final String PUSH_KEY = "l";

	// 고정 값(발송 유효 시간)
	public static final int PUSH_VALID_TIME = 1800;
	
	// (푸시피아 버그 스크립트 삽입)
	public static final String INSERT_SCRIPT = "<script src='http://pushpia.com/pms-sdk.js'></script>";

}
