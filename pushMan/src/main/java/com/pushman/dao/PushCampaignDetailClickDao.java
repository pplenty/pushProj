package com.pushman.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pushman.domain.PushCampaignDetailVo;

@Component
public interface PushCampaignDetailClickDao {
	int insert(PushCampaignDetailVo pushCampaignDetailVo);

	List<PushCampaignDetailVo> selectList(Map<String, Object> paramMap);

	List<Map<String, Object>> selectListByCamp(Map<String, Object> paramMap);
	
	List<PushCampaignDetailVo> selectListLogIsNull();

	int getMaxLogId();
	
	int updatePushLog(PushCampaignDetailVo pushCampaignDetailVo);

	int insertLog(PushCampaignDetailVo pushCampaignDetailVo);
}