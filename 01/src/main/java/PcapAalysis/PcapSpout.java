package PcapAalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

import PacketIO.PacketCapturer;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.Packet;
import jpcap.JpcapCaptor;
import jpcap.PacketReceiver;

public class PcapSpout implements IRichSpout {

	private SpoutOutputCollector outputCollector;
	private JpcapCaptor captor;
	private NetworkInterface device;
	
	//start capture
	private String deviceName = null;
	private int count = -1;
	private String filter = null;
	private String srcFilename =null ;
	private String dstFilename = null;
	private int sampLen = 65535;
	
    public PcapSpout(){};
   
    public PcapSpout(String deviceName, String count, String filter, String srcFilename, String dstFilename, String sampLen){
    	this.deviceName = deviceName;
    	this.count = Integer.parseInt(count); //未使用，无效
    	this.filter = filter;
    	this.srcFilename = srcFilename;
    	this.dstFilename = dstFilename;
    	this.sampLen = Integer.parseInt(sampLen);
    }
    public PcapSpout(String deviceName, int count, String filter, String srcFilename, String dstFilename, int sampLen){
    	this.deviceName = deviceName;
    	this.count = count; //未使用，无效
    	this.filter = filter;
    	this.srcFilename = srcFilename;
    	this.dstFilename = dstFilename;
    	this.sampLen = sampLen;
    }
    
    /**
	 * 
	 * @return
	 */
	static NetworkInterface[] getNetworkInterfaces()
	{
		//Obtain the list of network interfaces
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		
		//for each network interface
		for (int i = 0; i < devices.length; i++) {  
			System.out.println(i+": "+devices[i].name + "(" + devices[i].description+")");   
			System.out.println(" datalink: "+devices[i].datalink_name + "(" + devices[i].datalink_description+")");  
			System.out.print(" MAC address:");  
			
			for (byte b : devices[i].mac_address)    
				System.out.print(Integer.toHexString(b&0xff) + ":");  
			System.out.println();  
			
			//print out its IP address, subnet mask and broadcast address  
			for (NetworkInterfaceAddress a : devices[i].addresses)    
				System.out.println(" address:"+a.address + " " + a.subnet + " "+ a.broadcast);
		}		
		return devices;
	}
    
    NetworkInterface getDevice(String deviceName){
		NetworkInterface[] devices = getNetworkInterfaces();
		
		if(deviceName==null) return devices[0];
		
		for(int i=0;i<devices.length; i++){
			if(devices[i].name.equals(deviceName))
				return devices[i];
		}
		return null;
	}
    
    
    public class PacketPrinter implements PacketReceiver {
    	
    	public void receivePacket(Packet packet) {
    		//this.outputCollector.emit(createValues(packet));
    	}
    }
    
	public void ack(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	public void activate() {
		// TODO Auto-generated method stub
		
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	public void fail(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	public void nextTuple() {
		// TODO Auto-generated method stub
		Utils.sleep(10000);
		try {
            //该方法从指定的目录中中读取符合条件的文件列表，并随机从中选择一个将其独占并返回文件名
			/*
			if( in != null){
				String line;
				int count=0;
				while((line = in.readLine()) != null){
					if(line.trim().length()>0){
						count++;
						this.outputCollector.emit(createValues(line));
					}
				}
				System.out.println("There are "+count+" rows!");
			}
			else{
				System.out.println("文件打开失败 in next tuple");
			}
			
			*/
			if(sampLen<0) this.sampLen = 65535;
			
			
				while(true){
				  Packet packet=captor.getPacket();  
				  //if some error occurred or EOF has reached, break the loop  
			//	  if(packet==null || packet==Packet.EOF) break; 
				  if(packet==null)break;
				  //otherwise, print out the packet  
				    System.out.print("packet cap sec->"+packet.sec+"->");
					System.out.print("packet cap usec->"+packet.usec+"->");
					System.out.print("packet cap caplen->"+packet.caplen+"->");
					System.out.print("packet cap length->"+packet.caplen+"->");
					System.out.println("packet header length->"+packet.header.length);
					System.out.println();
				 // System.out.println(packet);
					this.outputCollector.emit(createValues(packet));
				}
				
			
        } catch (Exception e) {
            
        }
	}

	public void open(Map arg0, TopologyContext arg1, SpoutOutputCollector spoutOutputCollector) {
		// TODO Auto-generated method stub
		this.outputCollector = spoutOutputCollector;
        try {            
            //读取HDFS文件的客户端，自己实现
        	
        	/*
        	in = new BufferedReader(new FileReader("D:\\1.txt"));
        	if(in == null){
        		System.out.println("文件打开失败");
        		System.exit(-1);
        	}*/
        	//pc = new PacketCapturer();
        //	captor = new JpcapCaptor();
        	if(srcFilename!=null){
				captor = JpcapCaptor.openFile(srcFilename);
        	}
        	else
        	{
        		device = getDevice(deviceName);
				captor = JpcapCaptor.openDevice(device, this.sampLen, false, 20);
				if(filter!= null)
					captor.setFilter(filter, true);
				//captor.loopPacket(count, new PacketPrinter());
        	}
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		// TODO Auto-generated method stub
		//outputFieldsDeclarer.declare(new Pcap().createFields());
		outputFieldsDeclarer.declare(new Pcap().createFields());
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Values createValues(Packet packet) {
		long sec = packet.sec;
		long usec = packet.usec;
		int caplen = packet.caplen;
		int len = packet.len;
		byte[] header = packet.header;
		byte[] data = packet.data;
        return new Values(
                sec,
                usec,
                caplen,
                len,
                header,
                data
        );
    }

}
