package com.example.breeze;

/**
 * @author Jethro Bakker
 */
public class Analyser {

	public String analyze(String document) {
		return document.concat(":" + Math.random());
	}
}
