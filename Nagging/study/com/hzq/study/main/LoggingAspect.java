package com.hzq.study.main;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author hzq
 *
 * 2015年8月22日 下午1:14:39 
 */

public class LoggingAspect {
	
	
	public void dobefore(JoinPoint jionPoint){
		jionPoint.getSignature().getName();//方法名称
		List<Object> argsList=Arrays.asList(jionPoint.getArgs());
		System.out.println(argsList);
		System.err.println("2");
	}
	
	
	public void returning(JoinPoint jionPoint,Object rs){
		System.out.println("fanhui");
		System.out.println(rs);
	}
	
	
}
