package com.pushman.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pushman.domain.AppUserVo;

@Component
public interface AppUserDao {
	int insert(AppUserVo appUserVo);
	
	int update(AppUserVo appUserVo);
	
	int countAll(Map<String, Object> paramMap);
	
	List<AppUserVo> selectListAll(Map<String, Object> paramMap);
	
	AppUserVo selectOneByCustId(String cust_id);
	
	AppUserVo selectOneByUserId(int user_id);

	List<AppUserVo> selectListTarget(Map<String, Object> paramMap);
}


