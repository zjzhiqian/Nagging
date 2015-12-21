<xmp>
	/**
	 * 通过工厂的方式来创建Person对象
	 * 在createPerson中创建一个对象
	 * 然后为这个对象设置相应的属性和方法
	 * 之后返回这个对象
	 */
	function createPerson(name,age) {
		var o = new Object();
		o.name = name;
		o.age = age;
		o.say = function() {
			alert(this.name+","+this.age);
		}
		return o;
	}
	/**
	 * 使用工厂的方式，虽然有效的解决了类的问题，但是依然存在另外一个问题
	 * 我们无法检测对象p1和p2的数据类型
	 */
	var p1 = createPerson("Leon",22);
	var p2 = createPerson("Ada",33);
	p1.say();
	p2.say();
	
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/js_study/include.jsp"%>