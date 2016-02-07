package com.pushman.domain;

import java.io.Serializable;

public class CampaignDetailVo implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int cd_no;
	protected int log_msg_seq;
	protected String rcv_name;
	protected String rcv_mob;
	protected String send_error;
	protected String send_time;
	protected int camp_no;
	public int getCd_no() {
		return cd_no;
	}
	public void setCd_no(int cd_no) {
		this.cd_no = cd_no;
	}
	public int getLog_msg_seq() {
		return log_msg_seq;
	}
	public void setLog_msg_seq(int log_msg_seq) {
		this.log_msg_seq = log_msg_seq;
	}
	public String getRcv_name() {
		return rcv_name;
	}
	public void setRcv_name(String rcv_name) {
		this.rcv_name = rcv_name;
	}
	public String getRcv_mob() {
		return rcv_mob;
	}
	public void setRcv_mob(String rcv_mob) {
		this.rcv_mob = rcv_mob;
	}
	public String getSend_error() {
		return send_error;
	}
	public void setSend_error(String send_error) {
		this.send_error = send_error;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public int getCamp_no() {
		return camp_no;
	}
	public void setCamp_no(int camp_no) {
		this.camp_no = camp_no;
	}
	@Override
	public String toString() {
		return "CampaignDetailVo [cd_no=" + cd_no + ", log_msg_seq="
				+ log_msg_seq + ", rcv_name=" + rcv_name + ", rcv_mob="
				+ rcv_mob + ", send_error=" + send_error + ", send_time="
				+ send_time + ", camp_no=" + camp_no + "]";
	}
	
	
}
