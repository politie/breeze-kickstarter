package com.example.breeze;

import eu.icolumbo.breeze.SpringBolt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsRepo {

	private static final Logger logger = LoggerFactory.getLogger(SpringBolt.class);

	public void register(String document, String analysed){
		logger.info("document={} analyzed={}", document, analysed);
	}
}
