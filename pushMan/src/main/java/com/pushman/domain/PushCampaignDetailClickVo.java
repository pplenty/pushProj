package com.pushman.domain;

import java.io.Serializable;

public class PushCampaignDetailClickVo implements Serializable {// WAS 사이에 데이터 이전이 가능하도록 직렬화를
	private static final long serialVersionUID = 1L;

	protected int clk_id;
	protected long tb_click_id;
	protected int link_seq;
	protected String link;
	protected String msg_push_type;
	protected int click_cnt;
	protected String reg_date;
	protected String upt_date;
	protected int camp_id;
	
	public int getClk_id() {
		return clk_id;
	}
	public void setClk_id(int clk_id) {
		this.clk_id = clk_id;
	}
	public long getTb_click_id() {
		return tb_click_id;
	}
	public void setTb_click_id(long tb_click_id) {
		this.tb_click_id = tb_click_id;
	}
	public int getLink_seq() {
		return link_seq;
	}
	public void setLink_seq(int link_seq) {
		this.link_seq = link_seq;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getMsg_push_type() {
		return msg_push_type;
	}
	public void setMsg_push_type(String msg_push_type) {
		this.msg_push_type = msg_push_type;
	}
	public int getClick_cnt() {
		return click_cnt;
	}
	public void setClick_cnt(int click_cnt) {
		this.click_cnt = click_cnt;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getUpt_date() {
		return upt_date;
	}
	public void setUpt_date(String upt_date) {
		this.upt_date = upt_date;
	}
	public int getCamp_id() {
		return camp_id;
	}
	public void setCamp_id(int camp_id) {
		this.camp_id = camp_id;
	}
	
	@Override
	public String toString() {
		return "PushCampaignDetailClickVo [clk_id=" + clk_id + ", tb_click_id=" + tb_click_id + ", link_seq=" + link_seq
				+ ", link=" + link + ", msg_push_type=" + msg_push_type + ", click_cnt=" + click_cnt + ", reg_date="
				+ reg_date + ", upt_date=" + upt_date + ", camp_id=" + camp_id + "]";
	}
}
