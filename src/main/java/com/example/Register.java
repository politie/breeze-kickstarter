package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Register {

	private static final Logger logger = LoggerFactory.getLogger(Register.class);


	public void write(String header, boolean isEven, String judge){
		StringBuilder buffer = new StringBuilder(header);
		buffer.append(" You are ").append(isEven ? "even." : "odd.");
		buffer.append(" Cheers from ").append(judge);
		logger.info(buffer.toString());
	}

}
