/** 
 * @author hzq
 * date: 2016年3月28日 上午9:04:13 <br/> 
 */
package com.hzq.study.code.structure;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements Iterable<T> {

	private static final int DEFAULT_CAPACITY = 10;

	private int theSize;
	public T[] theItems;

	public MyArrayList() {
		clear();
	}

	public boolean isEmpty() {
		return theSize == 0;
	}

	public void trimToSize() {
		ensureCapacity(size());
	}

	public void clear() {
		theSize = 0;
		ensureCapacity(DEFAULT_CAPACITY);
	}

	public int size() {
		return theSize;
	}

	@SuppressWarnings("unchecked")
	public void ensureCapacity(int capaciti) {
		if (theSize > capaciti) {
			return;
		}

		T[] old = theItems;
		theItems = (T[]) new Object[capaciti];
		for (int i = 0; i < size(); i++) {
			theItems[i] = old[i];
		}
	}

	public T get(int idx) {
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		return theItems[idx];
	}

	public T set(int idx, T val) {

		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		T old = theItems[idx];
		theItems[idx] = val;
		return old;

	}

	public void add(T val){
		add(size(),val);
	}
	
	public void add(int idx, T val) {
		if(theItems.length == size()){
			ensureCapacity(size()*2+1);
		}
		for(int i = theSize ; i > idx ; i --){
			theItems[i] = theItems[i-1];
		}
		theItems[idx] = val;
		theSize ++;
	}
	
	public T remove(int idx){
		T removeItem = theItems[idx];
		for (int i = idx ; i < size() ; i++){
			theItems[i] = theItems [i+1];
		}
		theSize--;
		return removeItem;
		
		
	}
	

	@Override
	public Iterator<T> iterator() {
		return new MyListIterator<T>();
	}
	
	
	@SuppressWarnings("hiding")
	private  class  MyListIterator<T> implements Iterator<T>{
		
		private int current = 0 ;
		
		@Override
		public boolean hasNext() {
			return current < size();
		}

		@SuppressWarnings("unchecked")
		@Override
		public T next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			return (T) theItems[current++];
		}
		
		public void remove(){
			MyArrayList.this.remove(--current);
		}
		
	}
	
}
