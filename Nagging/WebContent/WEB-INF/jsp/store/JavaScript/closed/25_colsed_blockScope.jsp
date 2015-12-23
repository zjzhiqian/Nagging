<xmp>
	for(var i=0;i<10;i++) {
		
	}
	//在js中没有块作用域，不管是使用循环还是判断之后，这个变量会一直存在
	/*
	 * 所以当在全局使用某个变量进行循环或者判断之后，这个变量可能会影响
	 * 到函数中的变量，所以在特殊情况不要使用全局变量，而且使用全局变量
	 * 在作用域链的最上层，访问是最慢的
	 */
	var i;//此时会认为是无效语句，除非使用var i = 0;
	console.log(i);
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>