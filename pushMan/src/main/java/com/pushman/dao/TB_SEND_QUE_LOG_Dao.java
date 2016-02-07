package com.pushman.dao;

import java.util.List;
import java.util.Map;

import com.pushman.domain.MSG_LOG_Vo;
import com.pushman.domain.TB_SEND_QUE_LOG_Vo;



//@Component
public interface TB_SEND_QUE_LOG_Dao {
	List<TB_SEND_QUE_LOG_Vo> selectListAll(Map<String, Object> paramMap);
}











