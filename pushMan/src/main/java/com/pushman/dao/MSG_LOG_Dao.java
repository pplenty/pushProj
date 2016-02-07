package com.pushman.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pushman.domain.MSG_LOG_Vo;

@Component
public interface MSG_LOG_Dao {
	
	List<MSG_LOG_Vo> selectListAll(Map<String, Object> paramMap);
}











