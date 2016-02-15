package com.pushman.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pushman.domain.PushCampaignDetailVo;
import com.pushman.domain.PushCampaignVo;

@Component
public interface PushCampaignDao {
	int insert(PushCampaignVo pushCampaignVo);

	List<PushCampaignVo> selectList(Map<String, Object> paramMap);
	
	List<PushCampaignVo> selectListByPusher(Map<String, Object> paramMap);

	int updateInit(PushCampaignVo pushCampaignVo);

	int updateResult(Map<String, Object> paramMap);
}
