package com.example.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import com.example.Marker;
import java.util.Arrays;
import java.util.Map;


public class MarkerBolt implements IRichBolt {

	private Marker marker;
	private OutputCollector collector;


	@Override
	public void prepare(Map stormConf, TopologyContext topologyContext, OutputCollector outputCollector) {
		collector = outputCollector;
		marker = new Marker();
	}

	@Override
	public void execute(Tuple tuple) {
		Long number = tuple.getLongByField("number");
		String heading = tuple.getStringByField("heading");
		Marker.Attributes bean = marker.mark(number);
		collector.emit(tuple, Arrays.<Object>asList(heading, bean.getIsEven(), bean.getSource()));
		collector.ack(tuple);
	}

	@Override
	public void cleanup() {
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("heading", "isEven", "source"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
