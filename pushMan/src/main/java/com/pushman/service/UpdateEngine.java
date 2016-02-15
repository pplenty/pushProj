package com.pushman.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
		 
		 
		 // 업데이트 된 게 있을 때 
		if (!isNullList.isEmpty()) {

			// 데이터소스 SET - 푸시피아 DB
			MultipleDataSource.setDataSourceKey("pushpiaDB");
			HashMap<String, Object> sqlParams = new HashMap<String, Object>();
			sqlParams.put("text_biz_id", PushSetting.TEXT_PUSH_BIZ_KEY);
			sqlParams.put("rich_biz_id", PushSetting.RICH_PUSH_BIZ_KEY);
			sqlParams.put("rtn_type", "S");
			List<TB_SEND_QUE_LOG_Vo> pushLogList = tbSendQueLogDao.selectListByRtnType(sqlParams);
			
			System.out.println(pushLogList.get(0));
			for (TB_SEND_QUE_LOG_Vo tb_SEND_QUE_LOG_Vo : pushLogList) {
				tb_SEND_QUE_LOG_Vo.getReg_date();
				tb_SEND_QUE_LOG_Vo.getReq_uid();
				tb_SEND_QUE_LOG_Vo.getRtn_type();
				tb_SEND_QUE_LOG_Vo.getRes_cd();
				tb_SEND_QUE_LOG_Vo.getSend_que_id();
//				tb_SEND_QUE_LOG_Vo.get
//				tb_SEND_QUE_LOG_Vo.getSend_que_id();
				
				
			}

		}
		 
//		 System.out.println(pushLogList.size());
		 
		 
	 }
	 
	// 매일 새벽 2시에 로그인 횟수 초기화
//	 @Scheduled(cron="0 0 02 * * ?")
//	 public void HOWSchedular() throws RuntimeException {
//		 System.out.println("02시 00분");
//	 }
	 
	 
	 //ReqUid에서 cd_id 뽑기
	 public String getCdIdFromReqUid(String ReqUid) {
		 String[] result = ReqUid.split("_");
		 return result[result.length - 1];
	 }
	
}
