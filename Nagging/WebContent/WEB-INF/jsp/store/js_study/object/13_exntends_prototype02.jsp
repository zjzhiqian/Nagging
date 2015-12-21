<xmp>
		/**
		 * js实现继承的第一种方式是基于原型链的方式
		 */
		function Parent() {
			this.pv = "parent";
			this.color = ["red","yellow"];
		}
		Parent.prototype.pp = "ok";
		Parent.prototype.showParentValue = function() {
			alert(this.pv);
		}
		
		function Child() {
			
			this.cv = "child";
		}
		/**
		 * 使用原型链继承，最大的缺点是，无法从子类中调用父类的构造函数
		 * 这样就没有办法把子类中的属性赋值到父类
		 * 第二个缺点就是，如果父类中有引用类型，此时这个引用类会添加到
		 * 子类的原型中，当第一个对象的修改了这个引用之后，其他对象的引用同时修改
		 * 所以一般都不会单纯的使用原型链来实现继承
		 */
		
		Child.prototype = new Parent();
		
		Child.prototype.showChildValue = function() {
			 alert(this.cv);
		}
		/**
		 * 此时完成的对父类对象的覆盖
		 */
		Child.prototype.showParentValue = function() {
			alert("override parent");
		}
		
		var c1 = new Child();
		//Child中的原型的color被修改
		c1.color.push("blue");
		alert(c1.color);//red,yellow blue
		var c2 = new Child();
		alert(c2.color);//red yellow blue
	
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/js_study/include.jsp"%>