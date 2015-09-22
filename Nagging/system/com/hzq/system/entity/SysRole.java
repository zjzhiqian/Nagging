package com.hzq.system.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

public class SysRole implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@Length(min=3,max=10,message="角色名称长度必须在3-10之间")
    private String authorityname;
	@Length(min=3,max=20,message="角色描述长度必须在3-20之间")
    private String description;

    private String state;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date addtime;

    private String adduserid;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date modifytime;

    private String modifyuserid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthorityname() {
        return authorityname;
    }

    public void setAuthorityname(String authorityname) {
        this.authorityname = authorityname == null ? null : authorityname.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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
		return "SysRole [id=" + id + ", authorityname=" + authorityname
				+ ", description=" + description + ", state=" + state
				+ ", addtime=" + addtime + ", adduserid=" + adduserid
				+ ", modifytime=" + modifytime + ", modifyuserid="
				+ modifyuserid + "]";
	}
    
}