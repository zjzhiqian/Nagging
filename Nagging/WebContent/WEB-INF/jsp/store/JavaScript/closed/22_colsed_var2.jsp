<xmp>
	function fn1() {
		//创建了一个数组
		var fns = new Array();
		//i这个变量是保存在fn1这个作用域中的
		for(var i=0;i<10;i++) {
			//num这个变量是保存在fns这个tf这个作用域，每一个闭包的num都是不一样
			//所以此时所消耗的内存特别的大
			var tf = function(num) {
				fns[num] = function() {
					return num;
				}
			}
		}
		return fns;
	}
	
	var fs = fn1();
	for(var i=0;i<fs.length;i++) {
		//每一个fs都是在不同作用域链中，num也是保存在不同的作用域中，所以输出0-9
		console.log(fs[i]())
	}
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>