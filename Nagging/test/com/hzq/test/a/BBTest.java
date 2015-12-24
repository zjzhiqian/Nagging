/**
 * @(#)BBTest.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月24日 huangzhiqian 创建版本
 */
package com.hzq.test.a;

import java.util.Vector;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class BBTest {
	public static void main(String args[]) {
		Vector obj = new Vector();
		Thread consumer = new Thread(new Consumer(obj));
		Thread producter = new Thread(new Producter(obj));
		consumer.start();
		producter.start();
	}
}

/* 消费者 */
class Consumer implements Runnable {
	private Vector obj;

	public Consumer(Vector v) {
		this.obj = v;
	}

	public void run() {
		synchronized (obj) {
			while (true) {
				try {
					if (obj.size() == 0) {
						obj.wait();
					}
					System.out.println("Consumer:goods have been taken");
					System.out.println("obj size: " + obj.size());
					obj.clear();
					obj.notify();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

/* 生产者 */
class Producter implements Runnable {
	private Vector obj;

	public Producter(Vector v) {
		this.obj = v;
	}

	public void run() {
		synchronized (obj) {
			while (true) {
				try {
					if (obj.size() != 0) {
						obj.wait();
					}
					obj.add(new String("apples"));
					obj.notify();
					System.err.println("Producter:obj are ready");
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
