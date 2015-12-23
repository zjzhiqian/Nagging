<xmp>
	function fn1() {
		//创建了一个数组
		var fns = new Array();
		//i这个变量是保存在fn1这个作用域中的
		//for(var i=0;i<10;i++) {
			//数组中方的值是一组函数
		//	fns[i] = function() {
		//		return i;
		//	}
		//}
		var i = 0 ;
		fns[0] = function(){
			return i 
		}
		i++
		return fns;
	}
	
	var fs = fn1();
	for(var i=0;i<fs.length;i++) {
		//此时通过闭包来调用所有函数，当输出i的时候会去上一级的作用域中查找
		//这个时候i的值已经10，所以连续输出了10个10
		console.log(fs[i]())
	}
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>