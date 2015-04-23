package PcapAalysis;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
//import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class PcapBolt implements IRichBolt{
	
	private OutputCollector outputCollector;
	//private FileWriter fw= null;


	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	public void execute(Tuple tuple) {
		// TODO Auto-generated method stub
		try {
			//System.out.println("timestamp:"+tuple.getValueByField("sec")+" caplen: "+tuple.getValueByField("caplen"));
			//fw.write("timestamp:"+tuple.getValue(0)+" flow: "+tuple.getValue(1));
			System.out.println(1);
            //this.outputCollector.emit(tuple, tuple.getValues());
        } catch (Exception e) {
            
        } finally {
            outputCollector.ack(tuple);
        }
	}

	public void prepare(Map arg0, TopologyContext arg1, OutputCollector outputCollector) {
		// TODO Auto-generated method stub
		this.outputCollector = outputCollector;
        try {
            //初始化HBase数据库
        	//fw = new FileWriter("D:\\1-test.txt");
        	

        } catch (Exception e) {
           
        }
	}

	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		// TODO Auto-generated method stub
		outputFieldsDeclarer.declare(new Pcap().createFields());
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
