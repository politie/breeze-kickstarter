package com.example.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import com.example.Register;

import java.util.Map;


public class RegisterBolt implements IRichBolt {

	private Register register;
	private OutputCollector collector;


	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		collector = outputCollector;
		register = new Register();
	}

	@Override
	public void execute(Tuple tuple) {
		String heading = tuple.getStringByField("heading");
		boolean isEven = tuple.getBooleanByField("isEven");
		String source = tuple.getStringByField("source");
		register.write(heading, isEven, source);
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
