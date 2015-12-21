<xmp>
	function Parent(name) {
		this.color = ["red","blue"];
		this.name = name;
	}
	
	function Child(name,age) {
		console.log(this)
		this.age = age;
		/*
		 * 使用伪造的方式就可以把子类的构造函数参数传递到
		 * 父类中
		 */
		Parent.call(this,name);
	}
	
	
	var c1 = new Child("Leon",12);
	var c2 = new Child("Ada",22);
	
	console.log(c1)
	console.log(c2)
	
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/js_study/include.jsp"%>