/**
 * @(#)Login.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月25日 huangzhiqian 创建版本
 */
package com.hzq.system.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.entity.Json;
import com.hzq.system.service.UpdateDBService;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Controller

public class LoginController {
	
	@Autowired
	UpdateDBService updateDBService;
	
	private static final String IndexErrorKey="loginErrorMsg";
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String goIndexJsp(){
		return "index/index";
	}
	
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String login(){
//		if (SecurityUtils.getSubject().getSession() != null) {
//	        SecurityUtils.getSubject().getSession().stop();
//			System.err.println("这个会话已经有了");
//			shiroRealm.clearCached();
//			return "redirect:/";
//	    }
		return "login";
	}
	
	
	/**
	 * 此方法不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="login",method=RequestMethod.POST)
	public String index(HttpServletRequest request) throws Exception{
		
		//如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
				String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
				//根据shiro返回的异常类路径判断，抛出指定异常信息
				if(exceptionClassName!=null){
					if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
//						request.setAttribute(IndexErrorKey, "账号不存在");
						//避免被恶意扫描账号库
						request.setAttribute(IndexErrorKey, "用户名/密码错误");
					} else if (IncorrectCredentialsException.class.getName().equals(
							exceptionClassName)) {
						request.setAttribute(IndexErrorKey, "用户名/密码错误");
					} else if("validataCodeError".equals(exceptionClassName)){
						request.setAttribute(IndexErrorKey, "验证码错误");
					}  else if("usernameValidError".equals(exceptionClassName)) {
						request.setAttribute(IndexErrorKey, "用户名长度不合法");
					}else if("passwordValidError".equals(exceptionClassName)){
						request.setAttribute(IndexErrorKey, "密码长度不合法");
					}else if(ExcessiveAttemptsException.class.getName().equals(exceptionClassName)){
						request.setAttribute(IndexErrorKey, "验证失败次数过多,5分钟后重试");
					} else if(LockedAccountException.class.getName().equals(exceptionClassName)){
						request.setAttribute(IndexErrorKey, "账号被锁定,请联系管理员");
					}else if (AuthenticationException.class.getName().equals(exceptionClassName)){
						request.setAttribute(IndexErrorKey, "用户名或密码错误");
					}else{
						request.setAttribute(IndexErrorKey, "未知错误");
					}
					//如果认证错误 返回login页面(显示错误信息)
					return "login";
				}
				//登陆失败回退首页(会自动跳转到登录页面)
				//若一个已经登录的用户发送get请求/login实际上无论怎么输入表单数据都会跳转到index页面 
				return "redirect:/";
	}
	
	
	@RequestMapping("index")
	public String first(){
		return "index/first";
	}
	
	@RequestMapping("kicked")
	public String kickedRedirectToLogin(HttpServletRequest request){
		request.setAttribute("iskicked", true);
		return "login";
	}
	
	
	@RequestMapping("resetDB")
	@ResponseBody
	public Json resetDB(){
		Json j = new Json();
		try{
			updateDBService.restDB();
			j.setFlag(true);
			j.setMsg("更新成功");
		}catch(Exception e){
			e.printStackTrace();
			return j;
		}
		return j;
	}
		
}

