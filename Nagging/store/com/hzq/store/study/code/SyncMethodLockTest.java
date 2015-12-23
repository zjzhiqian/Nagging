/**
 * @(#)TestThread.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月23日 huangzhiqian 创建版本
 */
package com.hzq.store.study.code;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 
 * 
 * @author huangzhiqian
 */
public class SyncMethodLockTest {
		
	
	
	public static void main(String[] args) {
		Person p1 = new Person("p1");
//		Person p2 = new Person("p2");
		ExecutorService service = Executors.newFixedThreadPool(3);
		service.execute(new LockTask(p1));
		service.execute(new LockTask(p1));
//		service.execute(new LockTask(p2));
		service.shutdown();
	}
	
}


class LockTask implements Runnable {
	private Person p ;
	LockTask(Person p ){
		this.p = p ;
	}
	@Override
	public void run() {
		p.ShowThis();	
	}
	
}

class Person{
	
	private String name;
	
	public Person(String name){
		this.name = name ;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public synchronized void ShowThis(){
		System.out.println("...1111111");
		System.out.println(this);
	}

	@Override
	public String toString() {
		return "Person [name=" + name + "]";
	}
	
	
}