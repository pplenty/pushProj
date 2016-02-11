package com.pushman.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

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
import com.pushman.dao.AuthNumberDao;
import com.pushman.domain.PushCampaignVo;
import com.pushman.domain.SmsUserVo;

@Controller
public class PushController {

	@Autowired
	AuthNumberDao authNumberDao;

	@RequestMapping("/pushPage")
	public String pushPage(String mobile, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model) {

		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		if (smsUser == null) return "index";// 세션 없을 시 메인으로
		model.addAttribute("name", smsUser.getName());

		return "pushPage";
	}
	
	@RequestMapping("/pushTest")
	@ResponseBody
	public Object pushTest(
			HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		
		
		String pushCampTitle = request.getParameter("pushCampTitle");	
		String pushPopupTitle = request.getParameter("pushPopupTitle");	
		String pushPopupContent = request.getParameter("pushPopupContent");	
		String innerContent = request.getParameter("innerContent");	

		
		
		System.out.println(pushCampTitle);
		
		HashMap<String, String> responseData = new HashMap<String, String>();
		responseData.put("body", "ok");
		return responseData;
	}

	@RequestMapping("/push")
	@ResponseBody
	public Object push(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		
		PushCampaignVo pushCampaignVo = new PushCampaignVo();
		
		String pushCampTitle = request.getParameter("pushCampTitle");	
		String pushPopupTitle = request.getParameter("pushPopupTitle");	
		String pushPopupContent = request.getParameter("pushPopupContent");	
		String innerContent = request.getParameter("innerContent");	
		
		URL url = null;
		URLConnection urlConnection = null;
		String body = null;

		// URL 주소
		String sUrl = "http://dev-api.pushpia.com/msg/send/realtime";

		// 파라미터 이름
		String paramName = "d";

		// 파라미터 이름에 대한 값
		String paramValue = null;

		JsonObject jo = new JsonObject();
		jo.addProperty("bizId", "06d388bd180a42018ba0da946d099d09");
		jo.addProperty("msgType", "T");
		jo.addProperty("pushTime", 1800);
		jo.addProperty("pushTitle", pushPopupTitle);
		jo.addProperty("pushMsg", pushPopupContent);
		jo.addProperty("inappContent", innerContent);
		jo.addProperty("pushKey", "1");
		jo.addProperty("pushValue", "http://www.pushpia.com");
		jo.addProperty("reserveTime", "20150417101702");
//		jo.addProperty("reqUid", "pushpia_20150417101702");
//		jo.addProperty("custId", "436149");

		JsonArray jarr = new JsonArray();
		JsonObject jo1 = new JsonObject();
		JsonObject jo2 = new JsonObject();
		jo1.addProperty("reqUid", "pushpia_20150417101702");
		jo1.addProperty("custId", "436149");
		jo2.addProperty("reqUid", "pushpia_20150417101712");
		jo2.addProperty("custId", "kohyusik");
		jarr.add(jo1);
		jarr.add(jo2);
		
		jo.add("list", jarr);
		

		try {
			paramValue = URLEncoder.encode(jo.toString(), "UTF-8");

			// Get방식으로 전송 하기
			// url = new URL(sUrl + "?" + paramName + "=" + paramValue);
			// urlConnection = url.openConnection();
			// printByInputStream(urlConnection.getInputStream());

			// Post방식으로 전송 하기
			url = new URL(sUrl);
			urlConnection = url.openConnection();
			urlConnection.setDoOutput(true);

			printByOutputStream(urlConnection.getOutputStream(), paramName
					+ "=" + paramValue);
			printByInputStream(urlConnection.getInputStream());
			// body = IOUtils.toString(urlConnection.getInputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}

		HashMap<String, String> responseData = new HashMap<String, String>();
		responseData.put("body", body);
		responseData.put("jo", jo.toString());
		return responseData;
	}

	// 웹 서버로 부터 받은 웹 페이지 결과를 콘솔에 출력하는 메소드
	public void printByInputStream(InputStream is) {
		byte[] buf = new byte[1024];
		int len = -1;
		try {
			while ((len = is.read(buf, 0, buf.length)) != -1) {
				System.out.write(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
