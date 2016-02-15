package com.pushman.domain;

import java.io.Serializable;

public class SmsDetailVo implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int sms_id;
	protected String reg_date;
	protected String tg_mobile;
	protected String error_code;
	protected int cd_id;
	protected int MSG_SEQ;
	public int getSms_id() {
		return sms_id;
	}
	public void setSms_id(int sms_id) {
		this.sms_id = sms_id;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getTg_mobile() {
		return tg_mobile;
	}
	public void setTg_mobile(String tg_mobile) {
		this.tg_mobile = tg_mobile;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public int getCd_id() {
		return cd_id;
	}
	public void setCd_id(int cd_id) {
		this.cd_id = cd_id;
	}
	public int getMSG_SEQ() {
		return MSG_SEQ;
	}
	public void setMSG_SEQ(int mSG_SEQ) {
		MSG_SEQ = mSG_SEQ;
	}

}
