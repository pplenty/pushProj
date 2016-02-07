package com.pushman.domain;

import java.io.Serializable;

public class PushCampaignDetailVo implements Serializable {// WAS 사이에 데이터 이전이 가능하도록 직렬화를
	private static final long serialVersionUID = 1L;

	protected int cd_id;
	protected String reg_date;
	protected String reqUid;
	protected String rtn_type;
	protected String res_cd;
	protected int cust_id;
	protected int camp_id;
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
	public String getRtn_type() {
		return rtn_type;
	}
	public void setRtn_type(String rtn_type) {
		this.rtn_type = rtn_type;
	}
	public String getRes_cd() {
		return res_cd;
	}
	public void setRes_cd(String res_cd) {
		this.res_cd = res_cd;
	}
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
	public int getCamp_id() {
		return camp_id;
	}
	public void setCamp_id(int camp_id) {
		this.camp_id = camp_id;
	}
	@Override
	public String toString() {
		return "PushCampaignDetailVo [cd_id=" + cd_id + ", reg_date="
				+ reg_date + ", reqUid=" + reqUid + ", rtn_type=" + rtn_type
				+ ", res_cd=" + res_cd + ", cust_id=" + cust_id + ", camp_id="
				+ camp_id + "]";
	}
	
}
