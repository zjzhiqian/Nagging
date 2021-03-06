<xmp>
	/**
	 *基于原型的创建虽然可以有效的完成封装，但是依然有一些问题
	 * 1、无法通过构造函数来设置属性值
	 * 2、当属性中有引用类型变量是，可能存在变量值重复
	 */
	function Person(){
		
	}
	Person.prototype = {
		constructor:Person,
		name:"Leon",
		age:30,
		friends:["Ada","Chris"],
		say:function() {
			alert(this.name+"["+this.friends+"]");
		}
	}
	var p1 = new Person();
	p1.name = "John";
	
	console.log(p1.friends)
	
	//会在原型中找friends,所以Mike是在原型中增加的
	p1.friends.push("Mike");//为p1增加了一个朋友
	
	console.log(p1)
	
	var p2 = new Person();
	
	console.log(p2)
	//此时原型中就多了一个Mike,这就是原型所带来的第二个问题
	console.log(p2.friends);
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>