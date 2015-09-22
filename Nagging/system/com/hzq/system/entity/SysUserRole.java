/*
 * SysUserRole.java
 * Copyright (huangzhiqian)
 * All rights reserved.
 * -----------------------------------------------
 * 2015-09-10 Created
 */
package com.hzq.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关联表
 * 
 * @author huangzhiqian
 * @version 1.0 2015-09-10
 */
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private Integer userid;
    /**
     * 角色编号
     */
    private Integer roleId;
    /**
     * 添加时间
     */
    private Date addtime;
    /**
     * 添加人编号
     */
    private String adduserid;

    public Integer getUserid() {
        return userid;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    public Integer getRoleId() {
        return roleId;
    }
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    public Date getAddtime() {
        return addtime;
    }
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
    public String getAdduserid() {
        return adduserid;
    }
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }
}