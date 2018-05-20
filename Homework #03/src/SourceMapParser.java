import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SourceMapParser {
    public String[] sources;
    public String[] names;
    public String[] mappings;

    public SourceMapParser() {
    }

    public void parseFile(String path) throws IOException {
        String input = readFile(path);
        JsonParser parser = new JsonParser();
        JsonObject parseTree = parser.parse(input).getAsJsonObject();
        JsonArray jSources = parseTree.getAsJsonArray("sources");
        sources = jsonArrToStringArr(jSources);
        JsonArray jNames = parseTree.getAsJsonArray("names");
        names = jsonArrToStringArr(jNames);
        JsonPrimitive jMappings = parseTree.getAsJsonPrimitive("mappings");
        mappings = jMappings.getAsString().split(",");
    }

    public String[] getSources() {
        return sources;
    }

    public String[] getNames() {
        return names;
    }

    public String[] getMappings() {
        return mappings;
    }

    private static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    private String[] jsonArrToStringArr(JsonArray jArr) {
        String[] res = new String[jArr.size()];
        for (int i = 0; i < jArr.size(); i++) {
            res[i] = jArr.get(i).getAsString();
        }
        return res;
    }

    /*public SourceMapParser(String path) throws IOException {
        String input = readFile(path);
        JsonParser parser = new JsonParser();
        JsonObject mainObject = parser.parse(input).getAsJsonObject();
        names = mainObject.getAsJsonArray("names");
        sources = mainObject.getAsJsonArray("sources");
        JsonPrimitive pItem = mainObject.getAsJsonPrimitive("mappings");
        mappings = pItem.getAsString().split(",");

    }

    public JsonArray getNames() {
        return names;
    }

    public JsonArray getSources() {
        return sources;
    }

    public String[] getMappings() {
        return mappings;
    }

    private static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }*/
}