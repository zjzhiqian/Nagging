package com.hzq.test.helloworld;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;


public class Helloworld {
	private String name;
	private String age;

	public void setName(String name) {
		this.name = name;
	}

	public void hello(){
		System.out.println("hello");
	}
	
	public Helloworld(){
		super();
		
	}
	
	private void init() {
		System.out.println("init");

	}
	
	private void destroy() {
		System.out.println("destrry");
	}
}
