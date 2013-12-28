package com.example.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import com.example.Greeter;
import java.util.Arrays;
import java.util.Map;


public class GreeterBolt implements IRichBolt {

	private Greeter greeter;
	private OutputCollector collector;


	@Override
	public void prepare(Map stormConf, TopologyContext topologyContext, OutputCollector outputCollector) {
		collector = outputCollector;
		greeter = new Greeter();
		greeter.setGreeting((String) stormConf.get("greeting"));
	}

	@Override
	public void execute(Tuple tuple) {
		Long number = tuple.getLongByField("number");
		String heading = greeter.greet(number);
		collector.emit(tuple, Arrays.<Object>asList(number, heading));
		collector.ack(tuple);
	}

	@Override
	public void cleanup() {
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("number", "heading"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
