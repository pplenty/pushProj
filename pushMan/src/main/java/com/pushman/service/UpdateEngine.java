package com.pushman.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pushman.dao.AppUserDao;
import com.pushman.dao.PushCampaignDao;
import com.pushman.dao.PushCampaignDetailDao;
import com.pushman.dao.TB_SEND_QUE_LOG_Dao;
import com.pushman.domain.PushCampaignDetailVo;
import com.pushman.domain.TB_SEND_QUE_LOG_Vo;
import com.pushman.util.MultipleDataSource;
import com.pushman.util.PushSetting;

@Service
public class UpdateEngine {
	@Autowired
	AppUserDao appUserDao;
	@Autowired
	PushCampaignDao pushCampaignDao;
	@Autowired
	PushCampaignDetailDao pushCampaignDetailDao;
	@Autowired
	TB_SEND_QUE_LOG_Dao tbSendQueLogDao;
	
	// 푸시 로그 스케줄러('SEND')
	@Scheduled(fixedDelay = 10000)
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

		// 업데이트 된 로그가 있을 때 (시퀀스가 빈 로그가 있을 때)
		if (!isNullList.isEmpty()) {

			for (PushCampaignDetailVo pushCampaignDetailVo : isNullList) {
				pushCampaignDetailVo.getCd_id();
				// 데이터소스 SET - 푸시피아 DB
				MultipleDataSource.setDataSourceKey("pushpiaDB");
				HashMap<String, Object> sqlParams = new HashMap<String, Object>();

				//
				sqlParams.put("text_biz_id", PushSetting.TEXT_PUSH_BIZ_KEY);
				sqlParams.put("rich_biz_id", PushSetting.RICH_PUSH_BIZ_KEY);
				sqlParams.put("rtn_type", "S");
				// sqlParams.put("maxLogId", maxLogId);
				List<TB_SEND_QUE_LOG_Vo> pushLogList = tbSendQueLogDao.selectListByRtnType(sqlParams);
				for (TB_SEND_QUE_LOG_Vo tb_SEND_QUE_LOG_Vo : pushLogList) {
					if (getCdIdFromReqUid(tb_SEND_QUE_LOG_Vo.getReq_uid()) == pushCampaignDetailVo.getCd_id()) {
						pushCampaignDetailVo.setReg_date(tb_SEND_QUE_LOG_Vo.getReg_date());
						pushCampaignDetailVo.setReqUid(tb_SEND_QUE_LOG_Vo.getReq_uid());
						pushCampaignDetailVo.setRtn_type(tb_SEND_QUE_LOG_Vo.getRtn_type());
						pushCampaignDetailVo.setRes_cd(tb_SEND_QUE_LOG_Vo.getRes_cd());
						pushCampaignDetailVo.setPush_log_id(tb_SEND_QUE_LOG_Vo.getSend_que_id());
						pushCampaignDetailVo.setCamp_id(getCampIdFromReqUid(pushCampaignDetailVo.getReqUid()));
						pushCampaignDetailVo.setCd_id(getCdIdFromReqUid(pushCampaignDetailVo.getReqUid()));

						// 데이터소스 SET - 로컬 DB
						MultipleDataSource.setDataSourceKey("localDB");
						// 상세 로그 업데이트
						pushCampaignDetailDao.updatePushLog(pushCampaignDetailVo);

						// 캠페인 성공/실패 업데이트
						HashMap<String, Object> sqlParams2 = new HashMap<String, Object>();
						sqlParams2.put("res_cd", pushCampaignDetailVo.getRes_cd());
						sqlParams2.put("rtn_type", pushCampaignDetailVo.getRtn_type());
						sqlParams2.put("camp_id", pushCampaignDetailVo.getCamp_id());
						pushCampaignDao.updateResult(sqlParams2);
					}
				}
			}
		}
	}
	 
	// 푸시 로그 스케줄러('READ')
	@Scheduled(fixedDelay = 10000)
	public void updatePushReadLogSchedular() throws RuntimeException {

		// 데이터소스 SET - 로컬DB
		MultipleDataSource.setDataSourceKey("localDB");
		
		int maxLocagLogId = 0;
		int maxExLogId = 0;

		// 초기 업데이트가 안되 있는 경우 getMaxLogId NULL 예외 발생
		try {
			maxLocagLogId = pushCampaignDetailDao.getMaxLogId();
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
		
		if (maxLocagLogId < maxExLogId) {
			// 로그 업데이트 실행
			sqlParams.put("maxID", maxLocagLogId);
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
				pushCampaignDetailVo.setCamp_id(getCampIdFromReqUid(pushCampaignDetailVo.getReqUid()));
				pushCampaignDetailVo.setCd_id(getCdIdFromReqUid(pushCampaignDetailVo.getReqUid())); 
				String custId = tb_SEND_QUE_LOG_Vo.getCust_id();
				// 로그 예외처리
				if (appUserDao.selectOneByCustId(custId) != null) {
					pushCampaignDetailVo.setUser_id(appUserDao.selectOneByCustId(custId).getUser_id());
				}
				pushCampaignDetailDao.insertLog(pushCampaignDetailVo);
				
				// 캠페인 리드/클릭 업데이트
				HashMap<String, Object> sqlParams2 = new HashMap<String, Object>();
				sqlParams2.put("res_cd", pushCampaignDetailVo.getRes_cd());
				sqlParams2.put("rtn_type", pushCampaignDetailVo.getRtn_type());
				sqlParams2.put("camp_id", pushCampaignDetailVo.getCamp_id());
				pushCampaignDao.updateResult(sqlParams2);
			}
		}
		

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
		//ReqUid가 형식에 맞지 않을 경우 0 리턴
		if (resultSet.length < 4) return result;
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
		//ReqUid가 형식에 맞지 않을 경우 0 리턴
		if (resultSet.length < 4) return result;
		try {
			result =  Integer.parseInt(resultSet[resultSet.length - 2]);
		} catch (Exception e) {
			return 0;
		}
		return result;
	}
	
}
