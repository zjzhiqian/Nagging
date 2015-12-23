<xmp>
	/**
	 * 在js中当进行函数的调用，会为每一个函数增加一个属性SCOPE，通过这个属性来指向一块内存
	 * 这块内存中包含有所有的上下文使用的变量，当在某个函数中调用了新函数之后，新函数依然
	 * 会有一个作用域来执行原有的函数的SCOPE和自己新增加的SCOPE，这样就形成一个链式结构
	 * 这就是js中的作用域链
	 */
	var color = "red";
	
	var showColor = function() {
		console.log(this.color);
	}
	
	function changeColor() {
		var anotherColor = "blue";
		function swapColor() {
			var tempColor = anotherColor;
			anotherColor = color;
			color = tempColor;
		}
		swapColor();
	}
	
	changeColor();
	
	showColor();
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>