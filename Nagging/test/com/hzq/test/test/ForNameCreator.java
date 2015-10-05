/**
 * @(#)ForNameCreator.java
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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author huangzhiqian
 */
public abstract class ForNameCreator extends PetCreator {

	private static List<Class<? extends Pet>> types=new ArrayList<Class<? extends Pet>>();
	private static String[] typeNames={"com.hzq.test.test.Cat","com.hzq.test.test.Dog"};
	static{
		loader();
	}
	
	@SuppressWarnings("unchecked")
	private static void loader(){
		for(String name:typeNames){
			try {
				types.add((Class<? extends Pet>) Class.forName(name));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	@Override
	public List<Class<? extends Pet>> types() {
		return types;
	}

}

