<xmp>
	var name = "window";
	var person = {
		name:"zhangsan",
		age:23,
		say:function() {
			return function() {
				return this.name;
			}
		}
	}
	/*
	 * 当完成person.say()之后，这个函数就调用结束了，在这个函数调用结束之前
	 * this是指向person,但是在调用匿名函数的时候，this就指向window，所以
	 * 得到的结果是window
	 * 
	 */
	console.log((person.say()()));
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>