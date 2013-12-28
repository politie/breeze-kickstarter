package com.example.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;


public class FeedSpout implements IRichSpout {

	private Random feed;
	private SpoutOutputCollector collector;


	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("number"));
	}

	@Override
	public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
		collector = spoutOutputCollector;
		feed = new Random();
	}

	@Override
	public void nextTuple() {
		long next = feed.nextLong();
		collector.emit(Arrays.<Object>asList(next));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

	@Override
	public void close() {
	}

	@Override
	public void activate() {
	}

	@Override
	public void deactivate() {
	}

	@Override
	public void ack(Object o) {
	}

	@Override
	public void fail(Object o) {
	}

}
