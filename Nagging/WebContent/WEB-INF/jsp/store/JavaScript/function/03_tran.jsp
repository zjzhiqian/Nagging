<xmp>
	/**
	 * 由于函数是对象，所以可以直接把函数通过参数传递进来
	 */
	function callFun(fun,arg) {
		//第一个参数就是函数对象
		return fun(arg);
	}
	
	function sum(num) {
		return num+100;
	}
	
	function say(str) {
		console.log('say '+str)
	}
	//var say = xxx
	//调用了say函数
	callFun(say,"Leon");
	//调用了sum函数
	console.log(callFun(sum,20))
	
	function fn1(arg) {
		/**
		 * 此时返回的是一个函数对象
		 */
		var rel = function(num) {
			return arg+num;
		}
// 		function rel(num){
// 			return arg+num;
// 		}
		return rel;
	}
	//此时f是一个函数对象，可以完成调用
	var f = fn1(20);
	
	console.log(f(20))
	console.log(f(11))
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>