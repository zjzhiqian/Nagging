<xmp>
	fn1();
	//不会报错,对于通过function fn()这种写法来定义的函数，永远都会被最先初始化
	function fn1() {
		console.log("fn1");
	}
	
	fn2();
	//匿名函数,会报错
	var fn2 = function() {
		console.log("fn2");
	}	
	//使用如下方式定义函数，不会被先执行，如果在之前调用该函数就会报错
	/**
	 * 以下函数的定义方式是现在内存中创建了一块区域，之后通过一个fn2的变量
	 * 指向这块区域，这块区域的函数开始是没有名称的 ，这种函数就叫做匿名函数
	 */
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>