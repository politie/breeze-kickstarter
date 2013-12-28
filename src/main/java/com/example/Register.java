package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Register {

	private static final Logger logger = LoggerFactory.getLogger(Register.class);


	public void register(String msg, String from){
		logger.info("{} says {}", from, msg);
	}

}
