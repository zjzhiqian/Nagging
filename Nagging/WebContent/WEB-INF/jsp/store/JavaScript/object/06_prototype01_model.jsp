<xmp>
	/**
	 * 原型是js中非常特殊一个对象，当一个函数创建之后，会随之就产生一个原型对象
	 * 当通过这个函数的构造函数创建了一个具体的对象之后，在这个具体的对象中
	 * 就会有一个属性指向原型
	 */
	//第一种状态
	function Person(){
		
	}
	//第二种状态
	Person.prototype.name = "Leon";
	Person.prototype.age = 23;
	Person.prototype.say = function() {
		alert(this.name+","+this.age);
	}
	//第三中状态，创建了一个对象之后会有一个_prop_的属性指向原型
	//在使用时如果在对象内部没有找到属性会去原型中找，_prop_属性是隐藏的
	var p1 = new Person();
	
	
// 	p1.say();
	//以下方法可以检测出p1是否有_prop_指向Person的原型
	//alert(Person.prototype.isPrototypeOf(p1));
	console.log(p1)
	//第四种状态
	var p2 = new Person();
	//是在自己的空间中定义了一个属性，不会替换原型中的属性
	p2.name = "Ada";
	console.log(p2)
	
	console.log(p1.hasOwnProperty('name'))
	console.log(p2.hasOwnProperty('name'))
	delete p2.name
	console.log(p2.hasOwnProperty('name'))
	
	console.log('name' in p1)
	console.log('name' in p2)
	
	/**
	 * 可以通过如下方法检测某个属性是否在原型中存在
	 */
	function hasPrototypeProperty(obj,attr){
		if(attr in obj && (!obj.hasOwnProperty(attr))){
			return true
		}
		return false;
	}
	console.log(hasPrototypeProperty(p1,'name'))
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>