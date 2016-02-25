package com.pushman.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pushman.domain.PushCampaignDetailVo;

@Component
public interface PushCampaignDetailDao {
	int insert(PushCampaignDetailVo pushCampaignDetailVo);

	List<PushCampaignDetailVo> selectList(Map<String, Object> paramMap);

	List<Map<String, Object>> selectListByCamp(Map<String, Object> paramMap);
	
	List<PushCampaignDetailVo> selectListLogIsNull();

	int getMaxLogId();
	
	int updatePushLog(PushCampaignDetailVo pushCampaignDetailVo);

	int insertLog(PushCampaignDetailVo pushCampaignDetailVo);
	
	List<Map<String, Object>> selectSmsListByCamp(Map<String, Object> paramMap);

	int countListByCamp(HashMap<String, Object> sqlParams);

	// 전체 발송 수
	List<HashMap<String, Integer>> selectReadCntByPusher(HashMap<String, Object> sqlParams);

	int countSmsListByCamp(HashMap<String, Object> sqlParams);
	
//	List<PushCampaignDetailClickVo> selectSendPushByPusher(HashMap<String, Object> sqlParams);
}
