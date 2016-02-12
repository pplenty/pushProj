package com.pushman.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

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
import com.pushman.domain.AppUserVo;
import com.pushman.domain.PushCampaignVo;
import com.pushman.domain.SmsUserVo;
import com.pushman.util.PushSetting;

@Controller
public class PushController {

	@Autowired
	AppUserDao appUserDao;

	@RequestMapping("/pushPage")
	public String pushPage(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) {

		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		if (smsUser == null) return "index";// 세션 없을 시 메인으로
		model.addAttribute("name", smsUser.getName());

		return "pushPage";
	}

	@RequestMapping("/push")
	@ResponseBody
	public Object push(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		// ajax 요청 파라미터
		String pushCampTitle = request.getParameter("pushCampTitle");	
		String pushPopupTitle = request.getParameter("pushPopupTitle");	
		String pushMsg = request.getParameter("pushPopupContent");// 이부분 인풋 추가해야함	
		String pushPopupContent = request.getParameter("pushPopupContent");	
		String innerContent = request.getParameter("innerContent");	
		
		PushCampaignVo pushCampaignVo = new PushCampaignVo();
		
		
		
		URL url = null;
		URLConnection urlConnection = null;
		String body = null;

		// 요청 URL 주소(고정 값)
		String requestUrl = PushSetting.REQUEST_URL;

		// 파라미터 이름(고정 값)
		String paramName = "d";

		// 파라미터 이름에 대한 값
		String paramValue = null;
		
		// 응답 JSON
		String responseJSON = null;

		JsonObject jo = new JsonObject();
		jo.addProperty("bizId", PushSetting.TEXT_PUSH_BIZ_KEY); // biz key
		jo.addProperty("msgType", "T"); // TEXT
		jo.addProperty("pushTime", 1800);// 고정 값(발송 유효 시간)
		jo.addProperty("pushTitle", pushPopupTitle);// 팝업, 상태창 제목
		jo.addProperty("pushMsg", pushMsg);// 상태창 메시지
		jo.addProperty("popupContent", pushPopupContent);//팝업 내용
		jo.addProperty("inappContent", innerContent);// 인앱 메시지
		jo.addProperty("pushKey", "l");// 고정 값 (소문자 L로 고정되어야 하며 변경 시 발송 불가)
		jo.addProperty("pushValue", PushSetting.PUSHVALUE_URL);
		jo.addProperty("reserveTime", "20150417101702");
		System.out.println(jo);

		// 동보발송(사용자 list 추가)
		JsonArray jarr = new JsonArray();
		JsonObject targetJSON = null;
		List<AppUserVo> appUserVoList = appUserDao.selectListAll(null);
		
		for (AppUserVo appUserVo : appUserVoList) {
			targetJSON = new JsonObject();
			targetJSON.addProperty("reqUid", "pushpia_20150417101702");
			targetJSON.addProperty("custId", appUserVo.getCust_id());
			jarr.add(targetJSON);
		}
		
		jo.add("list", jarr);
		
		
		
		try {
			paramValue = URLEncoder.encode(jo.toString(), "UTF-8");

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

		HashMap<String, String> responseData = new HashMap<String, String>();
		
		responseData.put("resJSON", responseJSON);
		responseData.put("jo", jo.toString());
		return responseData;
	}
	

	@RequestMapping("/richPush")
	@ResponseBody
	public Object richPush(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		
		PushCampaignVo pushCampaignVo = new PushCampaignVo();
		
		// ajax 요청 파라미터
		String pushCampTitle = request.getParameter("pushCampTitle");	
		String pushPopupTitle = request.getParameter("pushPopupTitle");	
		String pushPopupContent = request.getParameter("pushPopupContent");	
		String innerContent = request.getParameter("innerContent");	
		
		URL url = null;
		URLConnection urlConnection = null;
		String body = null;

		// 요청 URL 주소(고정 값)
		String sUrl = "http://dev-api.pushpia.com/msg/send/realtime";

		// 파라미터 이름(고정 값)
		String paramName = "d";

		// 파라미터 이름에 대한 값
		String paramValue = null;
		
		// 응답 JSON
		String responseJSON = null;

		JsonObject jo = new JsonObject();
		jo.addProperty("bizId", "f91e805198204094b753262839e3e551");
		jo.addProperty("msgType", "H");
		jo.addProperty("pushTime", 1800);// 고정 값(발송 유효 시간)
		jo.addProperty("pushTitle", pushPopupTitle);
		jo.addProperty("pushMsg", pushPopupContent);
		jo.addProperty("inappContent", innerContent);
		jo.addProperty("pushKey", "l");// 고정 값 (소문자 L로 고정되어야 하며 변경 시 발송 불가)
		jo.addProperty("pushValue", "http://www.pushpia.com");
		jo.addProperty("reserveTime", "20150417101702");

		System.out.println("인앱: " + innerContent);
		// 동보발송(사용자 list 추가)
		JsonArray jarr = new JsonArray();
		JsonObject targetJSON = null;
		List<AppUserVo> appUserVoList = appUserDao.selectListAll(null);
		
		for (AppUserVo appUserVo : appUserVoList) {
			targetJSON = new JsonObject();
			targetJSON.addProperty("reqUid", "pushpia_20150417101702");
			targetJSON.addProperty("custId", appUserVo.getCust_id());
			jarr.add(targetJSON);
		}
		
		jo.add("list", jarr);
		System.out.println(jo);
		
		
		
		try {
			paramValue = URLEncoder.encode(jo.toString(), "UTF-8");
			System.out.println("http://dev-api.pushpia.com/msg/send/realtime?d=" + paramValue);
			// Post방식으로 전송 하기(푸시 요청)
			url = new URL(sUrl);
			urlConnection = url.openConnection();
			urlConnection.setDoOutput(true);

			printByOutputStream(urlConnection.getOutputStream(), paramName
					+ "=" + paramValue);
			responseJSON = printByInputStream(urlConnection.getInputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}

		HashMap<String, String> responseData = new HashMap<String, String>();
		
		responseData.put("resJSON", responseJSON);
		responseData.put("jo", jo.toString());
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
}
