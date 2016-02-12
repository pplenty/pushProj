package com.pushman.web;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pushman.dao.AppUserDao;
import com.pushman.dao.PushCampaignDao;
import com.pushman.domain.CampaignVo;
import com.pushman.domain.PushCampaignVo;
import com.pushman.domain.SmsUserVo;
import com.pushman.util.ListPagingUtil;

@Controller
public class PushReportController {

	@Autowired
	AppUserDao appUserDao;
	@Autowired
	PushCampaignDao pushCampaignDao;

	@RequestMapping("/pushList")
	public String pushPage(
			@RequestParam(required = false, defaultValue = "1") int pageNo,
			@RequestParam(required = false, defaultValue = "10") int pageSize,
			@RequestParam(required = false) String word,
			@RequestParam(required = false) String order, Model model,
			HttpSession session)
			throws Exception {

		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		if (smsUser == null) return "index";// 세션 없을 시 메인으로
		model.addAttribute("name", smsUser.getName());
		

		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
	    sqlParams.put("startIndex", ListPagingUtil.getStartIndexOfPage(pageNo, pageSize));
	    sqlParams.put("pageSize", pageSize);
	    sqlParams.put("word", word);
	    sqlParams.put("order", order);
		sqlParams.put("user_no", smsUser.getNo());
		
		List<PushCampaignVo> pushList = pushCampaignDao.selectList(sqlParams);

		return "pushReport";
	}
}
