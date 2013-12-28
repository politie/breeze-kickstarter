[Breeze](https://github.com/internet-research-network/breeze) Kickstarter
-------------------------------------------------------------------------

The Maven Exec plugin runs the demo in local mode.

```shell
$ mvn package exec:java
```

The storm command deploys the demo on a cluser.

```shell
$ storm jar breeze-kickstarter-1.0-SNAPSHOT.jar eu.icolumbo.breeze.namespace.TopologyStarter demo
```

Alternatively you can use a [progmatic configuration](https://github.com/internet-research-network/breeze-kickstarter/blob/master/src/main/java/com/example/TopologyStarter.java) instead of the [Breeze namespace](https://github.com/internet-research-network/breeze-kickstarter/blob/master/src/main/resources/applicationContext.xml) with the default starter.

```shell
$ storm jar breeze-kickstarter-1.0-SNAPSHOT.jar com.example.TopologyStarter
```


The [com.example.storm](http://github.com/internet-research-network/breeze-kickstarter/tree/master/src/main/java/com/example/storm) package demonstrates an equivalent without Breeze.


How It Works
============

The topology starters use Spring enabled [spout](https://github.com/internet-research-network/breeze/blob/master/src/main/java/eu/icolumbo/breeze/SpringSpout.java) and [bolt](https://github.com/internet-research-network/breeze/blob/master/src/main/java/eu/icolumbo/breeze/SpringBolt.java) Storm components to use the [topology appliction context](https://github.com/internet-research-network/breeze-kickstarter/blob/master/src/main/resources/demo-context.xml).

Note that the Marker bean will be instantiated for each call due to the prototype scope.
