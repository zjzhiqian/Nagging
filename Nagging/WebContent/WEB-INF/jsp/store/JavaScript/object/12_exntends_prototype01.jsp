<xmp>
		/**
		 * js实现继承的第一种方式是基于原型链的方式
		 */
		function Parent() {
			this.pv = "parent";
		}
		Parent.prototype.pp = "ok";
		Parent.prototype.showParentValue = function() {
			console.log(this.pv);
		}
		
		function Child() {
			this.cv = "child";
		}
		/**
		 * 如果想进行赋值之后，才进行原型链的设定，这样赋值的原型对象
		 * 就会被重写掉，赋值的对象就不存在在新的原型对象中
		 */
		//	Child.prototype.showChildValue = function() {
		//		console.log(this.cv);
		//	}
		
		/**
		 * 让Child的原型链指向Parent对象，也就等于完成了一次继承
		 * 注意内存模型
		 */
		Child.prototype = new Parent();
		Child.prototype.showChildValue = function() {
			 console.log(this.cv);
		}
		/**
		 * 此时完成的对父类对象的覆盖
		 */
		Child.prototype.showParentValue = function() {
			console.log("override parent");
		}
		/**
		 * 在使用原型链进行继承一定要注意一下问题：
		 * 1、不能在设定了原型链之后，再重新为原型链赋值
		 * 2、一定要在原型链赋值之后才能添加或者覆盖方法
		 */
		
		
		/**
		 * 当执行了下面这句话之后，意味着Child的原型又重写了
		 * 这样就不存在任何的继承关系了
		 * 这是使用原型链需要注意的第一个问题
		 */
		// Child.prototype = {
			// showChildValue:function() {
				// console.log(this.v);
			// }
		// }
		
		var c = new Child();
		c.showParentValue();
		c.showChildValue();
		console.log(c)
	
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>