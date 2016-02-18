package com.pushman.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pushman.dao.PushCampaignDetailClickDao;
import com.pushman.domain.PushCampaignDetailClickVo;
import com.pushman.util.MultipleDataSource;

@Service
public class PushClickUpdateEngine {
	@Autowired
	PushCampaignDetailClickDao pushCampaignDetailClickDao;
	
	
	// CLICK TABLE 바라보는 엔진
	@Scheduled(fixedDelay = SchedulerCommon.FIXED_DELAY)
	public void updateClickLog() throws RuntimeException {
		// 데이터소스 SET - 로컬DB
		MultipleDataSource.setDataSourceKey("localDB");
		
		int maxLocalLogId = 0;
		int maxExLogId = 0;

		// 초기 업데이트가 안되 있는 경우 getMaxLogId NULL 예외 발생
		try {
			maxLocalLogId = pushCampaignDetailClickDao.getLocalMaxLogId();
		} catch (Exception e) {
			maxLocalLogId = 0;
		}

		// 데이터소스 SET - 푸시피아 DB
		MultipleDataSource.setDataSourceKey("pushpiaDB");
		maxExLogId = pushCampaignDetailClickDao.getPushpiaMaxLogId();
		//HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		

		// 로그 업데이트가 필요할 경우
		if (maxExLogId > maxLocalLogId) {
			
			// 클릭 로그 가져오는 SELECT문
			List<HashMap<String, String>> clickLogList = new ArrayList<HashMap<String,String>>();
			clickLogList = pushCampaignDetailClickDao.getPushpiaClickLog(maxLocalLogId);
			PushCampaignDetailClickVo pushCampaignDetailClickVo = null;
			
			
			// 데이터소스 SET - 로컬DB
			MultipleDataSource.setDataSourceKey("localDB");
			for (HashMap<String, String> clickLog : clickLogList) {
				
				
				pushCampaignDetailClickVo = new PushCampaignDetailClickVo();
				pushCampaignDetailClickVo.setTb_click_id(Long.parseLong(String.valueOf(clickLog.get("ID"))));
				pushCampaignDetailClickVo.setLink_seq(Integer.parseInt(String.valueOf(clickLog.get("LINK_SEQ"))));
				pushCampaignDetailClickVo.setLink(clickLog.get("LINK"));
				pushCampaignDetailClickVo.setMsg_push_type(clickLog.get("MSG_PUSH_TYPE"));
				pushCampaignDetailClickVo.setClick_cnt(Integer.parseInt(String.valueOf(clickLog.get("CLICK_CNT"))));
				pushCampaignDetailClickVo.setReg_date(String.valueOf(clickLog.get("REG_DATE")));
				pushCampaignDetailClickVo.setUpt_date(String.valueOf(clickLog.get("UPT_DATE")));
				
				// 로컬 DB에 클릭 로그 데이터 가공 후 INSERT
				pushCampaignDetailClickDao.insertClickLog(pushCampaignDetailClickVo);
				
				
			}
		}
	}
}
