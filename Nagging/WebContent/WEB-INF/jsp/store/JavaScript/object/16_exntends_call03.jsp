<xmp>
	function Parent(name) {
		this.color = ["red","blue"];
		this.name = name;
		this.say = function() {
			alert(this.name);
		}
	}
	/**
	 * 由于使用伪造的方式，不会完成Child的原型指向Parent
	 * 所以say方法不存在，解决方法是，将这个方法放置到
	 * Parent中使用this来创建，但是此时每个对象中又存在say
	 * 这样空间占用太大，所以也不会单独的使用伪造的方式实现继承
	 */
	// Person.prototype.say = function() {
		// alert(this.name);
	// }
	
	function Child(name,age) {
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
	c1.say();
	c2.say();
	
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>