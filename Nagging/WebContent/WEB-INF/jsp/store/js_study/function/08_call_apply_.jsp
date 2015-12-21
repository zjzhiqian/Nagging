<xmp>
<!-- 	function.apply(this,arguments) 某个函数调用 方法,arguments是数组 this是调用该方法的对象-->
<!-- 	function.call(this,arg1,arg2,arg3) call是通过参数列表来完成传递，其他和apply没有任何区别 -->

		function sum(num1, num2) {
			return num1 + num2;
		}

		function callSum1(num1, num2) {
			//使用sum这个函数来完成一次调用，调用的参数就是callSum1这个函数的参数
			//apply的第二个参数表示一组参数数组
			console.log(this)
			return sum.apply(this, arguments);
		}
		
		
		function callSum2(num1, num2) {
			//关键就是第二个参数是数组
			console.log(this)
			return sum.apply(this, [ num1, num2 ]);
		}
		alert(callSum1(12, 22));
		alert(callSum2(22, 32));

		function callSum3(num1, num2) {
			//call是通过参数列表来完成传递，其他和apply没有任何区别
			return sum.call(this, num1, num2);
		}
		alert(callSum3(22, 33));
		
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/js_study/include.jsp"%>