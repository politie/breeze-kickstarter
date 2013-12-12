package com.example.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Jethro Bakker
 */
public class AnalyserBolt implements IRichBolt {

	private OutputCollector collector;

	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.collector = outputCollector;
	}

	@Override
	public void execute(Tuple tuple) {
		String value = (String) tuple.getValueByField("document");
		collector.emit(tuple, Arrays.<Object>asList(value, value + ":" + Math.random()));
		collector.ack(tuple);
	}

	@Override
	public void cleanup() {
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("document", "analyzed"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
