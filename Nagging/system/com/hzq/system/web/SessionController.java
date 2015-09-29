/**
 * @(#)SessionController.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月11日 huangzhiqian 创建版本
 */
package com.hzq.system.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.base.BaseController;
import com.hzq.common.entity.Grid;
import com.hzq.common.entity.Json;
import com.hzq.common.util.AESUtil;
import com.hzq.system.constant.Constant;
import com.hzq.system.entity.ShiroUser;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Controller
@RequestMapping("system")
public class SessionController extends BaseController{
	@Autowired
	private SessionDAO sessiondao;
	
	@RequestMapping(value="sessions",method=RequestMethod.GET)
	@RequiresPermissions("session:query")
	public String showSessions(){
		return "system/sessions";
	}
	
	
	@RequestMapping(value="sessions",method=RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("session:query")
	public Grid<ShiroUser> getSessions() {
		
		Collection<Session> sessions =  sessiondao.getActiveSessions();
		List<ShiroUser> shirousers=new ArrayList<ShiroUser>();
		for(Session session:sessions){
			if(session.getTimeout()!=0L){
				 PrincipalCollection principalCollection =(PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				 if(principalCollection!=null){
					 ShiroUser shirouser=new ShiroUser();
					 shirouser=(ShiroUser)principalCollection.getPrimaryPrincipal();
					 shirouser.setSessionId(AESUtil.encrypt(session.getId().toString()));
					 shirousers.add(shirouser);
				 }
			}
		}
		return new Grid<ShiroUser>(shirousers, sessions.size());
	}

	@RequestMapping(value="sessions/{sessionId}")
	@ResponseBody
	@RequiresPermissions("session:delete")
	public Json forceLogout(@PathVariable("sessionId") String id) {
//		String sessionId=AESUtil.decrypt(id);
		if(StringUtils.equalsIgnoreCase(id, getShiroUser().getSessionId().toString())){
			return new Json(false,"干嘛踢自己");
		}
		try {
			String sid=AESUtil.decrypt(id);
			Session session = sessiondao.readSession(sid);
			if (session != null) {
				session.setAttribute(Constant.SHIRO_KICK_KEY, Boolean.TRUE);
				session.setTimeout(0L);
				return new Json(true,"踢出成功");
			}else{
				return new Json("该用户已被踢出");
			}
		} catch (UnknownSessionException e) {
			return new Json("session不存在");
		}
		
	}
	

}
