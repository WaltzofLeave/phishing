package HTTPdeal;

import java.util.HashSet;
import java.util.Set;

public class Viewer {
    public static Set<Character> avaliableSet = null;
    public static void init(){
        avaliableSet = new HashSet<>();
        for(int i = 33; i < 126;i++){
            avaliableSet.add((char)i);
        }
        avaliableSet.add('\n');
    }
    public static void viewBytesByString(byte[] info,boolean viewablecharonly) {
        int len = info.length;
        if (avaliableSet == null) {
            init();
        }
        char[] infochs = Converter.Bytes2char(info);
        if (viewablecharonly) {
            for (int i = 0; i < len; i++) {
                if (avaliableSet.contains(infochs[i])) {
                    System.out.print(infochs[i]);
                } else {
                    System.out.print(" ");
                }
            }
        } else {
            for (int i = 0; i < len; i++) {
                System.out.print(infochs[i]);
            }
        }
    }
}
