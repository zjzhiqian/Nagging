package com.hzq.test.helloworld;

public class Address {
	private String city;
	private String name;
	public String getCity() {
		return city;
	}
	
	public Address() {
		super();
		System.out.println("init");
	}

	public void setCity(String city) {
		this.city = city;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
