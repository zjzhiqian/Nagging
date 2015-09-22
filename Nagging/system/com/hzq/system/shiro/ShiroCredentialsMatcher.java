/**
 * @(#)ShiroCredentialsMatcher.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月2日 huangzhiqian 创建版本
 */
package com.hzq.system.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import net.sf.ehcache.Element;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

/**
 * 
 * 记录验证失败次数HashedCredentialsMatcher 用ehcache,超过密码尝试最大次数后会锁定账号
 * @author huangzhiqian
 */
public class ShiroCredentialsMatcher extends HashedCredentialsMatcher {

    private String cacheKey;
    private CacheManager cacheManager;
    private Cache<String,Element> passwordRetryCache;
    
    
    public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
    public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
		this.passwordRetryCache= this.cacheManager.getCache(cacheKey);
	}
    
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		
		String username = (String)token.getPrincipal();  
        //retry count + 1  
        Element element = passwordRetryCache.get(username);  
        if(element == null) {  
        	element=new Element(username, new AtomicInteger(0));
            passwordRetryCache.put(username, element);
        }  
      //用AtomicInteger 保证并发原子性
        AtomicInteger retryCount = (AtomicInteger)element.getObjectValue();  
        if(retryCount.incrementAndGet() > 5) {
            throw new ExcessiveAttemptsException(); 
        }  
  
        boolean matches = super.doCredentialsMatch(token, info);  
        if(matches) {  
            //clear retry count  
            passwordRetryCache.remove(username);  
        }  

		
		return super.doCredentialsMatch(token, info);
	}
}

