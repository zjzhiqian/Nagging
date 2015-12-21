<xmp>
	/**
	 * 以下演示了通过原型的创建方式，使用基于原型的创建可以将属性和方法
	 * 设置为Person专有的，不能再通过window来调用
	 */
	function Person(){
		
	}
	Person.prototype.name = "Leon";
	Person.prototype.age = 23;
	Person.prototype.say = function() {
		alert(this.name+","+this.age);
	}
	var p1 = new Person();
	var p2 = new Person();
	p1.say();
	p2.say();
	alert(p1.say==p2.say)
	//通过window没有办法调用say方法，如此就完成了封装
	// say();
<xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/js_study/include.jsp"%>