<xmp>
/**
 * 为了让定义的方式更加符合对象的封装性，就把定义方法的原型代码放置到Person这个构造函数中
 */
	function Person(name,age,friends){
		//属性在构造函数中定义
		this.name = name;
		this.age = age;
		this.friends = friends;
		/**
		 * 判断Person.prototype.say是否存在，如果不存在就表示需要创建
		 * 当存在之后就不会在创建了
		 */
		if(!Person.prototype.say) {
			Person.prototype.say = function() {
				alert(this.name+"["+this.friends+"]");
			}	
		}
	}
	
	//此时所以的属性都是保存在自己的空间中的
	var p1 = new Person("Leon",23,["Ada","Chris"]);
	p1.name = "John";
	p1.friends.push("Mike");//为p1增加了一个朋友
	console.log(p1.friends)
	var p2 = new Person("Ada",33,["Leon"]);
	console.log(p2.friends)
	
	console.log(p1.say == p2.say)
	
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>