/**
 * @(#)ThreadLocaleTest.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年11月30日 huangzhiqian 创建版本
 */
package com.hzq.store.code.study;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * ThreadLocale 线程本地存储,5个线程用到了value,每个线程都由Random生成的值
 * @author huangzhiqian
 */
public class ThreadLocaleTest {
	private static ThreadLocal<Integer> value=new ThreadLocal<Integer>(){
		private Random rand=new Random(47);
		@Override
		protected synchronized Integer initialValue(){
			return rand.nextInt(1000);
		}
	};
	
	public static void increment(){
		value.set(value.get()+1);
	}
	
	public static int get(){
		return value.get();
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service=Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			service.execute(new Accessor(i));
		}
		TimeUnit.SECONDS.sleep(3);
		service.shutdown();
	}
}

class Accessor implements Runnable{
	private final int id;
	public Accessor(int id) {
		this.id=id;
	}
	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			ThreadLocaleTest.increment();
			System.out.println(this);
			Thread.yield();
		}
	}
	
	public String toString(){
		return "#"+id+"     "+ThreadLocaleTest.get();
	}
	
	
}