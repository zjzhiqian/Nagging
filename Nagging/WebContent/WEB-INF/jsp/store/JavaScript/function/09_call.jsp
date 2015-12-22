<xmp>
	/**
	 * 当需要创建一个类的时候，设置类的属性和方法需要通过this关键字来引用
	 * 但是特别注意:this关键字在调用时会根据不同的调用对象变得不同
	 */
	
	var color = "red";
	function showColor() {
		console.log(this.color);
	}
	/**
	 * 创建了一个类，有一个color的属性和一个show的方法
	 */
	function Circle(color) {
		this.color = color;
	}
	
	var c = new Circle("yellow");
	
	//类似于method.invoke(object,arguments)
	showColor.call(this);//使用上下文来调用showColor,结果是red
	showColor.call(c);//上下文对象是c,结果就是yellow
	/**
	 * 通过以上发现，使用call和apply之后，对象中可以不需要定义方法了
	 * 这就是call和apply的一种运用
	 */
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>