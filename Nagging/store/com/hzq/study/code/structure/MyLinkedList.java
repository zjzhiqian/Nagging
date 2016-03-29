/** 
 * @author hzq
 * date: 2016年3月28日 下午10:55:13 <br/> 
 */
package com.hzq.study.code.structure;

import java.util.Iterator;

public class MyLinkedList<T> implements Iterable<T> {

	private static class Node<T> {
		private T data;
		private Node<T> pre;
		private Node<T> next;

		public Node(T data, Node<T> pre, Node<T> next) {
			this.data = data;
			this.pre = pre;
			this.next = next;
		}
	}

	private Node<T> begin;
	private Node<T> end;
	private int theSize;
	private int modCount;

	public MyLinkedList() {
		clear();
	}

	public void clear() {
		begin = new Node<T>(null, null, null);
		end = new Node<T>(null, begin, null);
		begin.next = end;

		modCount++;
	}

	public int size() {
		return theSize;
	}

	public boolean isEmpty() {
		return theSize == 0;
	}

	public boolean add(T t) {
		add(size(), t);
		return true;
	}

	public boolean add(int idx, T t) {
		addBefore(getNode(idx), t);
		return true;
	}

	private void addBefore(Node<T> node, T t) {
		Node<T> newNode = new Node<T>(t,node.pre,node);
		node.pre.next=newNode;
		node.pre=newNode;
		modCount++;
		theSize++;
	}
	
	public Node<T> remove(int idx){
		return remove(getNode(idx));
	}
	
	
	private Node<T> remove(Node<T> node) {
		node.next.pre=node.pre;
		node.pre.next=node.next;
		modCount++;
		theSize--;
		return node;
	}

	private Node<T> getNode(int idx) {
		Node<T> retVal;
		if (idx < 0 || idx > size()) {
			throw new ArrayIndexOutOfBoundsException("index : "+idx);
		}
		
		if (idx < size() % 2) {
			retVal = begin;
			for (int i = 0 ;i < idx;i++){
				retVal = retVal.next;
			}
		}else{
			retVal = end;
			for (int i = size(); i>idx;i--){
				retVal = retVal.pre;
			}
		}
		return retVal;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Iterator iterator() {
		return new MIterator();
	}

	@SuppressWarnings("unused")
	private class MIterator<T> implements Iterator<T>{
		@SuppressWarnings("unchecked")
		private Node<T> current = (Node<T>) begin.next;
		private int expectModiCount = modCount ;
		private boolean okToRemove = false;
		
		@Override
		public boolean hasNext() {
			return current !=end;
		}

		@Override
		public T next() {
			if(hasNext()){
				T nextItem = current.data;
				current = current.next;
				okToRemove = true;
				return nextItem;
			}
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	
	
	
	public static void main(String[] args) {
		MyLinkedList<Integer> t =new MyLinkedList<Integer>();
		t.add(0);t.add(1);t.add(2);t.add(3);
		for(int i : t ){
			System.out.println(i);
		}
		
		
	}
	
	
	
	
	
}
