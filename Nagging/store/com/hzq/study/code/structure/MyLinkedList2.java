package com.hzq.study.code.structure;
import java.util.Iterator;


public class MyLinkedList2<T> implements Iterable<T>{

    private Node<T> head = null;
    private Node<T> tail = null;
    private int theSize = 0 ;

    private static class Node<T>{
        Node<T> next = null;
        T data = null;

        Node(Node<T> next,T data){
            this.next= next;
            this.data= data;
        }
    }


    public MyLinkedList2(){
        tail = new Node<T>(null,null);
        head = new Node<T>(tail,null);
        theSize=0;
    }

    public boolean isEmpty(){
        return theSize==0;
    }

    public int size(){
        return theSize;
    }

    public void add(T val){
        add(size(),val);
    }

    public void add(int idx,T val){

        //如果是刚初始化的,复制给head
        if(head.data==null){
            head.data=val;
            theSize++;
            return;
        }
        add(getNode(idx-1),getNode(idx),val);

    }

    public void add(Node<T> preNode,Node<T> node,T val){
        Node<T> newNode = new Node<T>(node,val);
        preNode.next=newNode;
        theSize++;
    }

    public Node<T> getNode(int idx) {
        if (idx < 0 || idx > size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Node<T> retVal;
        retVal = head;
        for (int i = 0; i < idx; i++) {
            retVal = retVal.next;
        }
        return retVal;
    }



    @Override
    public Iterator<T> iterator() {
        return new MyLinkList2Iterator<T>();
    }

    private class MyLinkList2Iterator<T> implements  Iterator<T>{

        private Node<T> current = (Node<T>)head;

        @Override
        public boolean hasNext() {
            return current!=tail;
        }

        @Override
        public T next() {
            if(hasNext()){
                T retVal = current.data;
                current=current.next;
                return retVal;
            }
            throw  new ArrayIndexOutOfBoundsException();
        }
    }


    public void printSelf(){
        for(T t:this){
            System.out.println(t);
        }
    }

    public static void main(String[] args){

        MyLinkedList2<Integer> m = new MyLinkedList2<Integer>();
        m.add(3);
        m.add(4);
        m.add(5);
        m.add(6);
        m.add(7);
        m.printSelf();
    }
}
