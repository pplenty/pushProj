package com.pushman.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.pushman.domain.PushCampaignDetailVo;
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
	
	@RequestMapping("/test2")
	@ResponseBody
	// 앱을 실행할 때마다 모바일 번호를 저장함
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
	
	//캠페인 레포트 디테일
	@RequestMapping("/pushListDetail")
	public String pushListDetail(
			@RequestParam(required = false, defaultValue = "1") int pageNo,
			@RequestParam(required = false, defaultValue = "10") int pageSize,
			@RequestParam(required = false) String word,
			@RequestParam(required = false) String order, 
			Model model, int cno, HttpSession session)
			throws Exception {

		//
		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		if (smsUser == null) return "index";
		model.addAttribute("name", smsUser.getName());
		
		//
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("startIndex", CommonMethod.getStartIndexOfPage(pageNo, pageSize));
		sqlParams.put("pageSize", pageSize);
		sqlParams.put("word", word);
		sqlParams.put("order", order);
		sqlParams.put("camp_id", cno);
		
		List<Map<String, Object>> campDetailList = pushCampaignDetailDao.selectListByCamp(sqlParams);
//		List<String> mobileList = new ArrayList<String>();
				
//		AppUserVo appUserVo;
//		for (PushCampaignDetailVo pushCampaignDetailVo : campDetailList) {
//			appUserVo = appUserDao.selectOneByUserId(pushCampaignDetailVo.getUser_id());
//			mobileList.add(appUserVo.getMobile());
//		}
//		model.addAttribute("countList", pushList.size());
		model.addAttribute("list", 		campDetailList);
//		model.addAttribute("mobileList", mobileList);
//		model.addAttribute("pageNo", 	pageNo);
//		model.addAttribute("pageSize",  pageSize);
//		model.addAttribute("maxPage", 	CommonMethod.countTotalPage(pageSize, pushList.size()));

		return "./pushReport_detail";
	}
}
