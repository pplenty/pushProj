package com.pushman.web;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pushman.dao.MSG_LOG_Dao;
import com.pushman.dao.SmsUserDao;
import com.pushman.dao.TB_SEND_QUE_LOG_Dao;
import com.pushman.domain.SmsUserVo;
import com.pushman.domain.TB_SEND_QUE_LOG_Vo;
import com.pushman.util.MultipleDataSource;

@Controller
public class LoginController {
	@Autowired
	SmsUserDao smsUserDao;
	@Autowired
	MSG_LOG_Dao msg_log_dao;
	@Autowired
	TB_SEND_QUE_LOG_Dao tb_send_que_log_dao;
	
	@RequestMapping("/ma")
	public String ma(
			HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) {
		
		MultipleDataSource.setDataSourceKey("iHeartDB");
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
//		List<MSG_LOG_Vo> list = msg_log_dao.selectListAll(hm);
//		System.out.println(list.get(0));
		
		MultipleDataSource.setDataSourceKey("pushpiaDB");
		
		List<TB_SEND_QUE_LOG_Vo> list2 = tb_send_que_log_dao.selectListAll(null);
		for (TB_SEND_QUE_LOG_Vo tb_SEND_QUE_LOG_Vo : list2) {
			System.out.println(tb_SEND_QUE_LOG_Vo.getReg_date());
		}
		
		
		
		
		return null;
	}
	
	

	@RequestMapping("/main")
	public String smsMain(
			HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) {
//		MultipleDataSource.setDataSourceKey("localDB");
		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		if (smsUser != null) return "redirect:chartPage.do";// 이미 로그인 되있을 때
		
		return "index";
	}
	
	// 로그인 부분
	@RequestMapping("/login2")
	@ResponseBody
	public Object login2(String email, String password, String loginkeeping,
			HttpServletRequest request, HttpServletResponse response, 
			HttpSession session, Model model) {


		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		if (smsUser != null) return "redirect:pushList.do";// 이미 로그인 되어있을 때
		
		// loginkeeping 체크상자
		if (loginkeeping != null) { // 체크 상자를 체크 했으면,
			// 클라이언트로 사용자 이메일을 쿠키에 담아 보낸다.
			Cookie emailCookie = new Cookie("email", email);
			emailCookie.setMaxAge(60 * 60 * 24 * 30); // 30일간 쿠키 유지
			response.addCookie(emailCookie);
		} else {
			// 기존의 이메일 삭제
			Cookie emailCookie = new Cookie("email", "");
			emailCookie.setMaxAge(0); // 쿠키 삭제
			response.addCookie(emailCookie);
		}
		
		// 사용자 검증
		HashMap<String, String> sqlParams = new HashMap<String, String>();
		sqlParams.put("email", email);

		SmsUserVo user = smsUserDao.selectOneforEmail(sqlParams);
		

		HashMap<String, String> responseData = new HashMap<String, String>();
		
		
		if (user == null) {// 없는 이메일일 떄
			responseData.put("status", "noEmail");
		} else if (!password.equals(user.getPassword())) {// 비번 불일치
			responseData.put("status", "wrongPwd");
		} else { // 이메일과 암호가 일치하는 사용자를 찾았다면,
		    session.setAttribute("user", user); // 로그인 성공 => 사용자 정보를 세션에 보관한다.
			responseData.put("status", "isMatched");
			
			user = (SmsUserVo)session.getAttribute("user");
			System.out.println(user.getEmail() + ": 로그인 / 세션 저장 완료");
		}
		

		return responseData;
	}
	

	// 회원가입 
	@RequestMapping("/signup")
	public String signup(String emailsignup, String usernamesignup, String passwordsignup,  
			String mobilesignup, String authNumber,
			HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) {

		SmsUserVo user = new SmsUserVo();
		user.setEmail(emailsignup);
		user.setName(usernamesignup);
		user.setPassword(passwordsignup);
		user.setMobile(mobilesignup);

		smsUserDao.insert(user);
		System.out.println("등록완료 : " + emailsignup);
		

		Cookie emailCookie = new Cookie("email", emailsignup);
		emailCookie.setMaxAge(60 * 60 * 24 * 30); // 30일간 쿠키 유지
		response.addCookie(emailCookie);
		

		return "redirect:main.do";
	}
	

	//이메일 중복 검사
	@RequestMapping("/duplEmail")
	@ResponseBody
	public Object duplEmail(
			String email,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session) {
		
		HashMap<String, String> sqlParams = new HashMap<String, String>();
		sqlParams.put("email", email);
		System.out.println(email+" 중복검사");

		SmsUserVo user = smsUserDao.selectOneforEmail(sqlParams);
	    
		HashMap<String, String> responseData = new HashMap<String, String>();
		if (user == null) {
			responseData.put("checkEmail", "ok");
		}
		else {
			responseData.put("checkEmail", "duplication");
		}
				
	    return responseData;
	    
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:main.do";
	}



}
