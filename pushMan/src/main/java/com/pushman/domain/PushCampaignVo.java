package com.pushman.domain;

import java.io.Serializable;

public class PushCampaignVo implements Serializable {// WAS 사이에 데이터 이전이 가능하도록 직렬화를
	private static final long serialVersionUID = 1L;

	protected int camp_id;
	protected String reg_date;
	protected int push_total;
	protected int push_succ;
	protected int push_fail;
	protected int push_open;
	protected int push_click;
	protected String camp_reqUid;
	public int getCamp_id() {
		return camp_id;
	}
	public void setCamp_id(int camp_id) {
		this.camp_id = camp_id;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getPush_total() {
		return push_total;
	}
	public void setPush_total(int push_total) {
		this.push_total = push_total;
	}
	public int getPush_succ() {
		return push_succ;
	}
	public void setPush_succ(int push_succ) {
		this.push_succ = push_succ;
	}
	public int getPush_fail() {
		return push_fail;
	}
	public void setPush_fail(int push_fail) {
		this.push_fail = push_fail;
	}
	public int getPush_open() {
		return push_open;
	}
	public void setPush_open(int push_open) {
		this.push_open = push_open;
	}
	public int getPush_click() {
		return push_click;
	}
	public void setPush_click(int push_click) {
		this.push_click = push_click;
	}
	public String getCamp_reqUid() {
		return camp_reqUid;
	}
	public void setCamp_reqUid(String camp_reqUid) {
		this.camp_reqUid = camp_reqUid;
	}
	@Override
	public String toString() {
		return "PushCampaignVo [camp_id=" + camp_id + ", reg_date=" + reg_date
				+ ", push_total=" + push_total + ", push_succ=" + push_succ
				+ ", push_fail=" + push_fail + ", push_open=" + push_open
				+ ", push_click=" + push_click + ", camp_reqUid=" + camp_reqUid
				+ "]";
	}
	
	
}
