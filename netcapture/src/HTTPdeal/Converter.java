package HTTPdeal;

public class Converter {
    public static char[] Bytes2char(byte[] bytes){
        int len = bytes.length;
        char[] ans = new char[len];
        for(int i = 0;i < len;i ++){
            ans[i] = (char)bytes[i];
        }
        return ans;
    }
}
