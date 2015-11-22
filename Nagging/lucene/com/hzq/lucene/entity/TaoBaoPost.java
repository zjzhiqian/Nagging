/*
 * TaoBaoPost.java
 * Copyright (huangzhiqian)
 * All rights reserved.
 * -----------------------------------------------
 * 2015-11-22 Created
 */
package com.hzq.lucene.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author huangzhiqian
 * @version 1.0 2015-11-22
 */
public class TaoBaoPost implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子编号
     */
    private Long id;
    private String url;
    /**
     * 标题
     */
    private String title;
    private String modual;
    private String nextpageurl;
    private Date addTime;
    private String adduserName;
    private Long click;
    private Long reply;
    private String content;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getModual() {
        return modual;
    }
    public void setModual(String modual) {
        this.modual = modual == null ? null : modual.trim();
    }
    public String getNextpageurl() {
        return nextpageurl;
    }
    public void setNextpageurl(String nextpageurl) {
        this.nextpageurl = nextpageurl == null ? null : nextpageurl.trim();
    }
    public Date getAddTime() {
        return addTime;
    }
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
    public String getAdduserName() {
        return adduserName;
    }
    public void setAdduserName(String adduserName) {
        this.adduserName = adduserName == null ? null : adduserName.trim();
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
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}