package com.hzq.system.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author hzq
 *
 *         2015年8月31日 下午8:55:04
 */
public class ShiroUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Serializable sessionId;
	private String username;
	private String nick;
	private String phone;
	private List<SysRole> roles;
	private List<SysPermission> menus;
	private List<SysPermission> permissions;
	public ShiroUser() {
		super();
	}

	public ShiroUser(Integer id, String username, String nick, String phone) {
		super();
		this.id = id;
		this.username = username;
		this.nick = nick;
		this.phone = phone;
	}

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
		this.username = username;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
	
	public List<SysPermission> getMenus() {
		return menus;
	}

	public void setMenus(List<SysPermission> menus) {
		this.menus = menus;
	}

	public List<SysPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}

	
	

	public Serializable getSessionId() {
		return sessionId;
	}

	public void setSessionId(Serializable sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		return "ShiroUser [id=" + id + ", sessionId=" + sessionId + ", username=" + username + ", nick=" + nick + ", phone=" + phone + ", roles=" + roles + ", menus=" + menus + ", permissions="
				+ permissions + "]";
	}

	

	
}
