package com.pushman.domain;

import java.io.Serializable;
import java.math.BigInteger;

public class PushCampaignDetailVo implements Serializable {// WAS 사이에 데이터 이전이 가능하도록 직렬화를
	private static final long serialVersionUID = 1L;

	protected int cd_id;
	protected String reg_date;
	protected String reqUid;
	protected char rtn_type;
	protected String res_cd;
	protected int camp_id;
	protected int user_id;
	protected int push_log_id;
	protected int clk_id;
	protected int tb_click_id;
	public int getCd_id() {
		return cd_id;
	}
	public void setCd_id(int cd_id) {
		this.cd_id = cd_id;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getReqUid() {
		return reqUid;
	}
	public void setReqUid(String reqUid) {
		this.reqUid = reqUid;
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
	public int getCamp_id() {
		return camp_id;
	}
	public void setCamp_id(int camp_id) {
		this.camp_id = camp_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getPush_log_id() {
		return push_log_id;
	}
	public void setPush_log_id(int push_log_id) {
		this.push_log_id = push_log_id;
	}
	public int getClk_id() {
		return clk_id;
	}
	public void setClk_id(int clk_id) {
		this.clk_id = clk_id;
	}
	public int getTb_click_id() {
		return tb_click_id;
	}
	public void setTb_click_id(int tb_click_id) {
		this.tb_click_id = tb_click_id;
	}
	@Override
	public String toString() {
		return "PushCampaignDetailVo [cd_id=" + cd_id + ", reg_date=" + reg_date + ", reqUid=" + reqUid + ", rtn_type="
				+ rtn_type + ", res_cd=" + res_cd + ", camp_id=" + camp_id + ", user_id=" + user_id + ", push_log_id="
				+ push_log_id + ", clk_id=" + clk_id + ", tb_click_id=" + tb_click_id + "]";
	}
	
	
	
}
