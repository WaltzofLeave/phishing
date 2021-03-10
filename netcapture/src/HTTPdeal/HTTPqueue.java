package HTTPdeal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import jpcap.packet.TCPPacket;
import java.util.ArrayList;
public class HTTPqueue {
    private final Map<Integer,List<byte[]>> queues = new HashMap<>();
    public HTTPqueue(){}
    public void addpacket(TCPPacket packet){
        int port;
        if(packet.src_port != 80){
            port = packet.src_port;
        }else{
            port = packet.dst_port;
        }
        if(queues.containsKey(port)){
            queues.get(port).add(packet.data);
        }else{
            queues.put(port,new LinkedList<>());
        }

    }
    public List<byte[]> getpacket(){
        for(int key : queues.keySet()){
            //判断是否为http头的方法，
        }
        return null;
    }
}
