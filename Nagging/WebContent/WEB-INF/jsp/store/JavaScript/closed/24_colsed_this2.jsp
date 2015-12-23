<xmp>
	var name = "window";
	var person = {
		name:"zhangsan",
		age:23,
		say:function() {
			//that就指向person
			var that = this;
			return function() {
				return that.name;
			}
		}
	}
	/*
	 * 此时that是指向person的，所以调用that.name就是person中name
	 * 
	 */
	console.log((person.say()()));

</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>