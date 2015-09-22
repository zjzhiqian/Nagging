package com.hzq.study.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hzq.study.aop.ArithmeticCalculator;

public class Mtest {
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		ArithmeticCalculator arithmeticCalculator = (ArithmeticCalculator) ctx.getBean("arithmeticCalculatorImpl");
		
		System.out.println(arithmeticCalculator.getClass().getName());
		
		int result = arithmeticCalculator.add(11, 12);
		System.out.println("result:" + result);
		
		result = arithmeticCalculator.div(21, 3);
		System.out.println("result:" + result);
	}
	
}
