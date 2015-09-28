package com.hzq.system.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;


public class SysPermission implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	@Length(min=1,max=20,message="长度不合法")
    private String operatename;
	@Length(min=1,max=20,message="长度不合法")
    private String description;
	@Length(min=1,max=20,message="长度不合法")
    private String auth;
	
    private String url;
    private String type;

    private String state;

    private String pid;
    
    private Integer seq;

    private Date addtime;

    private String adduserid;

    private Date modifytime;

    private String modifyuserid;

    

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOperatename() {
        return operatename;
    }

    public void setOperatename(String operatename) {
        this.operatename = operatename == null ? null : operatename.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth == null ? null : auth.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getModifyuserid() {
        return modifyuserid;
    }

    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

	@Override
	public String toString() {
		return "SysPermission [id=" + id + ", operatename=" + operatename
				+ ", description=" + description + ", auth=" + auth + ", url="
				+ url + ", type=" + type + ", state=" + state + ", pid=" + pid
				+ ", seq=" + seq + ", addtime=" + addtime + ", adduserid="
				+ adduserid + ", modifytime=" + modifytime + ", modifyuserid="
				+ modifyuserid + "]";
	}
    
    
}