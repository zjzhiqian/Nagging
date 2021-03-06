<xmp>
	/**
	 * json的意思就是javascript simple object notation
	 * json就是js的对象，但是它省去了xml中标签，而是通过{}来完成对象的说明
	 */
	var person = {
		name:"张三",//通过属性名:属性值来表示，不同的属性通过,来间隔
		age:23,
		say:function() {
			alert(this.name+","+this.age);
		}//最后一个属性之后不能有,
	}
	
	console.log(person)
	
	/**
	 * 通过json依然可以创建对象数组，创建的方式和js的数组一样
	 */
	var ps = [
		{name:"Leon",age:22},
		{name:"Ada",age:33}
	];
	var str = ''
	for(var i=0;i < ps.length;i++) {
		str += ps[i].name+" ";
	}
	console.log(str)
	/**
	 * 创建一组用户，用户的属性有
	 * name:string,age:int,friends:array
	 * List<Person> ps = new ArrayList<Person>();
	 * ps.add(new Person("Leon",22,["Ada","Alice"]));
	 * ps.add(new Person("John",33,["Ada","Chris"]));
	 * 把ps转换为json
	 */
	ps = [
		{
			name:"Leon",
			age:22,
			friends:["Ada","Alice"]
		},
		{
			name:"John",
			age:33,
			friends:["Ada","Chris"]
		}
	];
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>