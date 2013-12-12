package com.example.breeze;

public class Analyser {

	public String analyze(String document) {
		return document.concat(":" + Math.random());
	}
}
