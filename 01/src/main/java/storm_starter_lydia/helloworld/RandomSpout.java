package storm_starter_lydia.helloworld;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.Random;
import java.util.Map;

public class RandomSpout extends BaseRichSpout{
    private SpoutOutputCollector collector;
    private static String[] words = {"happy","excited","angry"};
    
    /* (non-Javadoc)
     * @see backtype.storm.spout.ISpout#open(java.util.Map, backtype.storm.task.TopologyContext, backtype.storm.spout.SpoutOutputCollector)
     */
    public void open(Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
        // TODO Auto-generated method stub
        this.collector = arg2;
    }
    
    /* (non-Javadoc)
     * @see backtype.storm.spout.ISpout#nextTuple()
     */
    public void nextTuple() {
        // TODO Auto-generated method stub
        String word = words[new Random().nextInt(words.length)]; 
        collector.emit(new Values(word));
    }
    
    /* (non-Javadoc)
     * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
     */
    public void declareOutputFields(OutputFieldsDeclarer arg0) {
        // TODO Auto-generated method stub
        arg0.declare(new Fields("randomstring"));
    }
}