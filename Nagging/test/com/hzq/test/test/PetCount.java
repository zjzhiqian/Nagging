/**
 * @(#)PetCount.java
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

import java.util.HashMap;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class PetCount {
	static class PetCounter extends HashMap<String, Integer> {
		private static final long serialVersionUID = 1L;

		public synchronized void count(String type) {
			Integer quantity = get(type);
			if (quantity == null) {
				put(type, 1);
			} else {
				put(type, quantity + 1);
			}
		}
	}

	public static void countPets(PetCreator creator) {
//			PetCounter counter=new PetCounter();
			
	}

}
