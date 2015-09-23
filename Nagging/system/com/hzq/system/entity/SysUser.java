package com.hzq.system.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class SysUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@Length(min=3,max=10,message="登录名长度必须为3-10之间")
    private String username;
	
	
    private String password;
    
    @Length(min=3,max=10,message="昵称长度必须为3-10之间")
    private String name;

    private String salt;

    
    @NotEmpty(message="手机号不能为空")
    private String phone;

    private String state;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date addtime;	

    private String adduserid;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date modifytime;

    private String modifyuserid;

    private String lastip;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date loginTime;

    private int loginCount;
    
    private String roleIds;
   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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

    public String getLastip() {
        return lastip;
    }

    public void setLastip(String lastip) {
        this.lastip = lastip == null ? null : lastip.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

   

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	@Override
	public String toString() {
		return "SysUser [id=" + id + ", username=" + username + ", password="
				+ password + ", name=" + name + ", salt=" + salt + ", phone="
				+ phone + ", state=" + state + ", addtime=" + addtime
				+ ", adduserid=" + adduserid + ", modifytime=" + modifytime
				+ ", modifyuserid=" + modifyuserid + ", lastip=" + lastip
				+ ", loginTime=" + loginTime + ", loginCount=" + loginCount
				+ ", roleIds=" + roleIds + "]";
	}



	
    
    
}