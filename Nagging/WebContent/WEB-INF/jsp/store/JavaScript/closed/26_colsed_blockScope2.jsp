<xmp>
	/*
	 * 在一个团队进行开发时，可能会涉及到定义同名的全局变量，所以在开发中
	 * 一定养成如下习惯，将全局变量的代码放到一个匿名函数，并且马上调用
	 * 匿名函数，这样也可以执行全局变量的代码，但是这些变量就被控制在开发
	 * 人员想要控制的作用域中了
	 */
	//在function的{}后不能直接调用，一定要加上括号
	// function(){
		// for(var i=0;i<10;i++) {
// 		
		// }	
	// }();
	(function(){
		for(var i=0;i<10;i++) {
		
		}	
	})();
	
	function fn() {
		alert(i);
	}
	fn();
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>