<xmp>
		function Parent() {
			this.color = ["red","blue"];
			this.name = "Leon";
			if(!Parent.prototype.say){
				Parent.prototype.say = function (){
				  console.log(1)
				}
			}
		}
		
		function Child() {
			//在Child中的this明显应该是指向Child的对象
			//当调用Parent方法的时候，而且this又是指向了Child
			//此时就等于在这里完成this.color = ["red","blue"]
			//也就等于在Child中有了this.color属性，这样也就变相的完成了继承
			Parent.call(this);
			//这种调用方式，就仅仅完成了函数的调用，根本无法实现继承
			//Parent();
		}
		
		var c3 = new Parent()
		console.log(c3.say)
		
		
		var c1 = new Child();
		c1.color.push("green");
		console.log(c1)
		console.log(c1.say) //无法实现方法的继承
		var c2 = new Child();
		console.log(c2)
		
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/js_study/include.jsp"%>