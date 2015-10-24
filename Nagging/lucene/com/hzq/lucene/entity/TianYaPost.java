/*
 * TianYaPost.java
 * Copyright (huangzhiqian)
 * All rights reserved.
 * -----------------------------------------------
 * 2015-10-22 Created
 */
package com.hzq.lucene.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 * @author huangzhiqian
 * @version 1.0 2015-10-22
 */
public class TianYaPost implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子编号
     */
    private Long id;
    /**
     * 帖子内容
     */
    private String content;
    private String url;
    /**
     * 标题
     */
    private String title;
    /**
     * 发帖人编号
     */
    private String adduserId;
    /**
     * 用户昵称
     */
    private String adduserName;
    /**
     * 发帖时间
     */
    private Date addTime;
    /**
     * 最后回复时间
     */
    private Date lastReplyTime;
    private Long click;
    private Long reply;
    /**
     * 是否加精
     */
    private String isBest;

    private AtomicInteger RetryCount=new AtomicInteger(0);
    
    
    
	public AtomicInteger getRetryCount() {
		return RetryCount;
	}
	public void setRetryCount(AtomicInteger retryCount) {
		RetryCount = retryCount;
	}
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }
    public String getAdduserId() {
        return adduserId;
    }
    public void setAdduserId(String adduserId) {
        this.adduserId = adduserId == null ? null : adduserId.trim();
    }
    public String getAdduserName() {
        return adduserName;
    }
    public void setAdduserName(String adduserName) {
        this.adduserName = adduserName == null ? null : adduserName.trim();
    }
    public Date getAddTime() {
        return addTime;
    }
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
    public Date getLastReplyTime() {
        return lastReplyTime;
    }
    public void setLastReplyTime(Date lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }
    public Long getClick() {
        return click;
    }
    public void setClick(Long click) {
        this.click = click;
    }
    public Long getReply() {
        return reply;
    }
    public void setReply(Long reply) {
        this.reply = reply;
    }
    public String getIsBest() {
        return isBest;
    }
    public void setIsBest(String isBest) {
        this.isBest = isBest == null ? null : isBest.trim();
    }
}