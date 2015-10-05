/**
 * @(#)PetCreator.java
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

import java.util.List;
import java.util.Random;

/**
 * 
 * 
 * @author huangzhiqian
 */
public abstract class PetCreator {
	private Random rand=new Random(47);
	//模板方法设计模式
	public abstract List<Class<? extends Pet>> types();
	public Pet randomPet(){
		int n=rand.nextInt(types().size());
		try {
			return types().get(n).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
}

