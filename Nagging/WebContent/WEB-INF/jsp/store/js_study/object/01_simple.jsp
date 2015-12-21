<xmp>
	/**
	 * 在js中并不存在类，所以可以直接通过Object来创建对象
	 * 但是使用如下方式创建，带来最大的问题是，由于没有类的约束
	 * 无法实现对象的重复利用，并且没有一种约定，在操作时会带来问题
	 */
	var person = new Object();
	person.name = "Leon";
	person.age = 33;
	person.say = function() {
		alert(this.name+","+this.age);
	}
	
	console.log(person.say)
<xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/js_study/include.jsp"%>