package com.pushman.scheduler;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pushman.dao.AppUserDao;
import com.pushman.dao.MSG_DATA_Dao;
import com.pushman.dao.MSG_LOG_Dao;
import com.pushman.dao.PushCampaignDao;
import com.pushman.dao.PushCampaignDetailClickDao;
import com.pushman.dao.PushCampaignDetailDao;
import com.pushman.dao.SmsDetailDao;
import com.pushman.dao.SmsUserDao;
import com.pushman.dao.TB_SEND_QUE_LOG_Dao;
import com.pushman.domain.PushCampaignDetailClickVo;
import com.pushman.domain.PushCampaignDetailVo;
import com.pushman.domain.TB_SEND_QUE_LOG_Vo;
import com.pushman.util.MultipleDataSource;
import com.pushman.util.PushSetting;

@Service
public class PushReadUpdateEngine {
	@Autowired
	AppUserDao appUserDao;
	@Autowired
	SmsUserDao smsUserDao;
	@Autowired
	PushCampaignDao pushCampaignDao;
	@Autowired
	PushCampaignDetailDao pushCampaignDetailDao;
	@Autowired
	PushCampaignDetailClickDao pushCampaignDetailClickDao;
	@Autowired
	TB_SEND_QUE_LOG_Dao tbSendQueLogDao;
	@Autowired
	MSG_DATA_Dao msgDataDao;
	@Autowired
	MSG_LOG_Dao msgLodDao;
	@Autowired
	SmsDetailDao smsDetailDao;
	
	// 푸시 로그 스케줄러('READ')
	@Scheduled(fixedDelay = SchedulerCommon.FIXED_DELAY)
	public void updatePushReadLogSchedular() throws RuntimeException {

		// 데이터소스 SET - 로컬DB
		MultipleDataSource.setDataSourceKey("localDB");
		
		int maxLocalLogId = 0;
		int maxExLogId = 0;

		// 초기 업데이트가 안되 있는 경우 getMaxLogId NULL 예외 발생
		try {
			maxLocalLogId = pushCampaignDetailDao.getMaxLogId();
		} catch (Exception e) {
			return;
		}

		// 데이터소스 SET - 푸시피아 DB
		MultipleDataSource.setDataSourceKey("pushpiaDB");
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		//
		sqlParams.put("text_biz_id", PushSetting.TEXT_PUSH_BIZ_KEY);
		sqlParams.put("rich_biz_id", PushSetting.RICH_PUSH_BIZ_KEY);
//		sqlParams.put("maxLogId", maxLogId);
		maxExLogId = tbSendQueLogDao.getMaxLogId(sqlParams);
		
		if (maxLocalLogId < maxExLogId) {
			// 로그 업데이트 실행
			sqlParams.put("maxID", maxLocalLogId);
			// RTN_TYPE이 READ, CLICK인 로그
			List<TB_SEND_QUE_LOG_Vo> pushLogList = tbSendQueLogDao.selectListByRtnType2(sqlParams);
			
//			System.out.println(pushLogList.size());

			// 데이터소스 SET - 로컬DB
			MultipleDataSource.setDataSourceKey("localDB");
			PushCampaignDetailVo pushCampaignDetailVo;
			for (TB_SEND_QUE_LOG_Vo tb_SEND_QUE_LOG_Vo : pushLogList) {
				pushCampaignDetailVo = new PushCampaignDetailVo();
				pushCampaignDetailVo.setReg_date(tb_SEND_QUE_LOG_Vo.getReg_date());
				pushCampaignDetailVo.setReqUid(tb_SEND_QUE_LOG_Vo.getReq_uid());
				pushCampaignDetailVo.setRtn_type(tb_SEND_QUE_LOG_Vo.getRtn_type());
				pushCampaignDetailVo.setRes_cd(tb_SEND_QUE_LOG_Vo.getRes_cd());
				pushCampaignDetailVo.setPush_log_id(tb_SEND_QUE_LOG_Vo.getSend_que_id());
				pushCampaignDetailVo.setCamp_id(SchedulerCommon.getCampIdFromReqUid(pushCampaignDetailVo.getReqUid()));
//				pushCampaignDetailVo.setCd_id(SchedulerCommon.getCdIdFromReqUid(pushCampaignDetailVo.getReqUid())); 
				if (pushCampaignDetailVo.getRtn_type() == 'C')
					pushCampaignDetailVo.setTb_click_id(Integer.parseInt(tb_SEND_QUE_LOG_Vo.getData()));
				String custId = tb_SEND_QUE_LOG_Vo.getCust_id();
				// 로그 예외처리
				if (appUserDao.selectOneByCustId(custId) != null) {
					pushCampaignDetailVo.setUser_id(appUserDao.selectOneByCustId(custId).getUser_id());
				}
				pushCampaignDetailDao.insertLog(pushCampaignDetailVo);
				
				//CLICK 인 경우 
				PushCampaignDetailClickVo pushCampaignDetailClickVo = new PushCampaignDetailClickVo();
				if (pushCampaignDetailVo.getRtn_type() == 'C') {
					// 데이터소스 SET - 푸시피아 DB
					MultipleDataSource.setDataSourceKey("pushpiaDB");
					
					HashMap<String, String> updatedClickLog = new HashMap<String,String>();
					HashMap<String, Object> sqlParams2 = new HashMap<String, Object>();
					//sql문 parameter 설정
					sqlParams2.put("TB_CLICK_ID", tb_SEND_QUE_LOG_Vo.getData());
					
					updatedClickLog = pushCampaignDetailClickDao.selectOneClickLogById(sqlParams2);
					pushCampaignDetailClickVo.setTb_click_id(Long.parseLong(String.valueOf(sqlParams2.get("TB_CLICK_ID"))));
					pushCampaignDetailClickVo.setClick_cnt(Integer.parseInt(String.valueOf(updatedClickLog.get("CLICK_CNT"))));
					pushCampaignDetailClickVo.setUpt_date(String.valueOf(updatedClickLog.get("UPT_DATE")));
					pushCampaignDetailClickVo.setCamp_id(pushCampaignDetailVo.getCamp_id());

					// 데이터소스 SET - 로컬DB
					MultipleDataSource.setDataSourceKey("localDB");
					pushCampaignDetailClickDao.updateClickLog(pushCampaignDetailClickVo);
					
				}
				
				// 캠페인 리드/클릭 업데이트
				HashMap<String, Object> sqlParams2 = new HashMap<String, Object>();
				sqlParams2.put("res_cd", pushCampaignDetailVo.getRes_cd());
				sqlParams2.put("rtn_type", pushCampaignDetailVo.getRtn_type());
				sqlParams2.put("camp_id", pushCampaignDetailVo.getCamp_id());
				pushCampaignDao.updateResult(sqlParams2);
			}
		}
		

	}
}
