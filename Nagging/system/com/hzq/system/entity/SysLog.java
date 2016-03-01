/*
 * SysLog.java
 * Copyright (huangzhiqian)
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-01 Created
 */
package com.hzq.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统操作日志
 * 
 * @author huangzhiqian
 * @version 1.0 2016-03-01
 */
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志编号
     */
    private Long id;
    /**
     * 日志类型0其他操作1查询2新增3修改4删除
     */
    private String type;
    /**
     * 日志内容
     */
    private String content;
    /**
     * 关键字
     */
    private String keyname;
    /**
     * 添加时间
     */
    private Date addtime;
    /**
     * 添加人编号
     */
    private String userid;
    /**
     * 添加人姓名
     */
    private String name;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 修改记录编号
     */
    private String dataid;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
    public String getKeyname() {
        return keyname;
    }
    public void setKeyname(String keyname) {
        this.keyname = keyname == null ? null : keyname.trim();
    }
    public Date getAddtime() {
        return addtime;
    }
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
    public String getDataid() {
        return dataid;
    }
    public void setDataid(String dataid) {
        this.dataid = dataid == null ? null : dataid.trim();
    }
}