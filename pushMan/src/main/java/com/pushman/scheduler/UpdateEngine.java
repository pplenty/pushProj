package com.pushman.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pushman.dao.CampaignDetailDao;
import com.pushman.dao.MSG_LOG_Dao;
import com.pushman.domain.CampaignDetailVo;
import com.pushman.domain.MSG_LOG_Vo;
import com.pushman.util.MultipleDataSource;
import com.pushman.util.PushSetting;

@Service
public class UpdateEngine {
	@Autowired
	CampaignDetailDao campaignDetailDao;
	@Autowired
	MSG_LOG_Dao MsgLogDao;
	

	// month에 따라 유동적인 로그 테이블 명
	Date currentDate = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	String iHeartDBLogTableName;
	// SMS 로그 스케줄러('SEND')
	@Scheduled(fixedDelay = 10000)
	public void updatePushLogSchedular() throws RuntimeException {
		
		currentDate = new Date();
		iHeartDBLogTableName = "MSG_LOG_" + sdf.format(currentDate);
		// iHeartDBLogTableName = "MSG_LOG_201602";

		// 데이터소스 SET - 로컬DB
		MultipleDataSource.setDataSourceKey("localDB");
		List<CampaignDetailVo> isNullList = campaignDetailDao.selectListLogIsNull();
		
		// 업데이트 된 로그가 있을 때 (시퀀스가 빈 로그가 있을 때)
		if (!isNullList.isEmpty()) {
			
			for (CampaignDetailVo campaignDetailVo : isNullList) {
				
				// 데이터소스 SET - iHeart
				MultipleDataSource.setDataSourceKey("iHeartDB");
				Map<String, String> sqlParams = new HashMap<String, String>();
				
				// 로그테이블에서 'etc1 = koh' 로그만 가져오기
				sqlParams.put("MSG_ETC1", PushSetting.SMS_SENDER_KEY);
				sqlParams.put("MSG_LOG_TABLE", iHeartDBLogTableName);
				List<MSG_LOG_Vo> smsLogList = MsgLogDao.selectListSmsLog(sqlParams);
				
				for (MSG_LOG_Vo msg_LOG_Vo : smsLogList) {
					if (Integer.toString(campaignDetailVo.getCd_no()).equals(msg_LOG_Vo.getMsg_etc5())) {
						campaignDetailVo.setLog_msg_seq(msg_LOG_Vo.getMsg_seq());
						campaignDetailVo.setSend_error(Character.toString(msg_LOG_Vo.getRslt_code2()));
						
						// 데이터소스 SET - 로컬 DB
						MultipleDataSource.setDataSourceKey("localDB");
						// SMS 상세 로그 업데이트
						campaignDetailDao.updateSMSLog(campaignDetailVo);
					}
				}
			}
		}
	}
	 
	
}
