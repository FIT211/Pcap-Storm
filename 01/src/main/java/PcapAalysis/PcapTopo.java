package PcapAalysis;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

public class PcapTopo {
	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
	//	builder.setSpout("PcapSpout", new PcapSpout(null,-1,null,"D:\\PROJECTS\\EclipseWorkstation\\01\\data0.pcap",null,-1), 1);
		builder.setSpout("PcapSpout", new PcapSpout(null,-1,null,null,null,-1), 1);
		builder.setBolt("PcapSavefileBolt", new PcapBolt(), 1).shuffleGrouping("PcapSpout");
		
		 Config conf = new Config();
         conf.setNumWorkers(2);
         
         LocalCluster cluster = new LocalCluster();  
         cluster.submitTopology("PcapTopo", conf, builder.createTopology());  
         Utils.sleep(10000);  
         cluster.killTopology("PcapTopo");  
         cluster.shutdown();  
         
	}
}
