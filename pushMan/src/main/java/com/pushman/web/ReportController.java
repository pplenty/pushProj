package com.pushman.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pushman.dao.CampaignDao;
import com.pushman.dao.CampaignDetailDao;
import com.pushman.domain.CampaignVo;
import com.pushman.domain.SmsUserVo;
import com.pushman.util.ListPagingUtil;

@Controller
public class ReportController {
	@Autowired
	CampaignDao campaignDao;
	@Autowired
	CampaignDetailDao campaignDetailDao;

	@RequestMapping("/list")
	public String list(
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
		
		List<CampaignVo> campList = campaignDao.selectListByUser(sqlParams);
		HashMap<String, Object> forSqlParams = new HashMap<String, Object>();
		JSONObject jo = new JSONObject();
		JSONObject joErr;
		
		for (CampaignVo campaignVo : campList) {
			
			joErr = new JSONObject();
			
			forSqlParams.put("camp_no", campaignVo.getCamp_no());
			
			//대기중인 건수
			forSqlParams.put("error_type", "9");
			joErr.put("wait", campaignDetailDao.cntRsltByCamp(forSqlParams));
//			System.out.println("대기: " + campaignDetailDao.cntRsltByCamp(forSqlParams));
			
			//성공 건수
			forSqlParams.put("error_type", "0");
			joErr.put("success", campaignDetailDao.cntRsltByCamp(forSqlParams));
//			System.out.println("성공: " + campaignDetailDao.cntRsltByCamp(forSqlParams));
			
			
			jo.put(campaignVo.getCamp_no(), joErr);
			
		}
		
		// 캠페인별 성공률 담아서 넘기기

		

	    int countList = campaignDao.countByUser(sqlParams);
		model.addAttribute("countList", countList);
		
		model.addAttribute("list", campList);
		
		
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("maxPage",
				ListPagingUtil.countTotalPage(pageSize, countList));
		

		model.addAttribute("num_error", jo);// camp별 성공건수가 담긴 맵
		

		return "./report";
	}
	
	@RequestMapping("/listDetail")
	public String listDetail(
			@RequestParam(required = false, defaultValue = "1")  int pageNo,
			@RequestParam(required = false, defaultValue = "10") int pageSize,
			@RequestParam(required = false) 					 String word,
			@RequestParam(required = false) 					 String order, 
			int cno, Model model, HttpSession session) 
					throws Exception {

		// sqlParams에 값담아서 넘김
		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		if (smsUser == null) return "index";// 세션 없을 시
		model.addAttribute("name", smsUser.getName());
		
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
	    sqlParams.put("startIndex", ListPagingUtil.getStartIndexOfPage(pageNo, pageSize));
	    sqlParams.put("pageSize", pageSize);
	    sqlParams.put("word", word);
	    sqlParams.put("order", order);
	    sqlParams.put("camp_no", cno);
	    
	    
	    int countList = campaignDetailDao.countByCamp(sqlParams);
	    
		model.addAttribute("countList", countList);
		model.addAttribute("list", campaignDetailDao.selectListByCamp(sqlParams));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("maxPage",
				ListPagingUtil.countTotalPage(pageSize, countList));
		model.addAttribute("cno", cno);
		model.addAttribute("order", order);

		return "./report_detail";
	}
	
	
	
	// TEST
	@RequestMapping("/chart")
	@ResponseBody
	public Object getChartDate(
			HttpServletRequest request, HttpSession session, Model model)
					throws Exception {
		
		List<Map<String, Object>> totalList = campaignDao.countTotalDaily();
		
		
		JSONObject jo = new JSONObject();
		for (Map<String, Object> map : totalList) {
			jo.put(map.get("DAY"), map.get("TOTAL"));
		}
		
		
		
		System.out.println(totalList);
		System.out.println(totalList.get(0).get("DAY"));
		System.out.println(jo);
		
		HashMap<String, String> responseData = new HashMap<String, String>();
	    responseData.put("totalMapList", jo.toString());

		return responseData;
	}
	
	@RequestMapping("/showChart")
	public String showChart(
			HttpServletRequest request, HttpSession session, Model model)
					throws Exception {
		
		return "./report_chart";
	}
	
	//////////////////////////
	
	

	@RequestMapping("/senderPage")
	public String senderPage(
			HttpServletRequest request, 
			HttpSession session, Model model)
				throws Exception {
		
		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		
		//세션 없을 시 메인으로
		if (smsUser == null) return "index";
		
		model.addAttribute("name", smsUser.getName());
		model.addAttribute("mobile", smsUser.getMobile());

		return "./sendPage";
	}

}
