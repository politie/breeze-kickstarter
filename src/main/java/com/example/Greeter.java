package com.example;


public class Greeter {

	private String greeting;


	public String greet(Object o) {
		StringBuilder buffer = new StringBuilder(greeting);
		buffer.append(' ').append(o).append('!');
		return buffer.toString();
	}

	public void setGreeting(String value) {
		greeting = value;
	}

}
