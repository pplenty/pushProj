package com.pushman.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pushman.dao.AppUserDao;
import com.pushman.dao.PushCampaignDao;
import com.pushman.dao.PushCampaignDetailDao;
import com.pushman.domain.AppUserVo;
import com.pushman.domain.PushCampaignDetailVo;
import com.pushman.domain.PushCampaignVo;
import com.pushman.domain.SmsUserVo;
import com.pushman.util.CommonMethod;
import com.pushman.util.PushSetting;

@Controller
public class PushController {

	@Autowired
	AppUserDao appUserDao;
	@Autowired
	PushCampaignDao pushCampaignDao;
	@Autowired
	PushCampaignDetailDao pushCampaignDetailDao;

	
	//PUSH PAGE LOAD
	@RequestMapping("/pushPage")
	public String pushPage(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) {

		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		if (smsUser == null) return "index";// 세션 없을 시 메인으로
		model.addAttribute("name", smsUser.getName());

		return "pushPage";
	}

	//PUSH 요청 URL
	@RequestMapping("/push")
	@ResponseBody
	public Object push(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		if (smsUser == null) return "index";// 세션 없을 시 메인으로

		// ajax 요청 파라미터 받기
		String pushType 		= request.getParameter("pushType"); 		// 푸시 타입 구분(Text/Html)
		String checkReTarget 	= request.getParameter("checkReTarget"); 	// 푸시 실패 시 SMS 전송 여부
		String targetType 		= request.getParameter("targetType"); 		// 타겟 타입(전체/로그인한 사람)
		String pushCampTitle  	= request.getParameter("pushCampTitle");	// 캠페인 관리용 제목
		String pushPopupTitle 	= request.getParameter("pushPopupTitle");	// 헤드라인, 팝업 제목
		String smsContent		= request.getParameter("smsContent");		// SMS 재발송 내용
		String pushMsg 			= null;										// notification 내용
		String pushPopupContent = null;										// 팝업 내용
		String innerContent 	= null;									 	// 인 앱 내용	
		
//		if (checkReTarget == null) checkReTarget = "N";
		// TEXT / RICH content, 캠페인 reqUid 생성
		String campReqUid = null;
		
		// TEXT push 인 경우
		if ("text".equals(pushType)) {
			campReqUid = create_ReqUid('T');
			pushMsg = request.getParameter("pushPopupContent");	
			pushPopupContent = request.getParameter("pushPopupContent");	
			innerContent = request.getParameter("innerContent");	
		}//rich push 인 경우
		else {
			campReqUid = create_ReqUid('H');
			pushMsg = request.getParameter("pushMsg");	
			pushPopupContent = request.getParameter("pushPopupContent");	
			innerContent = request.getParameter("innerContent");	
		}
				
				
		// 푸시 캠페인 등록
		PushCampaignVo pushCampaignVo = new PushCampaignVo();
		pushCampaignVo.setPush_camp_title(pushCampTitle);
		pushCampaignVo.setPush_title(pushPopupTitle);
		pushCampaignVo.setPush_msg(pushMsg);
		pushCampaignVo.setPopup_content(pushPopupContent);
		pushCampaignVo.setInapp_content(innerContent);
		pushCampaignVo.setUser_no(smsUser.getNo());
		pushCampaignVo.setCheckReTarget(checkReTarget);
		pushCampaignVo.setSmsContent(smsContent);
		pushCampaignVo.setTargetType(targetType);
		
		// 푸시 캠페인 초기 INSERT	
		pushCampaignDao.insert(pushCampaignVo);// INSERT 캠페인
		campReqUid += pushCampaignVo.getCamp_id();// campReqUid에 camp_id 넣기
		
		

		// 타겟별 상세 캠페인 준비
		PushCampaignDetailVo pushCampaignDetailVo = null;
		

		// 푸시 발송 타겟팅
		JsonArray targetList = new JsonArray();
		JsonObject targetObject = null;
		
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("target", targetType);// all, loginToday
		List<AppUserVo> appUserVoList = appUserDao.selectListTarget(sqlParams);// param: 타겟 옵션
		
		for (AppUserVo appUserVo : appUserVoList) {

			// 타겟별 상세 캠페인 등록
			pushCampaignDetailVo = new PushCampaignDetailVo();
			pushCampaignDetailVo.setCamp_id(pushCampaignVo.getCamp_id());
			pushCampaignDetailVo.setUser_id(appUserVo.getUser_id());
			pushCampaignDetailDao.insert(pushCampaignDetailVo);
			
			// 타겟 리스트 파라미터에 추가
			targetObject = new JsonObject();
			targetObject.addProperty("reqUid", campReqUid + "_" + pushCampaignDetailVo.getCd_id());
			targetObject.addProperty("custId", appUserVo.getCust_id());
			targetList.add(targetObject);
		}
		
		
		// 푸시 캠페인 total값과 reqUid 갱신
		pushCampaignVo.setCamp_reqUid(campReqUid);
		pushCampaignVo.setPush_total(appUserVoList.size());
		pushCampaignDao.updateInit(pushCampaignVo);

		
		URL url = null;
		URLConnection urlConnection = null;

		String requestUrl 	= PushSetting.REQUEST_URL;				// 요청 URL 주소(고정 값)
		String paramName 	= PushSetting.REQUEST_PARAMETER_NAME;	// 파라미터 이름(고정 값)
		String paramValue	= null;									// 파라미터 값
		String responseJSON = null;									// 응답 JSON

		JsonObject pushReqParam = new JsonObject();
		// text/rich push biz key구분
		if ("text".equals(pushType)) {
			pushReqParam.addProperty("bizId", PushSetting.TEXT_PUSH_BIZ_KEY);	// text biz key
			pushReqParam.addProperty("msgType", "T"); // TEXT
		} else {
			pushReqParam.addProperty("bizId", PushSetting.RICH_PUSH_BIZ_KEY); 	// rich biz key
			pushReqParam.addProperty("msgType", "H"); // HTML
			pushPopupContent = pushPopupContent + PushSetting.INSERT_SCRIPT;
			innerContent = innerContent + PushSetting.INSERT_SCRIPT;// (푸시피아 click 버그 스크립트 삽입)
		}
		
		pushReqParam.addProperty("pushTime", PushSetting.PUSH_VALID_TIME);		// 고정 값(발송 유효 시간)
		pushReqParam.addProperty("pushTitle", pushPopupTitle);					// 팝업, 상태창 제목
		pushReqParam.addProperty("pushMsg", pushMsg);							// 상태창 메시지
		pushReqParam.addProperty("popupContent", pushPopupContent);  			// 팝업 내용
		pushReqParam.addProperty("inappContent", innerContent);  				// 인앱 메시지
		pushReqParam.addProperty("pushKey", PushSetting.PUSH_KEY);				// 고정 값 (소문자 L로 고정되어야 하며 변경 시 발송 불가)
		pushReqParam.addProperty("pushValue", PushSetting.PUSHVALUE_URL);
		pushReqParam.addProperty("reserveTime", PushSetting.PUSH_RESERVED_TIME);// 고정 값(과거 값)
		
		pushReqParam.add("list", targetList);// 동보 메시지 타겟 리스트 추가
		System.out.println(pushReqParam);
		try {
			paramValue = URLEncoder.encode(pushReqParam.toString(), "UTF-8");

			// Post방식으로 전송 하기(푸시 요청)
			url = new URL(requestUrl);
			urlConnection = url.openConnection();
			urlConnection.setDoOutput(true);

			printByOutputStream(urlConnection.getOutputStream(), paramName
					+ "=" + paramValue);
			responseJSON = printByInputStream(urlConnection.getInputStream());
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		// ajax 응답 parameter Map
		HashMap<String, String> responseData = new HashMap<String, String>();
		
		responseData.put("resJSON", responseJSON);
		responseData.put("pushReqParam", pushReqParam.toString());
		return responseData;
	}
	

	// 웹 서버로 부터 받은 웹 페이지 결과를 스트링으로 리턴하는 메소드
	public String printByInputStream(InputStream is) {
		byte[] buf = new byte[1024];
		int len = -1;
		StringBuffer returnMsg = new StringBuffer();
		try {
			while ((len = is.read(buf, 0, buf.length)) != -1) {
				returnMsg.append(new String(buf, 0, len));
			}
			System.out.println("응답: " + returnMsg);
			return returnMsg.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnMsg.toString();
	}

	// 웹 서버로 파라미터명과 값의 쌍을 전송하는 메소드
	public void printByOutputStream(OutputStream os, String msg) {
		try {
			byte[] msgBuf = msg.getBytes("UTF-8");
			os.write(msgBuf, 0, msgBuf.length);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ReqUid 생성 Text/Html
	public String create_ReqUid(char type) {
		String reqUid =  "pushman" + System.currentTimeMillis() + "C" + CommonMethod.count();
		if (type == 'T') 
			reqUid += "_T_";
		else 
			reqUid += "_H_";
		
		return reqUid;
	}
}
