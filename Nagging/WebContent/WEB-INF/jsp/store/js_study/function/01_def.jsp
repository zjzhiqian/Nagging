<xmp>
	//第一种定义方式
	function fn1(){
		console.log("fn1")
	}
	
	//函数就是一个非常特殊的对象，是一个Function类的实例，其实在内存中存储的操作是通过一个键值对来存储的
	console.log(typeof fn1)
	
	//由于函数是一个对象，所以可以通过如下方式定义
	//以下是通过函数的拷贝来完成赋值，两个引用并没有指向同一个对象
	var fn2 = fn1;
	fn2();
	fn1 = function() {
		console.log("fnn1")
	}
	/**
	 * 函数虽然是一个对象，但是却和对象有一些区别，对象是通过引用的指向完成对象的赋值的，而函数却是通过对象的拷贝来完成的
	 * 所以fn1虽然变了，并不会影响fn2
	 */
	fn2();
	fn1();

	var o1 =new Object()
	o2 = o1 
	o2.name ='123'
	console.log(o1.name)
</xmp>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/js_study/include.jsp"%>