package tfip.b3.mp.pokemart.utils;

import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import tfip.b3.mp.pokemart.model.AttributeDAO;
import tfip.b3.mp.pokemart.model.ProductDAO;

public class ProductUtil {

    public static ProductDAO createProductDAOFromAPIJson(JsonObject pokeJsonObj) {
        String details = getENflavourText(pokeJsonObj.getJsonArray("flavor_text_entries")) + " " +
                pokeJsonObj.getJsonArray("effect_entries").getJsonObject(0).getString("effect");
        ProductDAO newProduct = new ProductDAO(
                "p" + GeneralUtils.generateUUID(8),
                pokeJsonObj.getInt("id"),
                pokeJsonObj.getString("name"),
                pokeJsonObj.getJsonObject("category").getString("name"),
                pokeJsonObj.getJsonNumber("cost").doubleValue(),
                details,
                getENname(pokeJsonObj.getJsonArray("names")));
        return newProduct;
    }

    public static String getENflavourText(JsonArray pokeJsonArr) {
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

    public static String getENname(JsonArray pokeJsonArr) {
        for (JsonValue pokeJsonObj : pokeJsonArr) {
            String lang = pokeJsonObj.asJsonObject().getJsonObject("language").getString("name");
            if (lang.equals("en")) {
                return pokeJsonObj.asJsonObject().getString("name");
            }
        }
        return "";
    }

    public static AttributeDAO createAttributeDAOFromJson(JsonObject pokeJsonObj, String productId) {
        JsonArray pokeJsonArr = pokeJsonObj.getJsonArray("attributes");
        List<String> attrList = new ArrayList<>();
        for (JsonValue pokeJsonVal : pokeJsonArr) {
            attrList.add(pokeJsonVal.asJsonObject().getString("name"));
        }
        return new AttributeDAO(productId, attrList);
    }

    public static JsonObject createJsonFromProductDAO(ProductDAO product) {
        return Json.createObjectBuilder()
                .add("productID", product.getProductID())
                .add("apiID", product.getApiID())
                .add("nameID", product.getNameID())
                .add("category", product.getCategory())
                .add("cost", product.getCost())
                .add("details", product.getDescription())
                .add("productName", product.getProductName())
                .build();
    }

}