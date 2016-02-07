package com.pushman.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pushman.domain.PushCampaignDetailVo;

@Component
public interface PushCampaignDetailDao {
	int insert(PushCampaignDetailVo pushCampaignDetailVo);

	List<PushCampaignDetailVo> selectList(Map<String, Object> paramMap);

}
