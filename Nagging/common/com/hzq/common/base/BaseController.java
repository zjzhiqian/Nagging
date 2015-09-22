package com.hzq.common.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;

import com.hzq.system.entity.ShiroUser;

/**
 * @author hzq
 *
 * 2015年8月18日 下午11:22:45 
 */
public abstract class BaseController{
	
	
	public ShiroUser getShiroUser(){
		Subject subject=SecurityUtils.getSubject();
		ShiroUser shiroUser=(ShiroUser)subject.getPrincipal();		
		if(shiroUser==null){
			throw new AuthorizationException("获取当前登陆用户失败");
		}
		return shiroUser;
	}
	
	
}
