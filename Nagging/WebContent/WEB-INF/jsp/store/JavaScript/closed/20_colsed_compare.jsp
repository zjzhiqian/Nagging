<xmp>
	/**
	 * 通过以下操作带来最大的好处是，compareObjectFunction的作用域变大了
	 * 当compareObjectFunction结束之后，prop这个变量依然存在
	 */
	function compareObjectFunction(prop) {
		//匿名函数
		return function(obj1,obj2) {
			if(obj1[prop]>obj2[prop]) return 1;
			else if(obj1[prop] < obj2[prop]) return -1;
			else return 0;
		}
	}
	var o1 = {name:"Leon",age:23};
	var o2 = {name:"Ada",age:28};
	//此时就是基于name来进行比较
	/*
	 * 在java或者c++中，以下代码执行完成之后，需要进行内存的释放
	 * 此时对于java和c++这些静态语言而言，prop会被释放
	 * 但是在js中，这个作用域却被放大了
	 */
	var compare = compareObjectFunction("age");
	//此时就比较了o1和o2
	/*
	 * 在js中，prop在这里依然可以被访问，这种通过返回函数来扩大函数的作用域的方法
	 * 就是闭包
	 */
	console.log(compare(o1,o2));
</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>