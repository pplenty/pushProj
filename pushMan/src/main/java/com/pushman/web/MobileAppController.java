package com.pushman.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pushman.dao.AppUserDao;
import com.pushman.domain.AppUserVo;


// 모바일 앱 구동 시 사용자 정보 받는 컨트롤러
@Controller
public class MobileAppController {

	@Autowired
	AppUserDao appUserDao;
	
	// cust id와 모바일 번호 앱으로부터 요청 받아 로컬 디비에 저장
	@RequestMapping("/userLogin")
	@ResponseBody
	//최초 앱 실행시 모바일 사용자의 정보를 로컬 DB에 저장
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String custId = request.getParameter("id");
		String phoneNum = request.getParameter("mobile");

		System.out.println(custId);
		System.out.println(phoneNum);
		
		AppUserVo appUserVo = new AppUserVo();
		appUserVo.setCust_id(custId);
		appUserVo.setMobile(phoneNum);
		
		appUserDao.insert(appUserVo);
	}
	
	@RequestMapping("/userUpdate")
	@ResponseBody
	
	// 앱을 실행할 때마다 최근 로그인 일시를 업데이트
	protected void doGetConnect(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String mobile = request.getParameter("mobile");
		String connect = request.getParameter("connect");

		System.out.println(mobile);
		System.out.println(connect);
		
		AppUserVo appUserVo = new AppUserVo();
		appUserVo.setMobile(mobile);
		
		appUserDao.update(appUserVo);
	}
}
