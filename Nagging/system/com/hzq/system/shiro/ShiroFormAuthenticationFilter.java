package com.hzq.system.shiro;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzq.common.util.AESUtil;
import com.hzq.common.util.AjaxUtil;
import com.hzq.common.util.Utils;
import com.hzq.system.constant.Constant;
import com.hzq.system.entity.ShiroUser;
import com.hzq.system.service.SysUserService;

/**
 * 自定义Filter用于拦截验证码,对登录时用户名密码进行后台校验,Session失效时对Ajax请求处理
 * 
 * @author hzq
 *
 * 2015年9月1日 下午9:59:27
 */

public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {
	private String cacheKey;
	private String kickoutUrl; // 踢出后到的地址
	private boolean kickoutAfter = false; // 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
	private int maxSession = 1; // 同一个帐号最大会话数 默认1
	private SessionManager sessionManager;
	private Cache<String, Deque<Serializable>> cache;

	@Autowired
	private SysUserService sysUserService;

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cache = cacheManager.getCache(cacheKey);
	}

	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}

	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@Override
	public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Subject subject = getSubject(request, response);
		Session shiroSession = subject.getSession(false);
		if (shiroSession == null) {
			super.doFilterInternal(request, response, chain);
		} else if (shiroSession.getAttribute(Constant.SHIRO_KICK_KEY) == null) {
			// 用户没有被踢出,正常执行
			super.doFilterInternal(request, response, chain);
		} else if (shiroSession.getAttribute(Constant.SHIRO_KICK_KEY) != null) {
			// 用户没有被踢出,使之失效
			subject.logout();
			if (AjaxUtil.isAjaxRequest(req)) {
				PrintWriter out = res.getWriter();
				res.setStatus(700);
				out.flush();
				out.close();
			} else {
				WebUtils.issueRedirect(request, response, kickoutUrl);
			}
		}

	}


	/**
	 * Session失效时,尝试访问页面被拒绝 时会调用这个方法 返回true
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = WebUtils.toHttp(request);
		HttpServletResponse res = WebUtils.toHttp(response);
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		Subject subject = getSubject(request, response);
		// 判断是否是Login 的url Request
		if (isLoginRequest(request, response)) {
			// 判断是不是登陆表单
			if (isLoginSubmission(request, response)) {

				if (username != null) {
					if (!(username.length() > 0 && username.length() < 10)) {
						req.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "usernameValidError");
						return true;
					}
				}
				if (password != null) {
					if (!(password.length() >= Constant.PSW_MIN_LENGTH && password.length() <= Constant.PSW_MAX_LENGTH)) {
						req.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "passwordValidError");
						return true;
					}
				}
				// 从session获取正确验证码
				HttpSession session = req.getSession();
				// 取出session的验证码（正确的验证码）
				String validateCode = (String) session.getAttribute("validateCode");
				// 取出页面的验证码
				// 输入的验证和session中的验证进行对比
				String randomcode = req.getParameter("randomcode");
				if(randomcode==null||validateCode==null){
					req.setAttribute("shiroLoginFailure","validataCodeError");
					//拒绝访问，不再校验账号和密码
					return true;
				}
				if(!randomcode.toLowerCase().equals(validateCode.toLowerCase())){
					
//				 	如果校验失败，将验证码错误失败信息，通过shiroLoginFailure设置到request中
					//TODO 取消万能验证码1111
					if(!"1111".equals(randomcode)){
						req.setAttribute("shiroLoginFailure","validataCodeError");
						//拒绝访问，不再校验账号和密码
						return true;
					}
				}	
				boolean logInflag = executeLogin(request, response);// 尝试登陆,返回是否登陆成功(返回false是登陆成功,返回true是登陆失败)
				// 登录成功,如果在异地有登录,则踢出那边用户
				if (!logInflag) {
					
					
					
					KickSessionOtherPlace(username, subject);
				}
				return logInflag;
			} else {
				// 登录的get请求
				// allow them to see the login page ;)
				return true;
			}
				
		} else {

			// 如果是Ajax,交给通用Ajax处理(在include.jsp设置)
			if (AjaxUtil.isAjaxRequest(req)) {
				PrintWriter out = res.getWriter();
				res.setStatus(401);
				out.flush();
				out.close();
				return false;
			}
			// saveRequestAndRedirectToLogin(request,response);(这条登陆成功会跳转到上一个请求页面)
			redirectToLogin(request, response);// (这条登陆成功后直接跳转到配置文件的页面)
			return false;
		}

	}

	@Override
	/**
	 * 登录成功后的操作
	 */
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		ShiroUser shirouser = (ShiroUser) subject.getPrincipal();
		Serializable sessionId=subject.getSession().getId();
		shirouser.setSessionId(AESUtil.encrypt(sessionId.toString()));
		
		
		String lastIp=Utils.getIP((HttpServletRequest) request);
		sysUserService.updateUserForLogin(shirouser.getId(),lastIp);
		SecurityUtils.getSubject().getSession().setAttribute("user", shirouser);
		sysUserService.addLogInLog(shirouser,lastIp);
			
		return super.onLoginSuccess(token, subject, request, response);
	}

	/**
	 * 登陆失败的操作 返回登陆页面,把异常放到request域中
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		setFailureAttribute(request, e);
		// String className = ae.getClass().getName();
		// request.setAttribute(getFailureKeyAttribute(), className);
		// shiroLoginFailure
		return true;
	}
	
	
	private static Lock lock=new ReentrantLock();
	/**
	 * 如果在其他地方已经登录，尝试踢出
	 * 
	 * @param username
	 * @param subject
	 * @param session
	 */
	private void KickSessionOtherPlace(String username, Subject subject) {
		Session shiroSession = subject.getSession();
		Serializable sessionId = shiroSession.getId();
		// 避免并发操作同一个cache
		try{
			lock.lock();
			Deque<Serializable> deque = cache.get(username);
			if (deque == null) {
				deque = new LinkedList<Serializable>();
				cache.put(username, deque);
			}

			// 如果队列里没有此sessionId，且用户没有被踢出;放入队列
			if (!deque.contains(sessionId) && shiroSession.getAttribute(Constant.SHIRO_KICK_KEY) == null) {
				deque.push(sessionId);
			}

			// 如果队列里的sessionId数超出最大会话数,开始踢人
			while (deque.size() > maxSession) {
				Serializable kickoutSessionId = null;
				if (kickoutAfter) { // 如果踢出后者
					kickoutSessionId = deque.removeFirst();
				} else { // 否则踢出前者
					kickoutSessionId = deque.removeLast();
				}
				try {
					Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
					if (kickoutSession != null) {
						kickoutSession.setAttribute(Constant.SHIRO_KICK_KEY, true);
					}
				} catch (Exception e) {// ignore exception
				}
			}
		}finally{
			lock.unlock();
		}
		
	}

}
