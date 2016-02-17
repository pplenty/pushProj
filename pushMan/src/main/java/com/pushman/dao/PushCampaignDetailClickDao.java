package com.pushman.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pushman.domain.PushCampaignDetailClickVo;
import com.pushman.domain.PushCampaignDetailVo;

@Component
public interface PushCampaignDetailClickDao {
	int getLocalMaxLogId();
	
	int getPushpiaMaxLogId();
	
	List<HashMap<String, String>> getPushpiaClickLog(int maxLocalLogId);

	void insertClickLog(PushCampaignDetailClickVo pushCampaignDetailClickVo);
	
	/*****************************************************************************/
	
	int insert(PushCampaignDetailVo pushCampaignDetailVo);

	List<PushCampaignDetailVo> selectList(Map<String, Object> paramMap);

	List<Map<String, Object>> selectListByCamp(Map<String, Object> paramMap);
	
	List<PushCampaignDetailVo> selectListLogIsNull();
	
	int updatePushLog(PushCampaignDetailVo pushCampaignDetailVo);

	int insertLog(PushCampaignDetailVo pushCampaignDetailVo);

}
