package com.hzq.test.test;

import org.apache.catalina.core.ApplicationContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hzq.common.util.SpringContextUtils;

/**
 * @author hzq
 *
 *         2015年9月3日 下午12:44:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 整合
@ContextConfiguration(locations = "classpath:applicationContext-spring.xml")
// 加载配置
public class ShiroTest {
	/*@Autowired
	private DefaultWebSecurityManager securityManager;
	@Autowired
	SessionDAO sessiondao;
*/
	@Test
	public void stest() throws InterruptedException {
//		SecurityUtils.setSecurityManager(securityManager);
//		Subject subject = SecurityUtils.getSubject();
//		System.err.println(subject);
//		UsernamePasswordToken token = new UsernamePasswordToken("123", "123456");
//		subject.login(token);
//		System.out.println(subject.isPermitted("item:query"));
//		new Thread(new Task()).start();
//		Thread.sleep(5000);
//		System.err.println(sessiondao.getActiveSessions().size());
		// System.out.println(session.getStartTimestamp());
		// System.out.println(session.getLastAccessTime());
		// System.out.println(session.getId());
		// System.out.println(session.getHost());
		// System.out.println(session.getTimeout());
		// Thread.sleep(3500);
		// session.touch();
	}

	class Task implements Runnable {

		@Override
		public void run() {
			Subject subject = SecurityUtils.getSubject();
			System.err.println(subject);
			UsernamePasswordToken token = new UsernamePasswordToken("666", "123456");
			subject.login(token);
		}
	}
	
	
	
	
	
	
	
	
	@Test
	public void mtt(){
		boolean flag=SpringContextUtils.containsBean("sysRoleServiceImpl");
		System.out.println(flag);
		
		flag=SpringContextUtils.isSingleton("sysRoleServiceImpl");
		System.out.println(flag);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
