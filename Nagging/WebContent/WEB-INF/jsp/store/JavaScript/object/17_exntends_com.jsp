<xmp>
		/**
		 * 组合的实现方式是属性通过伪造的方式实现，方法通过原型链的方式实现
		 * 注意内存模型
		 */
		function Parent(name) {
			this.color = ["red","blue"];
			this.name = name;
			if(!Parent.prototype.ps){
				Parent.prototype.ps = function() {
					alert(this.name+"["+this.color+"]");
				}
			}
		}
		
		function Child(name,age) {
			//已经完成了伪造
			Parent.call(this,name);
			this.age = age;
		}
		Child.prototype = new Parent();
		Child.prototype.say = function() {
			alert(this.name+","+this.age+"["+this.color+"]");
		}
		
		
		var c1 = new Child("Leon",22);
		
		
		c1.color.push("green");
		console.log(c1)
		
		
		var c2 = new Child("Ada",23);
		console.log(c2)
		
		
		console.log(c1.ps == c2.ps)
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>