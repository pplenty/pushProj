package com.pushman.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pushman.dao.AppUserDao;
import com.pushman.dao.MSG_DATA_Dao;
import com.pushman.dao.MSG_LOG_Dao;
import com.pushman.dao.PushCampaignDao;
import com.pushman.dao.PushCampaignDetailDao;
import com.pushman.dao.SmsDetailDao;
import com.pushman.dao.SmsUserDao;
import com.pushman.dao.TB_SEND_QUE_LOG_Dao;
import com.pushman.domain.MSG_DATA_Vo;
import com.pushman.domain.MSG_LOG_Vo;
import com.pushman.domain.PushCampaignDetailVo;
import com.pushman.domain.PushCampaignVo;
import com.pushman.domain.SmsDetailVo;
import com.pushman.domain.SmsUserVo;
import com.pushman.domain.TB_SEND_QUE_LOG_Vo;
import com.pushman.util.MultipleDataSource;
import com.pushman.util.PushSetting;

@Service
public class UpdateEngine {
	@Autowired
	AppUserDao appUserDao;
	@Autowired
	SmsUserDao smsUserDao;
	@Autowired
	PushCampaignDao pushCampaignDao;
	@Autowired
	PushCampaignDetailDao pushCampaignDetailDao;
	@Autowired
	TB_SEND_QUE_LOG_Dao tbSendQueLogDao;
	@Autowired
	MSG_DATA_Dao msgDataDao;
	@Autowired
	MSG_LOG_Dao msgLodDao;
	@Autowired
	SmsDetailDao smsDetailDao;
	
	// 푸시 로그 스케줄러('SEND') / 실패 시 SMS 재전송
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

				// 데이터소스 SET - 로컬DB(for문 때문에 다시 SET 해줘야함)
				MultipleDataSource.setDataSourceKey("localDB");
				// 타겟 모바일 번호 저장
				int user_id = pushCampaignDetailVo.getUser_id();
				String targetMobile = appUserDao.selectOneByUserId(user_id).getMobile();
				
				// 발송자 mobile 얻기
				PushCampaignVo pushCampVo = pushCampaignDao.selectOneByCampId(pushCampaignDetailVo.getCamp_id());
				SmsUserVo smsUserVo = smsUserDao.selectOneByUserNo(pushCampVo.getUser_no());
				String senderMobile = smsUserVo.getMobile();

				// 데이터소스 SET - 푸시피아 DB
				MultipleDataSource.setDataSourceKey("pushpiaDB");
				HashMap<String, Object> sqlParams = new HashMap<String, Object>();

				// 로그테이블에서 SEND 로그만 가져오기
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
						
						// 타겟  DB 바뀌기 전에 cd_id 저장
						int cd_id = pushCampaignDetailVo.getCd_id();

						// 캠페인 SMS 리타게팅 
						MSG_DATA_Vo msgDataVo = new MSG_DATA_Vo();
						// && 캠페인 VO. reTarget == Y 
						if (pushCampaignDetailVo.getRes_cd() == null || 
								!((pushCampaignDetailVo.getRes_cd()).equals("1000"))) {

							// 데이터소스 SET - SMS 중계사 DB
							MultipleDataSource.setDataSourceKey("iHeartDB");
							
							//실패 한 타겟들 문자로 재발송
							msgDataVo.setCall_to(targetMobile);
							msgDataVo.setCall_from(senderMobile);//하드코딩 수정
							msgDataVo.setSms_txt("PUSH 리타겟팅 TEST");
							msgDataVo.setMsg_etc2(Integer.toString(cd_id));
							msgDataDao.sendSMS(msgDataVo);
							
							// 발송 후 SMS Detail 캠페인 등록
							SmsDetailVo smsDetailVo = new SmsDetailVo();
							smsDetailVo.setError_code("9");// 전송중
							smsDetailVo.setTg_mobile(targetMobile);
							smsDetailVo.setCd_id(cd_id);
							
							// 데이터소스 SET - local DB
							MultipleDataSource.setDataSourceKey("localDB");
							smsDetailDao.insert(smsDetailVo);
						}
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



	// SMS 발송 Detail 로그 스케줄러
	@Scheduled(fixedDelay = 10000)
	public void updateSMSLog() throws RuntimeException {
		// 데이터소스 SET - 로컬DB
		MultipleDataSource.setDataSourceKey("localDB");
		List<SmsDetailVo> isNullList = smsDetailDao.selectListLogIsNull();
		// 업데이트 된 로그가 있을 때 (시퀀스가 빈 로그가 있을 때)
		if (!isNullList.isEmpty()) {

			for (SmsDetailVo smsDetailVo : isNullList) {

				// 데이터소스 SET - 아이하트 DB
				MultipleDataSource.setDataSourceKey("iHeartDB");
				HashMap<String, String> sqlParams = new HashMap<String, String>();
				// 로그테이블에서 로그 가져오기
				sqlParams.put("MSG_ETC1", PushSetting.RE_SMS_KEY);
				List<MSG_LOG_Vo> msgLogList = msgLodDao.selectListSmsLog(sqlParams);
				for (MSG_LOG_Vo msg_LOG_Vo : msgLogList) {

					if (msg_LOG_Vo.getMsg_etc2().equals(Integer.toString(smsDetailVo.getCd_id()))) {
						smsDetailVo.setError_code(Character.toString(msg_LOG_Vo.getRslt_code2()));
						smsDetailVo.setMSG_SEQ(msg_LOG_Vo.getMsg_seq());

						// 데이터소스 SET - 로컬 DB
						MultipleDataSource.setDataSourceKey("localDB");
						// SMS 상세 로그 업데이트
						smsDetailDao.updateSMSLog(smsDetailVo);
					}
				}
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
