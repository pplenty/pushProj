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
import com.pushman.dao.PushCampaignDetailDao;
import com.pushman.dao.SmsDetailDao;
import com.pushman.dao.SmsUserDao;
import com.pushman.dao.TB_SEND_QUE_LOG_Dao;
import com.pushman.domain.CampaignVo;
import com.pushman.domain.MSG_DATA_Vo;
import com.pushman.domain.PushCampaignDetailVo;
import com.pushman.domain.PushCampaignVo;
import com.pushman.domain.SmsDetailVo;
import com.pushman.domain.SmsUserVo;
import com.pushman.domain.TB_SEND_QUE_LOG_Vo;
import com.pushman.util.MultipleDataSource;
import com.pushman.util.PushSetting;

@Service
public class PushLogUpdateEngine {
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
	@Scheduled(fixedDelay = SchedulerCommon.FIXED_DELAY)
	public void updatePushLogSchedular() throws RuntimeException {

		// 데이터소스 SET - 로컬DB
		MultipleDataSource.setDataSourceKey("localDB");
		List<PushCampaignDetailVo> isNullList = pushCampaignDetailDao.selectListLogIsNull();

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
					if (SchedulerCommon.getCdIdFromReqUid(tb_SEND_QUE_LOG_Vo.getReq_uid()) == pushCampaignDetailVo.getCd_id()) {
						pushCampaignDetailVo.setReg_date(tb_SEND_QUE_LOG_Vo.getReg_date());
						pushCampaignDetailVo.setReqUid(tb_SEND_QUE_LOG_Vo.getReq_uid());
						pushCampaignDetailVo.setRtn_type(tb_SEND_QUE_LOG_Vo.getRtn_type());
						pushCampaignDetailVo.setRes_cd(tb_SEND_QUE_LOG_Vo.getRes_cd());
						pushCampaignDetailVo.setPush_log_id(tb_SEND_QUE_LOG_Vo.getSend_que_id());
						pushCampaignDetailVo.setCamp_id(SchedulerCommon.getCampIdFromReqUid(pushCampaignDetailVo.getReqUid()));
						pushCampaignDetailVo.setCd_id(SchedulerCommon.getCdIdFromReqUid(pushCampaignDetailVo.getReqUid()));

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
						if (	(pushCampaignDetailVo.getRes_cd() == null || 
								!((pushCampaignDetailVo.getRes_cd()).equals("1000"))) && 
								pushCampVo.getCheckReTarget() == "Y") {

							// 데이터소스 SET - SMS 중계사 DB
							MultipleDataSource.setDataSourceKey("iHeartDB");
							
							//실패 한 타겟들 문자로 재발송
							msgDataVo.setCall_to(targetMobile);
							msgDataVo.setCall_from(senderMobile);
							msgDataVo.setSms_txt(pushCampVo.getSmsContent());
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
	 
	// 매일 새벽 2시에 로그인 횟수 초기화
//	 @Scheduled(cron="0 0 02 * * ?")
//	 public void HOWSchedular() throws RuntimeException {
//		 System.out.println("02시 00분");
//	 }
	
}
