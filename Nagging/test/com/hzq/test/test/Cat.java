/**
 * @(#)Cat.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年10月5日 huangzhiqian 创建版本
 */
package com.hzq.test.test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

/**
 * 
 * 
 * @author huangzhiqian
 */

public class Cat extends Pet {

	public static void main(String[] args) {
		File path = new File("E:\\index");
		String[] list;
		list = path.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File arg0, String arg1) {
				return true;
			}
		});
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		for (String s : list) {
			System.out.println(s);
		}
	}
	
	

}
