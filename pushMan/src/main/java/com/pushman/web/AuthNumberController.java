package com.pushman.web;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pushman.dao.AuthNumberDao;
import com.pushman.domain.AuthNumberVo;
import com.pushman.util.PushSetting;

@Controller
public class AuthNumberController {

	@Autowired
	AuthNumberDao authNumberDao;

	// 회원 가입 시 인증번호 생성 후 발송 URL
	@RequestMapping("/authNo")
	@ResponseBody
	public Object authNo(
			String mobile, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session) {
		
	    Connection con = null;
	    Statement stmt = null;
	    
	    //
	    HashMap<String, String> sqlParams = new HashMap<String, String>();
	    AuthNumberVo authNumberVo = new AuthNumberVo();

	    do {
	    	String tempAuthNum = createAuthNumber(); // 인증번호  생성
			sqlParams.put("auth_number", tempAuthNum);
			authNumberVo.setAuthNumber(tempAuthNum);
			

		} while (authNumberDao.selectOne(sqlParams) != null);
    	
	    
	    authNumberDao.insert(authNumberVo);
	    System.out.println("인증번호 생성 : [" + authNumberVo.getAuthNumber() + "] to " + mobile);
	    
	    
	    //인증번호 문자 전송 부분
	    try {
	      DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	      con = DriverManager.getConnection(  
	              "jdbc:mysql://211.48.18.16:3306/test2016", /* DBMS 서버의 주소 및 데이터 베이스 연결 정보 */
	              "test2016", /* 로그인 아이디 */
	              "testpasswd0072");/* 암호 */
	      
	      stmt = con.createStatement();
	      String sendMSG = "[PUSH MAN]\n인증번호는 ["
	    		  			+ authNumberVo.getAuthNumber() +"] 입니다. 정확히 입력해주세요.";

	      int count = stmt.executeUpdate(
	              "INSERT INTO MSG_DATA"
	              + " (CUR_STATE, CALL_TO, CALL_FROM, SMS_TXT, MSG_TYPE, REQ_DATE, MSG_ETC1)"
	              + " VALUES"
	              + " (0, '" + mobile + "','" + PushSetting.TEL_PUSHMAN + "', '" 
	              + sendMSG + "', 4, SYSDATE(), '" + PushSetting.SMS_SENDER_KEY + "');");
	      
	      
	      if (count == 1) {
	        System.out.println("발송 완료 : " + mobile);
	      }
	      
	      
	    } catch (SQLException e) {
	      e.printStackTrace(); // 오류에 대한 상세 정보를 콘솔 창에 출력한다.
	    } finally {
	      try {stmt.close();} catch (Exception e) {}
	      try {con.close();} catch (Exception e) {}
	    }
	    
	    HashMap<String, String> responseData = new HashMap<String, String>();
	    responseData.put("authNum", authNumberVo.getAuthNumber());
		

		return responseData;
	}
	
	// 임시로 생성된 인증번호 삭제하는 URL
	@RequestMapping("/authNoDelete")
	@ResponseBody
	public Object authNoDelete(
			String authNumber, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session) {
		
	    AuthNumberVo authNumberVo = new AuthNumberVo();
		authNumberVo.setAuthNumber(authNumber);

		
		System.out.println("인증번호 만료 : [" + authNumberVo.getAuthNumber() + "]");
	    authNumberDao.delete(authNumberVo);
	    

		return null;
	}
	
	
	// 4자리 인증번호 문자열 생성 함수 
	public String createAuthNumber() {
		String authNumber = "";
		
		for (int i = 0; i < 4; i++) {
			authNumber += (int)Math.floor(Math.random() * 10d);
		}
		return authNumber;
	}

}
