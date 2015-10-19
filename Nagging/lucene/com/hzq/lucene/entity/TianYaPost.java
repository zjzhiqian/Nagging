package com.hzq.lucene.entity;

import java.io.Serializable;
import java.util.Date;

public class TianYaPost implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 是否加精1,是,0 不是
	 */
	private int isBest;
	
	private String href;
	
	private String title;
	
	private String addUserId;
	
	private String addUserName;
	
	private Date addTime;
	
	private Date lastReplyTime;
	
	
	private String click;
	
	private String reply;

	public int getIsBest() {
		return isBest;
	}

	public void setIsBest(int isBest) {
		this.isBest = isBest;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}

	public String getAddUserName() {
		return addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Date getLastReplyTime() {
		return lastReplyTime;
	}

	public void setLastReplyTime(Date lastReplyTime) {
		this.lastReplyTime = lastReplyTime;
	}

	@Override
	public String toString() {
		return "TianYaPost [isBest=" + isBest + ", href=" + href + ", title="
				+ title + ", addUserId=" + addUserId + ", addUserName="
				+ addUserName + ", addTime=" + addTime + ", lastReplyTime="
				+ lastReplyTime + ", click=" + click + ", reply=" + reply + "]";
	}
}
