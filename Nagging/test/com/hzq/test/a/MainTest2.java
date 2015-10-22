/**
 * @(#)MainTest2.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年10月22日 huangzhiqian 创建版本
 */
package com.hzq.test.a;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class MainTest2 {
	private static List<String> m=Collections.synchronizedList(new ArrayList<String>());
	
	public static void main(String[] args) {
		m.add("2");
		m.clear();
		System.out.println(m);
	}

}

