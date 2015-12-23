<xmp>
	var Person;
	(function(){
		//name正在函数结束之后就消失，在外面是无法引用的
		var name = "";
		Person = function(value){
			name = value;
		}
		Person.prototype.setName = function(value) {
			name = value;
		}
		Person.prototype.getName = function() {
			return name;
		}
	})();
	
	var p1 = new Person("aa");
	console.log(p1.getName());
	p1.setName("bb");
	console.log(p1.getName());
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>