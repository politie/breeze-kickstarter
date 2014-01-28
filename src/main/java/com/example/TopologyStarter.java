package com.example;

import eu.icolumbo.breeze.SpringBolt;
import eu.icolumbo.breeze.SpringSpout;
import backtype.storm.ILocalCluster;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Progmatic configuration alternative.
 * @author Jethro Bakker
 */
public class TopologyStarter {

	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();

		SpringSpout feed = new SpringSpout(Random.class, "nextLong()", "number");
		feed.setScatterOutput(true);
		builder.setSpout("feed", feed);

		SpringBolt greet = new SpringBolt(Greeter.class, "greet(number)", "heading");
		greet.setPassThroughFields("number");
		builder.setBolt("greet", greet).noneGrouping("feed");

		SpringBolt mark = new SpringBolt(Marker.class, "mark(number)", "source", "isEven");
		mark.setPassThroughFields("heading");
		builder.setBolt("mark", mark).noneGrouping("greet");

		SpringBolt register = new SpringBolt(Register.class, "write(heading, isEven, source)");
		builder.setBolt("register", register).noneGrouping("mark");

		StormTopology topology = builder.createTopology();

		Map<String, Object> config = new HashMap<>();
		ILocalCluster cluster = new LocalCluster();
		cluster.submitTopology("demo", config, topology);
		Utils.sleep(10 * 1000);
		cluster.shutdown();
	}

}
