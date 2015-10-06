/**
 * @(#)Fibonacci.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年10月6日 huangzhiqian 创建版本
 */
package com.hzq.test.generic;

import java.util.Iterator;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class Fibonacci {
	private int count = 0;

	Integer next() {
		return fib(count++);
	}

	private Integer fib(int i) {
		if (i < 2)
			return 1;
		else
			return fib(i - 1) + fib(i - 2);
	}

	public static void main(String[] args) {
		
		Fibonacci fib = new Fibonacci();
		for (int i = 0; i < 10; i++) {
			System.out.println(fib.next());
		}
	}

}

class IterableFibonacci extends Fibonacci implements Iterable<Integer> {
	private int size=0;
	
	public IterableFibonacci(int size) {
		this.size=size;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			
			@Override
			public Integer next() {
				size--;
				return IterableFibonacci.this.next();
			}
			
			@Override
			public boolean hasNext() {
				return size>0;
			}
		};
	}

}
