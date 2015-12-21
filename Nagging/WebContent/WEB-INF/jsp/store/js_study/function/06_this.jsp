<xmp>
	/**
	 * 当需要创建一个类的时候，设置类的属性和方法需要通过this关键字来引用
	 * 但是特别注意:this关键字在调用时会根据不同的调用对象变得不同
	 */
	
	var color = "red";
	function showColor() {
		alert(this.color);
	}
	/**
	 * 创建了一个类，有一个color的属性和一个show的方法
	 */
	function Circle(color) {
		this.color = color;
		this.showColor = showColor;
	}
	
	var c = new Circle("yellow");
	//使用c来调用showColor方法，等于调用了showColor()方法
	//此时的this是c，所以color就是yellow
	c.showColor();//yellow
	//此时调用的对象等于是window,showColor的this就是window,所以就会找window中color
	showColor();//red
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/js_study/include.jsp"%>