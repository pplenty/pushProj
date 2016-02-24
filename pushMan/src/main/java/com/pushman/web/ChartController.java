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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pushman.dao.PushCampaignDetailClickDao;
import com.pushman.dao.PushCampaignDetailDao;
import com.pushman.domain.PushCampaignDetailClickVo;
import com.pushman.domain.SmsUserVo;

@Controller
public class ChartController {
	@Autowired
	PushCampaignDetailDao pushCampaignDetailDao;
	@Autowired
	PushCampaignDetailClickDao pushCampaignDetailClickDao;
	
	//통계 페이지 이동
	@RequestMapping("/chartPage")
	public String pushPage(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) {

		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		if (smsUser == null) return "index";// 세션 없을 시 메인으로
		model.addAttribute("name", smsUser.getName());

		return "chartPage";
	}


	// 클릭 차트
	@RequestMapping("/clickChart")
	@ResponseBody
	public Object clickChart(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		// 링크에 따른 클릭 갯수 DB에서 가져오기
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("camp_id", request.getParameter("camp_id"));
		sqlParams.put("msg_push_type", "M");
		List<PushCampaignDetailClickVo> list = pushCampaignDetailClickDao.selectLinkCntListByCamp(sqlParams);

		JsonArray jsonResMsgData = new JsonArray();
		JsonObject item;
		HashMap<String, Object> responseData = new HashMap<String, Object>();
		// 클릭갯수 JSON 배열에 담기(메시지)
		for (PushCampaignDetailClickVo pushCampaignDetailClickVo : list) {
			item = new JsonObject();
			item.addProperty("linkSeq", pushCampaignDetailClickVo.getLink_seq());// linkSeq
			item.addProperty("linkAddr", pushCampaignDetailClickVo.getLink());// linkAddr
			item.addProperty("clickCnt", pushCampaignDetailClickVo.getClick_cnt());// clickCnt
			jsonResMsgData.add(item);
		}
		// 클릭갯수 JSON 배열에 담기(팝업)
		sqlParams.put("msg_push_type", "P");
		list = pushCampaignDetailClickDao.selectLinkCntListByCamp(sqlParams);
		JsonArray jsonResPopupData = new JsonArray();
		for (PushCampaignDetailClickVo pushCampaignDetailClickVo : list) {
			item = new JsonObject();
			item.addProperty("linkSeq", pushCampaignDetailClickVo.getLink_seq());// linkSeq
			item.addProperty("linkAddr", pushCampaignDetailClickVo.getLink());// linkAddr
			item.addProperty("clickCnt", pushCampaignDetailClickVo.getClick_cnt());// clickCnt
			jsonResPopupData.add(item);
		}

		// AJAX 응답으로 꺼내온 값 보내기
		responseData.put("msgList", jsonResMsgData.toString());
		responseData.put("popupList", jsonResPopupData.toString());
		return responseData;

	}

	// 푸시 전체 차트 
	@RequestMapping("/pushChart")
	@ResponseBody
	public Object pushChart(
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session) {
		
		SmsUserVo smsUser = (SmsUserVo)session.getAttribute("user");
		if (smsUser == null) return "index";// 세션 없을 시 메인으로
		
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		
		// from, to 둘다 NULL인 경우
		if (fromDate == null && toDate == null) {
			fromDate = "";
			toDate = "";
			
		// 날짜를 하나만 입력한 경우
		} else if (fromDate == "") {
			fromDate = toDate;
		} else if (toDate == "") {
			toDate = fromDate;
		}

		// 링크에 따른 클릭 갯수 DB에서 가져오기
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("user_id", smsUser.getNo());
		sqlParams.put("rtn_type", "R");
		// 추가로 날짜 에 따른 WHERE절 추가 
		sqlParams.put("fromDate", fromDate);
		sqlParams.put("toDate", toDate);
		
		// 시간대에 따른 "READ" 카운트 리스트
		List<HashMap<String, Integer>> readCntList = pushCampaignDetailDao.selectReadCntByPusher(sqlParams);
		
		JsonObject jsonReadData = new JsonObject();
		
		// 클릭갯수 JSON 배열에 담기(메시지)
		for (HashMap<String, Integer> map : readCntList) {
			jsonReadData.addProperty(String.valueOf(map.get("reg_time")), map.get("count_sum"));
		}
		

		// 시간대에 따른 "CLICK" 카운트 리스트
		sqlParams.put("rtn_type", "C");
		List<HashMap<String, Integer>> clickCntList = pushCampaignDetailDao.selectReadCntByPusher(sqlParams);
		
		JsonObject jsonClickData = new JsonObject();
		
		// 클릭갯수 JSON 배열에 담기(메시지)
		for (HashMap<String, Integer> map : clickCntList) {
			jsonClickData.addProperty(String.valueOf(map.get("reg_time")), map.get("count_sum"));
		}
		

		
		
		
		// AJAX 응답으로 꺼내온 값 보내기
		HashMap<String, Object> responseData = new HashMap<String, Object>();
		responseData.put("readCntList", jsonReadData.toString());
		responseData.put("clickCntList", jsonClickData.toString());
	    return responseData;
	    
	}
}
