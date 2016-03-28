/**
 * @(#)MM.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年2月25日 huangzhiqian 创建版本
 */
package com.hzq.store.study.code;

import java.lang.reflect.Field;
import java.util.Arrays;

import com.hzq.system.entity.SysPermission;

/**
 * 
 * Integer 内部类Cache  -128~127是缓存，其它要new Integer(1000)
 * @author huangzhiqian
 */
public class IntegerTest {

	private static void testInteger() {
		Integer a = 1000, b = 1000;
		System.out.println(a == b);// 1
		Integer c = 100, d = 100;
		System.out.println(c == d);// 2

		int e = 1000, f = 1000;
		System.out.println(e == f);// 1
		int g = 100, h = 100;
		System.out.println(g == h);// 2
		
		Integer i = new Integer(2);
		Integer j = new Integer(2);
		System.out.println(i==j);

	}

	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
		testInteger();
		System.out.println("========================");
		Class<?> cache = Integer.class.getDeclaredClasses()[0]; // 1
		Field myCache = cache.getDeclaredField("cache"); // 2
		myCache.setAccessible(true);// 3
		Integer[] newCache = (Integer[]) myCache.get(cache); // 4
		System.out.println(Arrays.toString(newCache));
		newCache[132] = newCache[133]; // 5

		int a = 2;
		int b = a + a;
		System.out.printf("%d + %d = %d", a, a, b); //

	}
}
