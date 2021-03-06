<xmp>
function say(num) {
	/*
	 * 在函数对象中有一个属性叫做arguments,通过这个属性可以获取相应的参数值，这个属性
	 * 是一个数组，其实就是传递进来的参数
	 */
	console.log('argLength: ' + arguments.length);
	var args = []
	for(var i=0 ; i < arguments.length ; i++) {
		args.push(arguments[i])
	}
	console.log(args)
	console.log(num);
}
/**
 * 在arguments这个对象中有一个callee的方法，arguments.callee(arg)可以反向的调用
 */
	function factorial(num) {
		if(num<=1) return 1; 
		//此时和函数名耦合在一起
		// else return num*factorial(num-1);
		//以下就实现了函数名的解耦合，在js中通常都是使用这种方式做递归
		else return num*arguments.callee(num-1);
	}
	/**
	 * 以上是一个求阶乘的函数,以上递归调用的函数名称和原有函数名耦合在一起了，如果将来这个函数名称更改之后，
	 * 递归调用就会失效
	 */
	var cf = factorial;
	//此时不会报错
	console.log(cf(5));
	factorial = null;
	//此时由于cf这个函数依然使用factorial这个名称来调用，但是factorial已经指向null了，所以就会报错
	//如上情况就需要使用arguments.callee方法来调用
	console.log(cf(5));
console.log('--------say--------')
say(1,2,3);

</xmp>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/store/JavaScript/include.jsp"%>
