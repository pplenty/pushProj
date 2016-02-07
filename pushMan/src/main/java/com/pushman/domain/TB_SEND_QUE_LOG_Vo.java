package com.pushman.domain;

import java.io.Serializable;

public class TB_SEND_QUE_LOG_Vo implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int 		send_que_id;
	protected String 	biz_id;
	protected String 	req_uid;
	protected String 	cust_id;
	protected char 		rtn_type;
	protected String	res_cd;
	protected String 	data;
	protected String 	res_date;
	protected char 		check_flag;
	protected String 	reg_date;
	protected int 		app_user_id;
	
	
	public int getSend_que_id() {
		return send_que_id;
	}
	public void setSend_que_id(int send_que_id) {
		this.send_que_id = send_que_id;
	}
	public String getBiz_id() {
		return biz_id;
	}
	public void setBiz_id(String biz_id) {
		this.biz_id = biz_id;
	}
	public String getReq_uid() {
		return req_uid;
	}
	public void setReq_uid(String req_uid) {
		this.req_uid = req_uid;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public char getRtn_type() {
		return rtn_type;
	}
	public void setRtn_type(char rtn_type) {
		this.rtn_type = rtn_type;
	}
	public String getRes_cd() {
		return res_cd;
	}
	public void setRes_cd(String res_cd) {
		this.res_cd = res_cd;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getRes_date() {
		return res_date;
	}
	public void setRes_date(String res_date) {
		this.res_date = res_date;
	}
	public char getCheck_flag() {
		return check_flag;
	}
	public void setCheck_flag(char check_flag) {
		this.check_flag = check_flag;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getApp_user_id() {
		return app_user_id;
	}
	public void setApp_user_id(int app_user_id) {
		this.app_user_id = app_user_id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "TB_SEND_QUE_LOG_Vo [send_que_id=" + send_que_id + ", biz_id=" + biz_id + ", req_uid=" + req_uid
				+ ", cust_id=" + cust_id + ", rtn_type=" + rtn_type + ", res_cd=" + res_cd + ", data=" + data
				+ ", res_date=" + res_date + ", check_flag=" + check_flag + ", reg_date=" + reg_date + ", app_user_id="
				+ app_user_id + "]";
	}

	
	
	
	
}