
package com.hzq.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring的工具类，用来获得配置文件中的bean
 * @author hzq
 *
 * 2015年8月17日 下午8:05:31
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/*** 
	 * 根据一个bean的id获取配置文件中相应的bean 
	 * @param name 
	 * @return 
	 * @throws BeansException 
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	/*** 
	 * 返回bean
	 * @param name 
	 * @param requiredType 
	 * @return 
	 * @throws BeansException 
	 */
	public static <T> T getBean(String name, Class<T> requiredType)
			throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	/** 
	 * 是否有bean
	 * @param name 
	 * @return boolean 
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * 判断bean singleton or prototype
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name)
			throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	/** 
	 * @param name 
	 * @return  
	 * @throws NoSuchBeanDefinitionException 
	 */
	public static Class<?> getType(String name)
			throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

  

}

