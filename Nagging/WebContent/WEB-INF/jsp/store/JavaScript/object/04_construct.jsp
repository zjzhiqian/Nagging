<xmp>
	/**
	 * 通过构造函数的方式创建，和基于工厂的创建类似
	 * 最大的区别就是函数的名称就是类的名称，按照java的约定，第一个
	 * 字母大写。使用构造函数创建时，在函数内部是通过this关键字来
	 * 完成属性的定义
	 */
	function Person(name,age) {
		this.name = name;
		this.age = age;
		//以下方式带来的问题是所有的对象都会为该行为分配空间
		// this.say = function() {
			// alert(this.name+","+this.age);
		// }
		this.say = say;
	}
	/**
	 * 将行为设置为全局的行为，如果将所有的方法都设计为全局函数的时候
	 * 这个函数就可以被window调用，此时就破坏对象的封装性
	 * 而且如果某个对象有大 量的方法，就会导致整个代码中充斥着大量的全局函数
	 * 这样将不利于开发
	 */
	function say() {
		alert(this.name+","+this.age);
	}
	/*
	 * 通过new Person来创建对象
	 */
	var p1 = new Person("Leon",22);
	var p2 = new Person("Ada",32);
	console.log(p1)
	console.log(p2)
	/**
	 * 使用构造函数的方式可以通过以下方式来检测
	 * 对象的类型
	 */
	console.log(p1 instanceof Person);
	/**
	 * 可以将函数放到全局变量中定义，这样可以让类中的行为指向
	 * 同一个函数
	 */
	console.log(p1.say==p2.say);
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>