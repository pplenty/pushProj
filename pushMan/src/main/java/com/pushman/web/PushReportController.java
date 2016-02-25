package com.pushman.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pushman.dao.AppUserDao;
import com.pushman.dao.PushCampaignDao;
import com.pushman.dao.PushCampaignDetailClickDao;
import com.pushman.dao.PushCampaignDetailDao;
import com.pushman.domain.PushCampaignDetailClickVo;
import com.pushman.domain.PushCampaignVo;
import com.pushman.domain.SmsUserVo;
import com.pushman.util.CommonMethod;

@Controller
public class PushReportController {

	@Autowired
	AppUserDao appUserDao;
	@Autowired
	PushCampaignDao pushCampaignDao;
	@Autowired
	PushCampaignDetailDao pushCampaignDetailDao;
	@Autowired
	PushCampaignDetailClickDao pushCampaignDetailClickDao;

	
	// 푸시 기본 캠페인 리스트 페이지
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
		

		// SQL(select문) 조건 parameter 세팅
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
	    sqlParams.put("startIndex", CommonMethod.getStartIndexOfPage(pageNo, pageSize));
	    sqlParams.put("pageSize", pageSize);
	    sqlParams.put("word", word);
	    sqlParams.put("order", order);
		sqlParams.put("user_no", smsUser.getNo());
		
		List<PushCampaignVo> pushList = pushCampaignDao.selectListByPusher(sqlParams);
		int countList = pushCampaignDao.countListByPusher(sqlParams);
		
		String tempReqUid = null;
		for (PushCampaignVo pushCampaignVo : pushList) {
			tempReqUid = CommonMethod.getPushTypeFromReqUid(pushCampaignVo.getCamp_reqUid());
			pushCampaignVo.setCamp_reqUid(tempReqUid);
		}
		
		//View로 값 전달
		model.addAttribute("countList", countList);
		model.addAttribute("list", 		pushList);
		model.addAttribute("pageNo", 	pageNo);
		model.addAttribute("pageSize",  pageSize);
		model.addAttribute("maxPage", 	CommonMethod.countTotalPage(pageSize, countList));

		return "pushReport";
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
		int countList = pushCampaignDetailDao.countListByCamp(sqlParams);
				
		model.addAttribute("countList", countList);
		model.addAttribute("list", 		campDetailList);
		model.addAttribute("pageNo", 	pageNo);
		model.addAttribute("pageSize",  pageSize);
		model.addAttribute("maxPage", 	CommonMethod.countTotalPage(pageSize, countList));

		return "./pushReport_detail";
	}
	
	//캠페인 레포트 리타겟 디테일
	@RequestMapping("/pushListDetail_reTarget")
	public String pushListDetail_reTarget(
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
		
		List<Map<String, Object>> smsDetailList = pushCampaignDetailDao.selectSmsListByCamp(sqlParams);
		int countList = pushCampaignDetailDao.countSmsListByCamp(sqlParams);
		
		model.addAttribute("countList", countList);
		model.addAttribute("list", 		smsDetailList);
		model.addAttribute("pageNo", 	pageNo);
		model.addAttribute("pageSize",  pageSize);
		model.addAttribute("maxPage", 	CommonMethod.countTotalPage(pageSize, countList));

		return "./pushReport_detail_sms";
	}
	

	//캠페인 레포트 클릭 통계 디테일
	@RequestMapping("/pushListDetail_click")
	public String pushListDetail_click(
			@RequestParam(required = false, defaultValue = "1") int pageNo,
			@RequestParam(required = false, defaultValue = "10") int pageSize,
			@RequestParam(required = false) String word,
			@RequestParam(required = false) String order, 
			Model model, int cno, HttpSession session)
			throws Exception {

		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		if (smsUser == null) return "index"; // 세션 없을 시 메인으로
		model.addAttribute("name", smsUser.getName());
		
		//
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("startIndex", CommonMethod.getStartIndexOfPage(pageNo, pageSize));
		sqlParams.put("pageSize", pageSize);
		sqlParams.put("word", word);
		sqlParams.put("order", order);
		sqlParams.put("camp_id", cno);
		sqlParams.put("msg_push_type", "M");
		
		
		List<PushCampaignDetailClickVo> msgLinkList = pushCampaignDetailClickDao.selectLinkCntListByCamp(sqlParams);
		
		sqlParams.put("msg_push_type", "P");
		List<PushCampaignDetailClickVo> popupLinkList = pushCampaignDetailClickDao.selectLinkCntListByCamp(sqlParams);
				
		model.addAttribute("cntMsgLink", 	msgLinkList.size());
		model.addAttribute("cntpopupLink", 	popupLinkList.size());
		model.addAttribute("msgLinkList",	msgLinkList);
		model.addAttribute("popupLinkList",	popupLinkList);
		model.addAttribute("campId", cno);
//		model.addAttribute("pageNo", 		pageNo);
//		model.addAttribute("pageSize",  	pageSize);
//		model.addAttribute("maxPage", 		CommonMethod.countTotalPage(pageSize, pushList.size()));

		return "./pushReport_detail_click";
	}
}
