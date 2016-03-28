/** 
 * @author hzq
 * date: 2016年3月28日 上午9:20:19 <br/> 
 */  
package com.hzq.test.a;

import java.util.Arrays;
import java.util.Iterator;

import com.hzq.study.code.structure.MyArrayList;

public class Main {
	public static void main(String[] args) {
		MyArrayList<Integer> a =new MyArrayList<Integer>();
		a.add(3);
		a.add(4);
		System.out.println(Arrays.toString(a.theItems));
		Iterator c = a.iterator();
		while(c.hasNext()){
			System.out.println(c.next());
		}
	}
}
