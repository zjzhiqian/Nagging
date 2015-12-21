<xmp>
	// function sum(num1,num2) {
		// return num1+num2;
	// }
	var sum = function(num1,num2) {
		return num1+num2;
	}
	
	// function sum(num1) {
		// return num1+100;
	// }
	/**
	 * 此时sum所指向的空间已经从有两个参数的函数变化到只有num1的函数中
	 * 在调用的时候就只会调用只有num1的函数
	 * 特别指出：函数的参数和调用没有关系，如果函数只有一个参数，但是却传入
	 * 了两个参数，仅仅只会匹配一个
	 * 所以在js中函数不存在重载
	 */
	var sum = function(num1) {
		return num1+100;
	}
	
	//函数有如下一种定义方式
	/**
	 * 如下定义方式等于定义了一个
	 * function fn(num1,num2){
	 * 	  alert(num1+num2);
	 * }
	 * 所以通过以下的例子，充分的说明函数就是一个对象
	 */
	var fn = new Function("num1","num2","alert('fun:'+(num1+num2))");
	fn(12,22);
	alert(sum(19));
	
	alert(sum(19,20));
</xmp>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/js_study/include.jsp"%>