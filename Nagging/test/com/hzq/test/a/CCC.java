package com.hzq.test.a;

import java.math.BigDecimal;

public class CCC {
	public static void main(String[] args) {
		System.out.println(Math.pow(2d, 2d)*(1+0.25));
		sas d= new sas();
		d.getA();
		System.out.println(d.getA());
		String k ="11000001000100000000000000000000";
		System.out.println(k.length());
	}
}
class  sas{
	
	private BigDecimal a;
	
	public BigDecimal getA() {
		return a;
	}

	public void setA(BigDecimal a) {
		this.a = a;
	}
	
}