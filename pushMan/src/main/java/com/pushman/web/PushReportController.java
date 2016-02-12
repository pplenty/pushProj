package com.pushman.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pushman.dao.AppUserDao;
import com.pushman.domain.SmsUserVo;

@Controller
public class PushReportController {

	@Autowired
	AppUserDao appUserDao;

	@RequestMapping("/pushList")
	public String pushPage(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) {

		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		if (smsUser == null) return "index";// 세션 없을 시 메인으로
		model.addAttribute("name", smsUser.getName());

		return "pushReport";
	}
}
