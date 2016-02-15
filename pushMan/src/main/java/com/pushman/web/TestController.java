package com.pushman.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pushman.domain.AppUserVo;
import com.pushman.domain.SmsUserVo;
import com.pushman.dao.AppUserDao;
import com.pushman.dao.PushCampaignDao;
import com.pushman.dao.PushCampaignDetailDao;
import com.pushman.util.CommonMethod;



@Controller
public class TestController {

	@Autowired
	AppUserDao appUserDao;
	@Autowired
	PushCampaignDao pushCampaignDao;
	@Autowired
	PushCampaignDetailDao pushCampaignDetailDao;
	
	@RequestMapping("/test")
	@ResponseBody
	//理쒖큹 ???ㅽ뻾???먮룞 濡쒓렇??
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
	
	@RequestMapping("/test2")
	@ResponseBody
	//?깆쓣 ?ㅽ뻾???뚮쭏??理쒓렐 ?묒냽 ?쒓컙 ?낅뜲?댄듃
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
	
	@RequestMapping("/pushListDetail")
	public String pushListDetail(
			@RequestParam(required = false, defaultValue = "1") int pageNo,
			@RequestParam(required = false, defaultValue = "10") int pageSize,
			@RequestParam(required = false) String word,
			@RequestParam(required = false) String order, 
			Model model, int cno, HttpSession session)
			throws Exception {

		// sqlParams??媛믩떞?꾩꽌 ?섍?
		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		if (smsUser == null) return "index";// ?몄뀡 ?놁쓣 ??硫붿씤?쇰줈
		model.addAttribute("name", smsUser.getName());
		
		// SQL(select臾? 議곌굔 parameter ?명똿
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("startIndex", CommonMethod.getStartIndexOfPage(pageNo, pageSize));
		sqlParams.put("pageSize", pageSize);
		sqlParams.put("word", word);
		sqlParams.put("order", order);
		sqlParams.put("camp_id", cno);
				
//		model.addAttribute("countList", pushList.size());
		model.addAttribute("list", 		pushCampaignDetailDao.selectListByCamp(sqlParams));
//		model.addAttribute("pageNo", 	pageNo);
//		model.addAttribute("pageSize",  pageSize);
//		model.addAttribute("maxPage", 	CommonMethod.countTotalPage(pageSize, pushList.size()));

		return "./pushReport_detail";
	}
}
