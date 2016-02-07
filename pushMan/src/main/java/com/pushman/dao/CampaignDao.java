package com.pushman.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pushman.domain.CampaignVo;

@Component
public interface CampaignDao {
	int insert(CampaignVo campaign);

	List<CampaignVo> selectList(Map<String, Object> paramMap);

	List<CampaignVo> selectListByUser(Map<String, Object> paramMap);

	int countAll(Map<String, Object> paramMap);

	int countByUser(Map<String, Object> paramMap);

	int getRecentKey();
	
	List<Map<String, Object>> countTotalDaily();
	
	
}
