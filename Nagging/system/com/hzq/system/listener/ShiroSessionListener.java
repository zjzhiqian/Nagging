/**
 * @(#)ShiroSessionListener.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月4日 huangzhiqian 创建版本
 */
package com.hzq.system.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Component
public class ShiroSessionListener implements SessionListener {

	 @Override  
	    public void onStart(Session session) {//会话创建时触发  
	        System.out.println("会话创建：" + session.getId());  
	    }  
	    @Override  
	    public void onExpiration(Session session) {//会话过期时触发  
	        System.out.println("会话过期：" + session.getId());  
	    }  
	    @Override  
	    public void onStop(Session session) {//退出/会话过期时触发  
	        System.out.println("会话停止：" + session.getId());  
	    }    

}

