package com.example.breeze;

import backtype.storm.ILocalCluster;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.utils.Utils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class SpringTopologyStarter {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("demo-topology.xml");
		StormTopology topology = context.getBean("demo", StormTopology.class);

		ILocalCluster cluster = new LocalCluster();
		Map<String, Object> config = new HashMap<>();
		cluster.submitTopology("demo", config, topology);
		Utils.sleep(10 * 1000);
		cluster.shutdown();
	}
}
