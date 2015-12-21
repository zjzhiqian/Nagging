<xmp>
	/**
	 * 原型是js中非常特殊一个对象，当一个函数创建之后，会随之就产生一个原型对象
	 * 当通过这个函数的构造函数创建了一个具体的对象之后，在这个具体的对象中
	 * 就会有一个属性指向原型
	 */
	//第一种状态
	function Person(){
		
	}
	/**
	 * 以下方式将会重写原型
	 * 由于原型重写，而且没有通过Person.prototype来指定
	 * 此时的constructor不会再指向Person而是指向Object
	 * 如果constructor真的比较重要，可以在json中说明原型的指向
	 */
	Person.prototype = {
		constructor:Person,//手动指定constructor
		name:"Leon",
		age:23,
		say:function() {
			alert(this.name+","+this.age);
		}
	}
	var p1 = new Person();
	console.log(p1)
// 	p1.say();
// 	alert(p1.constructor==Person);
	
// 	alert(p1.name)
<xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/js_study/include.jsp"%>