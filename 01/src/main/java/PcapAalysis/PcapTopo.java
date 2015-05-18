package PcapAalysis;

import java.util.Iterator;
import java.util.List;
import backtype.storm.generated.TopologySummary;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;

public class PcapTopo {
	public static void main(String[] args) throws Exception{
		System.out.println("HeLLOFD-5-7");
		TopologyBuilder builder = new TopologyBuilder();
		//System.out.println("After TopoBuilder");
		//System.out.println("Before conf");
		 Config conf = new Config();
        	System.out.println("After conf"); 
         if (args == null || args.length == 0) {
        	 
        	// builder.setSpout("PcapSpout", new PcapSpout(null,-1,null,"D:\\PROJECTS\\EclipseWorkstation\\01\\data0.pcap",null,-1), 1);
//        	 builder.setSpout("NewSpout", new PcapSpout(null,-1,null,"/home/computer200/JavaProject/Pcap-Storm/01/data0.pcap",null,-1), 1);	
        	 	builder.setSpout("Spout-5-7", new PcapSpout(null,-1,null,null,null,-1), 1);
        		//	builder.setBolt("PcapSavefileBolt", new PcapBolt(), 1).fieldsGrouping("PcapSpout",new Fields("sec","caplen"));
        		//	builder.setBolt("PcapSavefileBolt", new PcapBolt(), 1).shuffleGrouping("PcapSpout");
        	builder.setBolt("bolt-5-7", new PcapFilterBolt(), 1).shuffleGrouping("Spout-5-7");
	         conf.setNumWorkers(2);
	         LocalCluster cluster = new LocalCluster();
 
		List<TopologySummary> topologyList =  cluster.getClusterInfo().get_topologies();
		System.out.println("There are "+ topologyList.size()+" topos");
	         for(int i=0; i<topologyList.size();i++){  
			 System.out.println("The "+i+"th topo is "+topologyList.get(i).get_name());
	        	 cluster.killTopology(topologyList.get(i).get_name()); 
		}
 		System.out.println("Killed all topos in local cluster");

	         cluster.submitTopology("PcapTopo-5-7", conf, builder.createTopology());  
	         Utils.sleep(10000);  
	         cluster.killTopology("PcapTopo-5-7");  
	         cluster.shutdown();  
         }else{
        	 
        	 System.out.println(args[0]);
        	 /*System.out.println(args[1]);
        	 System.out.println(args[2]);
        	 System.out.println(args[3]);
        	 System.out.println(args[4]);
        	 System.out.println(args[5]);
        	 System.out.println(args[6]);
        	 builder.setSpout("PcapSpout", new PcapSpout(args[1],args[2],args[3],args[4],args[5],args[6]), 2);
        	 */
        	 builder.setSpout("NewSpout511", new PcapSpout(null,-1,null,null,null,-1), 1);
        	// builder.setSpout("PcapSpout", new PcapSpout(null,-1,null,"D:\\PROJECTS\\EclipseWorkstation\\01\\data0.pcap",null,-1), 1);
        	 builder.setBolt("bolt511", new PcapFilterBolt(), 2).shuffleGrouping("NewSpout511");
        	 conf.setNumWorkers(4);
        	 
        	 StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
        	 
         }
	}
}
