import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = new File("").getAbsolutePath() + "\\res\\output.js.map";
        SourceMapParser parser = new SourceMapParser();
        parser.parseFile(path);
        String[] sources = parser.getSources();
        String[] names = parser.getNames();
        String[] mappings = parser.getMappings();
        SourceMapDecoder decoder = new SourceMapDecoder();
        ArrayList<Integer> res;
        int lastOutputIndex = 0;
        int lastInputIndex = 0;
        int lastInputColumn = 0;
        int lastFile = 0;
        int lastName = 0;
        for (String s : mappings) {
            res = decoder.decode(s);
            lastOutputIndex += res.get(0);
            lastFile += res.get(1);
            lastInputColumn += res.get(2);
            lastInputIndex += res.get(3);
            lastName += res.get(4);
            System.out.println("Symbol " + lastOutputIndex + " maps to column " + lastInputColumn + ", symbol " + lastInputIndex + " in file " + sources[lastFile] + " and belongs to " + names[lastName]);
        }
    }
}
