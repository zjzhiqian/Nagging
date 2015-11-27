/**
 * @(#)ReferenceTest.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年11月27日 huangzhiqian 创建版本
 */
package com.hzq.test.a;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class ReferenceTest {
	   static void StrongReference(){
		   Object object = new Object();
	        // 通过赋值创建 StrongReference
	        Object referent = object;
	        System.out.println(referent == object);
	        /************** true **********************/
	        object = null;
	        System.gc();
	        // StrongReference 在 GC 后不会被回收
	        System.out.println(null == referent);
	        /************** false **********************/
	   }
	   
	   @SuppressWarnings({ "rawtypes", "unchecked" })
	   static  void WeakReference() {
	        Object object = new Object();
	        // 创建 WeakReference
	        WeakReference referent = new WeakReference(object);
	        System.out.println(referent.get()==object);
	        object = null;
	        System.gc();
	        //一旦没有指向 referent 的强引用, WeakReference 在 GC 后会被自动回收
	        System.out.println(null == referent.get());
	        /************** false **********************/
	    }
	   
	   static void weakMap(){
		   	Map weakHashMap = new WeakHashMap();
	        Object key = new Object();
	        Object value = new Object();
	        weakHashMap.put(key, value);
	        System.out.println(weakHashMap.containsValue(value));
	        key = null;
	        System.gc();
	        /**
	         * 等待无效 entries 进入 ReferenceQueue 以便下一次调用 getTable 时被清理
	         */
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        /**
	         * 一旦没有指向 key 的强引用, WeakHashMap 在 GC 后将自动删除相关的 entry
	         */
	        System.out.println(weakHashMap.containsValue(value));
	   }
	   
		
	   public static void main(String[] args) {
	        StrongReference();
	        System.out.println("**********************");
	        WeakReference();
	        System.out.println("**********************");
	        weakMap();
	    }

}

