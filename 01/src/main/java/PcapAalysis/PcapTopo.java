package PcapAalysis;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;

public class PcapTopo {
	public static void main(String[] args) throws Exception{
		TopologyBuilder builder = new TopologyBuilder();
		
		
		 Config conf = new Config();
         
         if (args == null || args.length == 0) {
        	// builder.setSpout("PcapSpout", new PcapSpout(null,-1,null,"D:\\PROJECTS\\EclipseWorkstation\\01\\data0.pcap",null,-1), 1);
        			builder.setSpout("PcapSpout", new PcapSpout(null,-1,null,null,null,-1), 1);
        		//	builder.setBolt("PcapSavefileBolt", new PcapBolt(), 1).fieldsGrouping("PcapSpout",new Fields("sec","caplen"));
        		//	builder.setBolt("PcapSavefileBolt", new PcapBolt(), 1).shuffleGrouping("PcapSpout");
        	builder.setBolt("bolt2", new PcapFilterBolt(), 1).shuffleGrouping("PcapSpout");
	         conf.setNumWorkers(2);
	         LocalCluster cluster = new LocalCluster();  
	         cluster.submitTopology("PcapTopo", conf, builder.createTopology());  
	//         Utils.sleep(1000000);  
	 //        cluster.killTopology("PcapTopo");  
	//         cluster.shutdown();  
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
        	 builder.setSpout("PcapSpout", new PcapSpout(null,-1,null,null,null,-1), 1);
        	// builder.setSpout("PcapSpout", new PcapSpout(null,-1,null,"D:\\PROJECTS\\EclipseWorkstation\\01\\data0.pcap",null,-1), 1);
        	 builder.setBolt("bolt2", new PcapFilterBolt(), 2).shuffleGrouping("PcapSpout");
        	 conf.setNumWorkers(4);
        	 
        	 StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
        	 
         }
	}
}
