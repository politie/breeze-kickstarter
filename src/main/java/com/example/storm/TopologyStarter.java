package com.example.storm;

import backtype.storm.ILocalCluster;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class TopologyStarter {

	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("aspout", new ChunkReaderSpout());
		builder.setBolt("abolt", new AnalyserBolt()).noneGrouping("aspout");
		builder.setBolt("bbolt", new AnalyticsRepoBolt()).noneGrouping("abolt");

		StormTopology topology = builder.createTopology();

		Map<String, Object> config = new HashMap<>();
		config.put("file", "demo.txt");

		ILocalCluster cluster = new LocalCluster();
		cluster.submitTopology("demo", config, topology);
		Utils.sleep(10 * 1000);
		cluster.shutdown();

	}
}
