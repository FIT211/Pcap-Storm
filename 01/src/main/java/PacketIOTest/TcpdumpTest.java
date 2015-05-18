package PacketIOTest;

import jpcap.NetworkInterface;
import jpcap.JpcapCaptor;
import PacketIO.PacketCapturer;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;


public class TcpdumpTest {
	public static void main(String[] args) {
		System.out.println(System.getProperty("java.library.path")); 
		//NetworkInterface device = "eth0";
		//JpcapCaptor.openDevice(device, 20, false, 20);
		
		
		PacketCapturer pc = new PacketCapturer();
		pc.startCapture(null,-1,null,null,null,-1);
//		pc.startCapture(null,-1,null,"D:\\PROJECTS\\EclipseWorkstation\\01\\data0.pcap",null,-1);
		
		
	//	PacketCapturer pcap = new PacketCapturer ();
     //   String defaultDevice = pcap.findDevice ();
     //   StringTokenizer st1 = new StringTokenizer (defaultDevice, "/ n");
     //   String defaultDeviceStr = st1.nextToken ();
		
		
		
		return;
         
	}
}
