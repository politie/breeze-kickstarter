breeze-kickstarter
==================

Storm topologies can be defined with Java.

```java
package com.example;

import eu.icolumbo.analytics.spring.SpringBolt;
import eu.icolumbo.analytics.spring.SpringSpout;

import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;

import java.util.HashMap;
import java.util.Map;


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
		config.put("batch-file", args[0]);

		StormSubmitter.submitTopology("example", config, topology);
	}

}
```

Storm topologies can also be defined in Spring XML.

```xml
<?xml version="1.0" encoding="US-ASCII"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xmlns:breeze="http://www.icolumbo.eu/2013/breeze"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.icolumbo.eu/2013/breeze http://www.icolumbo.eu/2013/breeze.xsd">

	<breeze:topology id="demo">
		<breeze:spout beanType="com.example.breeze.ChunkReader" signature="next()" outputFields="document"/>
		<breeze:bolt beanType="com.example.breeze.AnalyticsRepo" signature="register(document, analyzed)"/>
		<breeze:bolt beanType="com.example.breeze.Analyser" signature="analyze(document)" outputFields="analyzed"/>
	</breeze:topology>

</beans>
```


Breeze will look for a matching beans definition based on the topology ID at `classpath:/demo-context.xml`. The Storm
 configuration map is available as a property source to the Spring environment.

```xml
<?xml version="1.0" encoding="US-ASCII"?>
<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:context="http://www.springframework.org/schema/context"
		xsi:schemaLocation="
			http://www.springframework.org/schema/beans
			http://www.springframework.org/schema/beans/spring-beans.xsd
			http://www.springframework.org/schema/context
			http://www.springframework.org/schema/context/spring-context.xsd
		">

	<context:property-placeholder/>

	<bean class="com.example.ChunkReader">
		<property name="feed" value="${batch-file}"/>
	</bean>

	<bean class="com.example.Analyser" scope="prototype"/>

	<bean class="com.example.AnalyticsRepo"/>

</beans>
```

Note that the Analyser bean will be instantiated for each call/tuple due to the prototype scope.


To run the demo topology on Storm build a jar with `mvn package` and execute the following command :

```shell
storm jar breeze-kickstarter-1.0-SNAPSHOT.jar com.example.breeze.TopologyStarter
```

See the [com.example.storm](http://github.com/internet-research-network/breeze-kickstarter/tree/master/src/main/java/com/example/storm) package for the Storm spout and bolt classes that don't use Spring and Breeze.
