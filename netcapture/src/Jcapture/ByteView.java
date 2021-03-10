package Jcapture;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
public class ByteView {
    public static Set<Character> avaliableSet = null;
    public static void init(){
        avaliableSet = new HashSet<>();
        for(int i = 33; i < 126;i++){
            avaliableSet.add((char)i);
        }
    }
    public static String byteToHex(byte b){
        String hex = Integer.toHexString(b & 0xFF);
        if(hex.length() < 2){
            hex = "0" + hex;
        }
        return hex;
    }

    public static void view(byte[] bytes,int linewidth){
        if(avaliableSet == null){
            init();
        }
        int len = bytes.length;
        int i = 0;
        int linenum = 0;
        List<Character> cl = new ArrayList<>();
        while(i < len){
            cl = new ArrayList<>();
            linenum = 0;
            while (linenum < linewidth && i < len){

                System.out.print(byteToHex(bytes[i])+" ");
                if(!avaliableSet.contains((char)bytes[i])){
                    cl.add(' ');
                }
                else{
                    cl.add((char)bytes[i]);
                }
                linenum++;
                i++;

            }
            if(i % linewidth != 0){
                for(int j = 0;j < linewidth - i % linewidth;j++){
                    System.out.print("   ");
                }
            }
            System.out.print("  |");
            for(Character c: cl){
                System.out.print(c);
            }
            System.out.println("");

        }
    }

    public static void main(String[] args) {
        ByteView.init();
    }
}
