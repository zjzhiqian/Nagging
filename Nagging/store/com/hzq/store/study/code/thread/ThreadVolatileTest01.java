package com.hzq.store.study.code.thread;

import java.util.concurrent.TimeUnit;
/**
 * 
 * volatile 只能保证 多个线程内存之间的可见性,
 * @author huangzhiqian
 */
public class ThreadVolatileTest01 implements Runnable{
	private volatile int i = 0;
	public void showI(){
		System.out.println(i);
	}
	public void run(){
		//多个线程读取时的值是最新的,如果多个线程读取到了相同的值,
		//i++操作完后的值也相同,写入主存的值也会相同,产生了i<100000的情况
		i++;
	}
	
	public static void main(String[] args) throws InterruptedException {
		ThreadVolatileTest01 task = new ThreadVolatileTest01();
		for(int i = 0 ; i < 100000 ;i ++){
			new Thread(task).start();
		}
		TimeUnit.SECONDS.sleep(3);
		task.showI();
	}
}
