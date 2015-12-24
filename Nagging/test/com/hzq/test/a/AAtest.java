/**
 * @(#)AAtest.java
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
/**
 * 
 * 
 * @author huangzhiqian
 */
public class AAtest {
    private final void test(int i){
    	synchronized (this) {
			
    		
    		while(true){
    			if(i==2){
    				try {
    					wait();
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
    			}else{
    				System.out.println(".....");
    			}
    			
    			
    		}
    		
		}
    	
    	
    	
    }
	
	
	public static void main(String[] args) {
		
		AAtest a = new AAtest();
		a.test(2);
//		test(2);
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
}

