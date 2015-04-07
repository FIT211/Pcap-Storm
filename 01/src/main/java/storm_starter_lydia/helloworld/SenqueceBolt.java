package storm_starter_lydia.helloworld;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class SenqueceBolt extends BaseBasicBolt{
    /* (non-Javadoc)
     * @see backtype.storm.topology.IBasicBolt#execute(backtype.storm.tuple.Tuple, backtype.storm.topology.BasicOutputCollector)
     */
    public void execute(Tuple input, BasicOutputCollector collector) {
        // TODO Auto-generated method stub
         String word = (String) input.getValue(0);  
         String out = "I'm " + word +  "!";  
         System.out.println("out=" + out);
    }
    
    /* (non-Javadoc)
     * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO Auto-generated method stub
    }
}