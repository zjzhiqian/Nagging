<xmp>

//利用返回函数对象,根据属性排序数组
function Person(name,age) {
	this.name = name;
	this.age = age;
}
var p1 = new Person("Leno",39);
var p2 = new Person("John",23);
var p3 = new Person("Ada",41);
var ps = [p1,p2,p3];

function sortByProperty(propertyName) {
	var sortFun = function(obj1,obj2) {
		if(obj1[propertyName]>obj2[propertyName]) return 1;
		else if(obj1[propertyName]==obj2[propertyName])return 0;
		else return -1;
	}
	return sortFun;
}

ps.sort(sortByProperty("age"))
console.log(ps)

</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>