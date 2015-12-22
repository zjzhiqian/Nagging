<xmp>
/**
 * 为了解决原型所带来的问题，此处需要通过组合构造函数和原型来实现对象的创建
 * 将属性在构造函数中定义，将方法在原型中定义
 * 这种有效集合了两者的优点，是目前最为常用的一种方式
 */
	function Person(name,age,friends){
		//属性在构造函数中定义
		this.name = name;
		this.age = age;
		this.friends = friends;
	}
	Person.prototype = {
		constructor:Person,
		//方法在原型中定义
		say:function() {
			alert(this.name+"["+this.friends+"]");
		}
	}
	//此时所以的属性都是保存在自己的空间中的
	var p1 = new Person("Leon",23,["Ada","Chris"]);
	p1.name = "John";
	p1.friends.push("Mike");//为p1增加了一个朋友
	
	console.log(p1.friends)
	var p2 = new Person("Ada",33,["Leon"]);
	console.log(p2.friends)
	
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>