/**
 * @(#)LinedStack.java
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
/**
 * 
 * 
 * @author huangzhiqian
 */
public class LinkStack<T> {
	private static class Node<U>{
		U item;
		Node<U> next;
		
		Node(){
			this.item=null;
			this.next=null;
		}
		Node(U item,Node<U> top){
			this.item=item;
			this.next=top;
		}
		
		boolean end(){
			return item==null&&next==null;
		}
	}
	private Node<T> top=new Node<T>();
	
	public void push(T item){
		top=new Node<T>(item,top);
	}
	
	public T pop(){
		T rs=top.item;
		if(!top.end()){
			top=top.next;
		}
		return rs;
	}
	
	public static void main(String[] args) {
		LinkStack<String> stack=new LinkStack<String>();
		for(String s:"a dwe weqj".split(" ")){
			stack.push(s);
		}
		String s;
		while((s=stack.pop())!=null){
			System.out.println(s);
		}
		
	}
	
	
	
}

