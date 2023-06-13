package b3.mp.tfip.pokemart.utils;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bson.BsonArray;
import org.bson.BsonValue;

import b3.mp.tfip.pokemart.model.ItemCountDAO;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class Utils {

    public static JsonObject getJsonObjectFromStr(String jsonString) {
        StringReader sr = new StringReader(jsonString.toString());
        JsonReader jsr = Json.createReader(sr);
        return (JsonObject) jsr.read();
    }

    public static String generateUUID(int numOfChar) {
        return UUID.randomUUID().toString().substring(0, numOfChar);
    }

    public static JsonArrayBuilder createItemJsonABFromMap(Map<String, Integer> itemMap, String key, String value) {
        JsonArrayBuilder jsonAB = Json.createArrayBuilder();
        JsonObjectBuilder jsonOB = Json.createObjectBuilder();
        for (Map.Entry<String, Integer> entry : itemMap.entrySet()) {
            jsonAB.add(jsonOB.add(key, entry.getKey()).add(value, entry.getValue()));
        }
        return jsonAB;

    }

    public static List<ItemCountDAO> createItemCountListFromJson(JsonArray jsonArr, String key, String value) {
        List<ItemCountDAO> itemCountList = new LinkedList<>();
        for (JsonValue j : jsonArr) {
            itemCountList.add(new ItemCountDAO(j.asJsonObject().getString(key), j.asJsonObject().getInt(value)));
        }
        return itemCountList;
    }

    // DEPRECATED
    public static Map<String, Integer> createCountMapFromJson(JsonArray jsonArr, String key, String value) {
        Map<String, Integer> countMap = new HashMap<>();
        for (JsonValue j : jsonArr) {
            countMap.put(j.asJsonObject().getString(key), j.asJsonObject().getInt(value));
        }
        return countMap;
    }

    // DEPRECATED
    public static Map<String, Integer> createCountMapFromBson(BsonArray bsonArr, String key, String value) {
        Map<String, Integer> countMap = new HashMap<>();
        for (BsonValue b : bsonArr) {
            countMap.put(b.asDocument().getString(key).toString(), b.asDocument().getNumber(value).intValue());
        }
        return countMap;
    }

}
