package com.pushman.web;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import au.com.bytecode.opencsv.CSVReader;

import com.pushman.dao.AuthNumberDao;
import com.pushman.dao.CampaignDao;
import com.pushman.dao.CampaignDetailDao;
import com.pushman.domain.CampaignDetailVo;
import com.pushman.domain.CampaignVo;
import com.pushman.domain.SmsUserVo;
import com.pushman.util.CommonMethod;


@Controller
public class SendController {

	@Autowired
	AuthNumberDao authNumberDao;
	@Autowired
	CampaignDao campaignDao;
	@Autowired
	CampaignDetailDao campaignDetailDao;
	@Autowired
	ServletContext ctx;
	
	
	
	@RequestMapping(value="/send", method = RequestMethod.POST)
	@ResponseBody
	public Object sendSMS(
			String list, String content, String senderMobile, String campTitle,
			HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) {

		SmsUserVo user = (SmsUserVo)session.getAttribute("user");
		String userEmail = user.getEmail();
		
		
	    Connection con = null;
		Statement stmt = null;
		
		
		//JSONArray
		JSONArray ja = (JSONArray)JSONValue.parse(list);
		int sendTotal = ja.size();
		System.out.println(sendTotal);
		
		//문자 전송 부분
	    try {
	      DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	      con = DriverManager.getConnection(  
	              "jdbc:mysql://211.48.18.16:3306/test2016", /* DBMS 서버의 주소 및 데이터 베이스 연결 정보 */
	              "test2016", /* 로그인 아이디 */
	              "testpasswd0072");/* 암호 */
	      
	      stmt = con.createStatement();
	      String sendMSG = "test 메시지 입니다.";
	      
	      // 캠페인 등록
	      CampaignVo campaignVo = new CampaignVo();
	      campaignVo.setCamp_content(content);
	      campaignVo.setSender_mob(senderMobile);
	      campaignVo.setCamp_title(campTitle);
	      campaignVo.setUser_no(user.getNo());
	      campaignVo.setAttr_sms("SMS");// 문자 유형 - SMS:4
	      campaignVo.setSend_total(sendTotal);
	      
	      campaignDao.insert(campaignVo);
	      int campNo = campaignDao.getRecentKey();
	      
	      CampaignDetailVo campaignDetailVo = new CampaignDetailVo();
	      
	      
	      

	      // 타겟 목록 INSERT(SMS발송)
			for (Object jsonObj : ja) {
				
				JSONObject jo = (JSONObject)jsonObj;
				String RCVName = (String)jo.get("name");
				String RCVMobile = (String)jo.get("mobile");

				campaignDetailVo.setRcv_name(RCVName);
				campaignDetailVo.setRcv_mob(RCVMobile);
				campaignDetailVo.setCamp_no(campNo);
				
				campaignDetailDao.insert(campaignDetailVo);
				
//				sendMSG = "to: " + RCVName + "\n" + content;// 타겟 이름 포함 해서 전송
				sendMSG = content;
				int count = stmt
						.executeUpdate("INSERT INTO MSG_DATA"
								+ " (CUR_STATE, CALL_TO, CALL_FROM, SMS_TXT,"
								+ " MSG_TYPE, REQ_DATE, MSG_ETC1, MSG_ETC2, MSG_ETC3, MSG_ETC4, MSG_ETC5)"
								+ " VALUES" + " (0, '" + RCVMobile
								+ "','" + senderMobile + "', '" + sendMSG
								+ "', 4, SYSDATE(), 'koh', '" + userEmail + "', " + campNo + ", '"
								+ RCVName + "', " + campaignDetailDao.getMaxKey() + ");");

				if (count == 1) {
					System.out.println("발송 완료 : " + jo.get("mobile"));
				}

			}
	      
	      
	      
	      
	    } catch (SQLException e) {
	      e.printStackTrace(); // 오류에 대한 상세 정보를 콘솔 창에 출력한다.
	    } finally {
	      try {stmt.close();} catch (Exception e) {}
	      try {con.close();} catch (Exception e) {}
	    }
		

//		return responseData;
	    return null;
	}
	
	@RequestMapping(value="/fileTarget", method = RequestMethod.POST)
	@ResponseBody
	public Object fileTargeting(
			MultipartHttpServletRequest request, 
		    HttpServletResponse response,
		    Model model) throws IOException {
		
		
		HashMap<String, String> responseData = new HashMap<String, String>();
	    Iterator<String> itr =  request.getFileNames();
	    MultipartFile file = request.getFile(itr.next());
	    
		 // 업로드 파일의 이름을 생성한다.
	    String originalFilename = file.getOriginalFilename();
	    String ext;
	    
	    //파일 확장자 얻기
	    ext = originalFilename.substring(
	    		originalFilename.lastIndexOf(".") + 1, originalFilename.length());
	    
	    
	    int lastIndexForDot = originalFilename.lastIndexOf(".");
	    String fileName = System.currentTimeMillis() + "-" 
	                      + CommonMethod.count()
	                      + originalFilename.substring(lastIndexForDot);
		

	    file.transferTo(new File(ctx.getRealPath("/upload") + "/" + fileName));
		
		System.out.println(file.getContentType());

		// 확장자별 파일 파싱 처리
		if (ext.equals("csv")) {
			CSVReader csvReader = null;
			String[] row = null;
			JSONArray ja = new JSONArray();
			JSONObject jo;
			
			try {
				
				csvReader = new CSVReader(
						new FileReader(ctx.getRealPath("/upload") + "/" + fileName));
				
				while ((row = csvReader.readNext()) != null) {
					jo = new JSONObject();
					jo.put("name", row[0]);
					jo.put("mobile", row[1]);
					ja.add(jo);
				}
				
				
				model.addAttribute("fileName", fileName);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				csvReader.close();
			}

			responseData.put("fileList", ja.toString());
			System.out.println(ja.toString());
			
		} else {
			System.out.println("csv파일 아님");
		}
		
		
		
		
	    return responseData;
	}
	
	
	// 오직 한 번에 한 스레드 만이 카운트 값을 얻을 수 있다. 중복 불가!
	int count = 0;
	synchronized private int count() {
		if (count == 100) {
			count = 0;
		}
		return ++count;
	}
	
	public String createNowTime() {
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// mariaDB DATETIME 형식
		String currentTime = sdf.format(dt);
		return currentTime;
	}
	
}
