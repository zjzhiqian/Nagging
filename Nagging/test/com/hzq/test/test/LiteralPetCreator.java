/**
 * @(#)LiteralPetCreator.java
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
public class LiteralPetCreator extends PetCreator {

	private static List<Class<? extends Pet>> types=new ArrayList<Class<? extends Pet>>();
	static{
		loader();
	}
	
	private static void loader(){
		types.add(Cat.class);
		types.add(Dog.class);
	}

	
	@Override
	public List<Class<? extends Pet>> types() {
		return types;
	}

}

