package com.example.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChunkReaderSpout implements IRichSpout {

	private SpoutOutputCollector collector;

	private BlockingQueue<String> queue = new LinkedBlockingQueue<>();

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("document"));
	}

	@Override
	public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
		this.collector = spoutOutputCollector;

		ClassLoader loader = getClass().getClassLoader();
		InputStream stream = loader.getResourceAsStream((String) map.get("file"));
		Scanner scanner = new Scanner(stream);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			for (int i = 0; i < 1000; i++)
				try {
					queue.put(line);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}

		scanner.close();
	}

	@Override
	public void nextTuple() {
		try {
			collector.emit(Arrays.<Object>asList(queue.take()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
