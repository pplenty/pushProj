package com.pushman.scheduler;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pushman.dao.MSG_LOG_Dao;
import com.pushman.dao.SmsDetailDao;
import com.pushman.domain.MSG_LOG_Vo;
import com.pushman.domain.SmsDetailVo;
import com.pushman.util.MultipleDataSource;
import com.pushman.util.PushSetting;

@Service
public class PushReSmsLogUpdateEngine {
	@Autowired
	SmsDetailDao smsDetailDao;
	@Autowired
	MSG_LOG_Dao msgLodDao;
	
	// SMS 발송 Detail 로그 스케줄러
//	@Scheduled(fixedDelay = SchedulerCommon.FIXED_DELAY)
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
}
