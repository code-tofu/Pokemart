package b3.mp.tfip.pokemart.utils;

import b3.mp.tfip.pokemart.model.ProductDAO;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

public class ProductUtils {

    public static ProductDAO createProductDAOFromJson(JsonObject pokeJsonObj) {
        String details = getENflavourText(pokeJsonObj.getJsonArray("flavor_text_entries")) + " " +
                pokeJsonObj.getJsonArray("effect_entries").getJsonObject(0).getString("effect");
        ProductDAO newProduct = new ProductDAO(
                Utils.generateUUID(8),
                pokeJsonObj.getInt("id"),
                pokeJsonObj.getString("name"),
                pokeJsonObj.getJsonObject("category").getString("name"),
                pokeJsonObj.getJsonNumber("cost").doubleValue(),
                details,
                getENname(pokeJsonObj.getJsonArray("names")));
        return newProduct;
    }

    private static String getENflavourText(JsonArray pokeJsonArr) {
        for (JsonValue pokeJsonObj : pokeJsonArr) {
            String lang = pokeJsonObj.asJsonObject().getJsonObject("language").getString("name");
            if (lang.equals("en")) {
                return pokeJsonObj.asJsonObject().getString("text")
                        .replace("\n", " ")
                        .replace("’", "'");
            }
        }
        return "";
    }

    private static String getENname(JsonArray pokeJsonArr) {
        for (JsonValue pokeJsonObj : pokeJsonArr) {
            String lang = pokeJsonObj.asJsonObject().getJsonObject("language").getString("name");
            if (lang.equals("en")) {
                return pokeJsonObj.asJsonObject().getString("name");
            }
        }
        return "";
    }

    public static JsonObject createJsonFromProductDAO(ProductDAO product) {
        return Json.createObjectBuilder()
                .add("productID", product.getProductID())
                .add("apiID", product.getApiID())
                .add("nameID", product.getNameID())
                .add("category", product.getCategory())
                .add("cost", product.getCost())
                .add("details", product.getDetails())
                .add("productName", product.getProductName())
                .build();
    }

}