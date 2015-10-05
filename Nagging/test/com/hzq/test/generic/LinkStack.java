package com.hzq.test.generic;
/**
 * 堆栈  末端哨兵
 * @author huangzhiqian
 * @date 2015年10月5日 下午8:22:06
 * @param <T>
 */
public class LinkStack<T> {
	private static class Node<U> {
		U item;
		Node<U> next;

		Node() {
			item = null;
			next = null;
		}

		Node(U item, Node<U> node) {
			this.item = item;
			this.next = node;
		}

		boolean end() {
			return item == null && next == null;
		}
	}

	private Node<T> top = new Node<T>();

	public void push(T item) {
		top = new Node<T>(item, top);
	}
	
	public T pop(){
		T result=top.item;
		if(!top.end()){
			top=top.next;
		}else{
			System.out.println("...ENd");
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		LinkStack<String> lss=new LinkStack<String>();
		for(String s:"I like football".split(" ")){
			lss.push(s);
		}
		String s=null;
		while((s=lss.pop())!=null){
			System.out.println(s);
		}
	}
}
