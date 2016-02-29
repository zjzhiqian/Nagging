/**
 * @(#)RuntimeExceptionTest.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年2月29日 huangzhiqian 创建版本
 */
package com.hzq.test.a;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class RuntimeExceptionTest {
	public static void main(String[] args) {
		try{
			
			System.out.println("1");
			throw new RuntimeException();
			
		}catch (RuntimeException e) {
			System.out.println("RuntimeException");
		}catch(Exception e ){
			System.out.println("213123");
		}
	}
}

