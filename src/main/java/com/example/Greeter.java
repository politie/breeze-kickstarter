package com.example;


public class Greeter {

	private String greeting;


	public String greet(Object o) {
		return greeting + " " + String.valueOf(o);
	}

	public void setGreeting(String value) {
		greeting = value;
	}

}
