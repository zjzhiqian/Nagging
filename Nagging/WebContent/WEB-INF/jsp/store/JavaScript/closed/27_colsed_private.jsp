<xmp>
	function Person(name) {
		/**
		 * 此时就没有办法直接访问name这个属性，因为没有this.name
		 * 要访问name只能通过this.getName,this.setName
		 * 但是使用这种方式创建私有变量带来的问题是，每个对象都存储大量的函数
		 * 解决的办法是通过静态私有变量来解决
		 */
		this.setName = function(value) {
			name = value;
		}
		this.getName = function() {
			return name;
		}
	}
	
	var p = new Person("aa");
	console.log(p.getName());
	p.setName("bb");
	console.log(p.getName());
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>