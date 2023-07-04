package tfip.b3.mp.pokemart.utils;

import java.io.StringReader;
import java.util.Map;
import java.util.UUID;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

public class GeneralUtils {

    public static String generateUUID(int numOfChar) {
        return UUID.randomUUID().toString().substring(0, numOfChar);
    }
    
    public static JsonObject getJsonObjectFromStr(String jsonString){
        StringReader sr = new StringReader(jsonString.toString());
        JsonReader jsr = Json.createReader(sr);
        return (JsonObject) jsr.read();
    }   

    public static JsonArrayBuilder createItemJsonABFromMap(Map<String, Integer> itemMap, String key, String value) {
        JsonArrayBuilder jsonAB = Json.createArrayBuilder();
        JsonObjectBuilder jsonOB = Json.createObjectBuilder();
        for (Map.Entry<String, Integer> entry : itemMap.entrySet()) {
            jsonAB.add(jsonOB.add(key, entry.getKey()).add(value, entry.getValue()));
        }
        return jsonAB;
    }

    public static String concatWords(String inputString){
        return inputString.replace(" ", "-");
    }
}
