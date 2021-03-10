package Jcapture;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.JpcapWriter;
import java.io.IOException;
import jpcap.packet.TCPPacket;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Netcaptor {
    private NetworkInterface[] devices = null;
    private JpcapCaptor[] captors = null;
    private PacketReceiver[] receivers = null;
    private int netInterfaceNum = 0;
    public Netcaptor(boolean verbose){
        getdevices(verbose);
        getJpcapCaptor(verbose);
        getWriter("capture",0);
        getReceiver(verbose);
        try {
            for(JpcapCaptor captor:captors) {
                captor.setFilter("ip and tcp", true);
            }
        }catch(IOException e){
            System.out.println("IOException while setting filter");
            e.printStackTrace();
        }
    }
    public void startCapture(int times,int netInterfacenumber) {
        for (int i = 0; i < times; i++) {
            captors[netInterfacenumber].processPacket(netInterfacenumber, receivers[netInterfacenumber]);
        }
    }
    private NetworkInterface[] getdevices(boolean verbose){
        if(verbose) {
            System.out.println("Getting devices ......");
        }
        devices = JpcapCaptor.getDeviceList();
        int devices_num = devices.length;
        netInterfaceNum = devices_num;
        if(verbose) {
            System.out.println("Finish getting devices,get " + devices_num + " devices.");
            System.out.println("Device Info:");
            for (int i = 0; i < devices_num; i++) {
                System.out.println("    " + i + ": " + devices[i].name + ", " + devices[i].description);
                System.out.println("       " + devices[i].datalink_name + ", " + devices[i].datalink_description);
                System.out.print("       Mac address: ");
                for (byte b : devices[i].mac_address) {
                    System.out.print(Integer.toHexString(b & 0xff) + ":");
                }
                System.out.println("");
            }
        }
        if(devices == null){
            System.out.println("No devices found. Check configuration.");
        }
        return devices;
    }
    private JpcapCaptor[] getJpcapCaptor(boolean verbose){
        if(verbose){
            System.out.println("Creating jpcap Captor ......");
        }
        JpcapCaptor jpcap = null;
        if(devices != null) {
            try {
                captors = new JpcapCaptor[netInterfaceNum];
                for(int i = 0;i < netInterfaceNum;i++) {
                    captors[i] = JpcapCaptor.openDevice(devices[i], 65535, false, 50);
                }
            } catch (IOException e) {
                System.out.println("Exception from capture.");
                e.printStackTrace();
            }
            if(verbose){
                System.out.println("Finish creating jpcap.");
            }
        }
        else{
            System.out.println("No devices.Failed to create jpcapcaptor");
            return null;
        }
        return captors;
    }
    private JpcapWriter getWriter(String filename,int capnum){
        if(captors != null) {
            JpcapWriter writer = null;
            try {
                writer = JpcapWriter.openDumpFile(captors[capnum], filename);
            }catch (IOException e){
                e.printStackTrace();
            }
            return writer;
        }else{
            System.out.println("No captor found! Create captor first.");
            return null;
        }
    }
    private boolean httpchecker(TCPPacket packet,boolean in,boolean out){
        if((in && packet.src_port == 443)||(out && packet.dst_port == 443)) {
            System.out.println("Header:");
            ByteView.view(packet.header,20);
            System.out.println("");
            System.out.println("Source:"+ packet.src_ip+":"+packet.src_port);
            System.out.println("Dest  :"+ packet.dst_ip+":"+packet.dst_port);
            System.out.println("Data  :");
            ByteView.view(packet.data,20);
            System.out.println("");
            System.out.println("==========================================================================");
            return true;
        }else{
            return false;
        }
    }
    private PacketReceiver[] getReceiver(boolean verbose) {
        receivers = new PacketReceiver[netInterfaceNum];
        for(int i = 0;i < netInterfaceNum;i++) {
            int finalI = i;
            receivers[i] = new PacketReceiver() {
                public void receivePacket(Packet packet) {
                    TCPPacket tcppkt = null;
                    if (packet.getClass().equals(TCPPacket.class)) {
                        if(verbose) {
                            System.out.println(packet.toString());
                        }
                        tcppkt = (TCPPacket) packet;
                        boolean cond = true;
                        cond = httpchecker(tcppkt,true,false);
                        if(verbose){
                            System.out.println("Receiving package");
                        }
                        if (cond) {
                            SimpleDateFormat sdf = new SimpleDateFormat();
                            sdf.applyPattern("MM-dd-HH-mm-ss");
                            Date date = new Date();// 获取当前时间
                            String timenow = sdf.format(date);
                            JpcapWriter writer = getWriter(timenow,finalI);
                            if (writer != null) {
                                writer.writePacket(packet);
                                if(verbose){
                                    System.out.println("Successfully write package");
                                }
                            } else {
                                if(verbose){
                                    System.out.println("Writer not available (being null)");
                                }
                            }
                        }
                    }
                }
            };
        }
        return receivers;
    }
    public void capture(){}
    public static void main(String[] args){
        Netcaptor cap = new Netcaptor(true);
        cap.startCapture(10000,4);


    }
}
