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

	HashMap<String, String> selectOneClickLogById(HashMap<String, Object> paramMap);

	int updateClickLog(PushCampaignDetailClickVo pushCampaignDetailClickVo);

	List<PushCampaignDetailClickVo> selectLinkCntListByCamp(HashMap<String, Object> sqlParams);
	
	
	/*****************************************************************************/
	
	
	int insert(PushCampaignDetailVo pushCampaignDetailVo);

	List<PushCampaignDetailVo> selectList(Map<String, Object> paramMap);

	List<Map<String, Object>> selectListByCamp(Map<String, Object> paramMap);
	
	List<PushCampaignDetailVo> selectListLogIsNull();

	int insertLog(PushCampaignDetailVo pushCampaignDetailVo);

}
