package com.example.breeze;

import backtype.storm.ILocalCluster;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import eu.icolumbo.breeze.SpringBolt;
import eu.icolumbo.breeze.SpringSpout;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jethro Bakker
 */
public class TopologyStarter {

	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();

		SpringSpout spout = new SpringSpout(ChunkReader.class, "next()", "document");
		spout.setScatterOutput(true);
		builder.setSpout("chunk-reader", spout);

		SpringBolt bolt1 = new SpringBolt(Analyser.class, "analyze(document)", "analysis");
		bolt1.setPassThroughFields("document");
		builder.setBolt("analyser", bolt1).noneGrouping("chunk-reader");

		SpringBolt bolt2 = new SpringBolt(AnalyticsRepo.class, "register(document, analysis)");
		builder.setBolt("analytics-repo", bolt2).noneGrouping("analyser");

		StormTopology topology = builder.createTopology();

		Map<String, Object> config = new HashMap<>();
		ILocalCluster cluster = new LocalCluster();
		cluster.submitTopology("demo", config, topology);
		Utils.sleep(10 * 1000);
		cluster.shutdown();
	}

}
