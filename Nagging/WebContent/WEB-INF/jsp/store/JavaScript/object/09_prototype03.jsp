<xmp>
	var s = '  213 '
	console.log(s)	
	console.log(s.trim())	
	String.prototype.trim = function() {
		//js的正则表达式和java很类似，区别就是js是使用//
		return this.replace(/(^\s+)|(\s+$)/g,"");
	}
	
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>