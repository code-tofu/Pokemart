package b3.mp.tfip.pokemart.utils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import b3.mp.tfip.pokemart.model.ItemCountDAO;
import b3.mp.tfip.pokemart.model.OrderDAO;
import jakarta.json.Json;
import jakarta.json.JsonObject;

public class OrderUtil {

    public static Document createDocFromOrder(OrderDAO order) {
        List<Document> itemsList = new LinkedList<>();
        for (ItemCountDAO item : order.getItems()) {
            Document itemDoc = new Document();
            itemsList.add(itemDoc.append("productID", item.getProductID()).append("quantity", item.getQuantity()));
        }
        Document newDoc = new Document();
        newDoc.append("orderID", order.getOrderID());
        newDoc.append("orderDate", order.getOrderDate());
        newDoc.append("customerID", order.getCustomerID());
        newDoc.append("customerName", order.getCustomerName());
        newDoc.append("customerPhone", order.getCustomerPhone());
        newDoc.append("shippingAddress", order.getShippingAddress());
        newDoc.append("items", itemsList);
        newDoc.append("total", order.getTotal());
        return newDoc;

    }

    public static OrderDAO createOrderFromJson(JsonObject jsonob) {
        List<ItemCountDAO> itemCountList = Utils.createItemCountListFromJson(jsonob.getJsonArray("items"), "productID",
                "quantity");
        return new OrderDAO(
                "o" + Utils.generateUUID(8),
                new Date(),
                jsonob.getString("customerID"),
                jsonob.getString("customerName"),
                jsonob.getString("customerPhone"),
                jsonob.getString("shippingAddress"),
                itemCountList,
                jsonob.getJsonNumber("total").doubleValue());
    }

    public static OrderDAO createOrderFromBson(Document doc) {
        List<Document> itemDocList = doc.getList("items", Document.class);
        List<ItemCountDAO> itemCountList = new LinkedList<>();
        for (Document itemDoc : itemDocList) {
            itemCountList.add(new ItemCountDAO(itemDoc.getString("productID"), itemDoc.getInteger("quantity")));
        }
        return new OrderDAO(
                doc.getString("orderID"),
                doc.getDate("orderDate"),
                doc.getString("customerID"),
                doc.getString("customerName"),
                doc.getString("customerPhone"),
                doc.getString("shippingAddress"),
                itemCountList,
                doc.getDouble("total"));
    }

    public static JsonObject createJsonFromOrder(OrderDAO order) {
        return Json.createObjectBuilder()
                .add("orderID", order.getOrderID())
                .add("orderDate", order.getOrderDate().getTime())
                .add("customerID", order.getCustomerID())
                .add("customerName", order.getCustomerName())
                .add("customerPhone", order.getCustomerPhone())
                .add("shippingAddress", order.getShippingAddress())
                .add("total", order.getTotal())
                .build();
    }

}
