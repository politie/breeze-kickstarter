package com.example.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import eu.icolumbo.breeze.SpringBolt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;


public class RegisterBolt implements IRichBolt {

	private static final Logger logger = LoggerFactory.getLogger(SpringBolt.class);

	private OutputCollector collector;

	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.collector = outputCollector;
	}

	@Override
	public void execute(Tuple tuple) {
		logger.info("document={} analyzed={}", tuple.getValueByField("document"), tuple.getValueByField("analyzed"));
		collector.ack(tuple);
	}

	@Override
	public void cleanup() {
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
