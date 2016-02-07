package com.pushman.dao;

import org.springframework.stereotype.Component;

import com.pushman.domain.MSG_DATA_Vo;


@Component
public interface MSG_DATA_Dao {
	int sendSMS(MSG_DATA_Vo msgDataVo);
}











