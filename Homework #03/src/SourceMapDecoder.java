import java.util.ArrayList;

public class SourceMapDecoder {
    private static final String base64Table = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private ArrayList<Integer> result;

    public SourceMapDecoder() {
        result = new ArrayList<>();
    }

    public ArrayList<Integer> decode(String str) {
        if (!result.isEmpty()){
            result.clear();
        }
        char c;
        byte interValue;
        int finalValue = 0;
        boolean isStarted = false;
        boolean sign = false;
        int ccouner = 0;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            interValue = getValue(c);
            if (!isStarted) {
                sign = !((interValue & 1) == 0);
                finalValue = (interValue & 30) >> 1;
                isStarted = true;
            }
            else {
                finalValue = finalValue + ((interValue & 31) << ((ccouner == 1) ? 5 : 4));
            }
            if ((interValue & 32) == 0) {
                isStarted = false;
                ccouner = 0;
                if (sign) {
                    finalValue = -finalValue;
                }
                result.add(finalValue);
                finalValue = 0;
            }
            ccouner++;
        }
        if (result.size() == 4) {
            result.add(0);
        }
        return result;
    }

    private byte getValue(char c) {
        return (byte) base64Table.indexOf((byte) c);
    }
}
