/**
 * @(#)Main.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月4日 huangzhiqian 创建版本
 */
package com.hzq.test.a;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;

import com.hzq.common.util.SaltGenerator;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class Main {
	public static void main(String[] args) {
		String salt=SaltGenerator.getSalt(16);
		SimpleHash hash = new SimpleHash("SHA-1", "123456", salt, 1024);
		String psw=hash.toHex();
		System.out.println(psw);
		System.out.println(salt);
		
		
		
		System.out.println(new BigDecimal("0.00").setScale(0).toPlainString());
		
		
	}
	
	
}

