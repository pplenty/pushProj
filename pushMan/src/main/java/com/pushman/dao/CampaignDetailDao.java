package com.pushman.dao;

import java.util.List;
import java.util.Map;

import com.pushman.domain.CampaignDetailVo;


public interface CampaignDetailDao {
	List<CampaignDetailVo> selectList(Map<String, Object> paramMap);

	List<CampaignDetailVo> selectListByCamp(Map<String, Object> paramMap);

	int insert(CampaignDetailVo campDetail);

	int countAll(Map<String, Object> paramMap);

	int countByCamp(Map<String, Object> paramMap);
	
	int cntRsltByCamp(Map<String, Object> paramMap);
	
	int getMaxKey();

	CampaignDetailVo selectOne(Map<String, String> paramMap);

	CampaignDetailVo selectOneforEmail(Map<String, String> paramMap);

	List<CampaignDetailVo> selectListLogIsNull();

	int updateSMSLog(CampaignDetailVo campaignDetailVo);

}
