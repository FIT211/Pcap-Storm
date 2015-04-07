package PcapAalysis;

import backtype.storm.tuple.Fields;
import jpcap.packet.Packet;

public class Pcap {
	
	long sec;
	long usec=0;
	int caplen=0;
	int len=0;
	byte[] header=null;
	byte[] data=null;
	
		
	public Pcap(Packet packet){
		sec = packet.sec;
		usec = packet.usec;
		caplen = packet.caplen;
		len = packet.len;
		header = packet.header;
		data = packet.data;
	}
	public Pcap(){};
	
	public Fields createFields() {
        return new Fields("sec","usec","caplen","len","header","data");
    }
}
