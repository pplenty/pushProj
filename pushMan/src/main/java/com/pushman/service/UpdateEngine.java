package com.pushman.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pushman.dao.AppUserDao;
import com.pushman.dao.PushCampaignDetailDao;
import com.pushman.dao.TB_SEND_QUE_LOG_Dao;
import com.pushman.domain.PushCampaignDetailVo;
import com.pushman.domain.TB_SEND_QUE_LOG_Vo;
import com.pushman.util.CommonMethod;
import com.pushman.util.MultipleDataSource;
import com.pushman.util.PushSetting;

@Service
public class UpdateEngine {
	@Autowired
	AppUserDao appUserDao;
	@Autowired
	PushCampaignDetailDao pushCampaignDetailDao;
	@Autowired
	TB_SEND_QUE_LOG_Dao tbSendQueLogDao;
	
	// 푸시 로그 스케줄러
	 @Scheduled(fixedDelay = 5000)
	 public void updatePushLogSchedular() throws RuntimeException {

		 // 데이터소스 SET - 로컬DB
		 MultipleDataSource.setDataSourceKey("localDB");
		 List<PushCampaignDetailVo> isNullList = pushCampaignDetailDao.selectListLogIsNull();
		 
		 int maxLogId = 0;
		 
		 // 초기 업데이트가 안되 있는 경우 getMaxLogId NULL 예외 발생
		 try {
			 maxLogId = pushCampaignDetailDao.getMaxLogId();
		} catch (Exception e) {
			maxLogId = -1;
		}
		 
		 
		 // 업데이트 된 로그가 있을 때 
		if (!isNullList.isEmpty()) {

			// 데이터소스 SET - 푸시피아 DB
			MultipleDataSource.setDataSourceKey("pushpiaDB");
			HashMap<String, Object> sqlParams = new HashMap<String, Object>();
			sqlParams.put("text_biz_id", PushSetting.TEXT_PUSH_BIZ_KEY);
			sqlParams.put("rich_biz_id", PushSetting.RICH_PUSH_BIZ_KEY);
			sqlParams.put("rtn_type", "S");
			List<TB_SEND_QUE_LOG_Vo> pushLogList = tbSendQueLogDao.selectListByRtnType(sqlParams);
			
			System.out.println(pushLogList.get(0));
			PushCampaignDetailVo pushCampaignDetailVo;
			for (TB_SEND_QUE_LOG_Vo tb_SEND_QUE_LOG_Vo : pushLogList) {
				pushCampaignDetailVo = new PushCampaignDetailVo();
				pushCampaignDetailVo.setReg_date(tb_SEND_QUE_LOG_Vo.getReg_date());
				pushCampaignDetailVo.setReqUid(tb_SEND_QUE_LOG_Vo.getReq_uid());
				pushCampaignDetailVo.setRtn_type(tb_SEND_QUE_LOG_Vo.getRtn_type());
				pushCampaignDetailVo.setRes_cd(tb_SEND_QUE_LOG_Vo.getRes_cd());
				pushCampaignDetailVo.setPush_log_id(tb_SEND_QUE_LOG_Vo.getSend_que_id());
				pushCampaignDetailVo.setCamp_id(getCampIdFromReqUid(pushCampaignDetailVo.getReqUid()));
				pushCampaignDetailVo.setCd_id(getCdIdFromReqUid(pushCampaignDetailVo.getReqUid()));

				// 데이터소스 SET - 로컬 DB
				MultipleDataSource.setDataSourceKey("localDB");
				// 
				pushCampaignDetailVo.setUser_id(
						appUserDao.selectOneByCustId(tb_SEND_QUE_LOG_Vo.getCust_id()).getUser_id());
				
				
			}

		}
		 
//		 System.out.println(pushLogList.size());
		 
		 
	 }
	 
	// 매일 새벽 2시에 로그인 횟수 초기화
//	 @Scheduled(cron="0 0 02 * * ?")
//	 public void HOWSchedular() throws RuntimeException {
//		 System.out.println("02시 00분");
//	 }
	 
	 
	// ReqUid에서 cd_id 뽑기
	public int getCdIdFromReqUid(String ReqUid) {
		String[] resultSet = ReqUid.split("_");
		int result = 0;
		try {
			result =  Integer.parseInt(resultSet[resultSet.length - 1]);
		} catch (Exception e) {
			return 0;
		}
		return result;
	}

	// ReqUid에서 camp_id 뽑기(project_pushType_campId_cdId)
	public int getCampIdFromReqUid(String ReqUid) {
		String[] resultSet = ReqUid.split("_");
		int result = 0;
		try {
			result =  Integer.parseInt(resultSet[resultSet.length - 2]);
		} catch (Exception e) {
			return 0;
		}
		return result;
	}
	
}
