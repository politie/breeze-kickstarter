package com.example.storm;

import backtype.storm.ILocalCluster;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

import java.util.Properties;


public class TopologyStarter {

	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("feed", new FeedSpout());
		builder.setBolt("greet", new GreeterBolt()).noneGrouping("feed");
		builder.setBolt("mark", new MarkerBolt()).noneGrouping("greet");
		builder.setBolt("register", new RegisterBolt()).noneGrouping("mark");
		StormTopology topology = builder.createTopology();

		Properties stormConf = new Properties();
		stormConf.put("greeting", "Hello");

		ILocalCluster cluster = new LocalCluster();
		cluster.submitTopology("demo", stormConf, topology);
		Utils.sleep(10 * 1000);
		cluster.shutdown();
	}

}
