package tfip.b3.mp.pokemart.utils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import tfip.b3.mp.pokemart.model.OrderDAO;
import tfip.b3.mp.pokemart.model.OrderItemDTO;
import tfip.b3.mp.pokemart.model.ShippingType;
import tfip.b3.mp.pokemart.model.VoucherDTO;

public class OrderUtil {

    public static OrderItemDTO createOrderItemDTOfromJSON(JsonObject jsonob) {
        return new OrderItemDTO(
                jsonob.getString("productID"),
                jsonob.getString("productName"),
                jsonob.getJsonNumber("cost").doubleValue(),
                jsonob.getJsonNumber("discount").doubleValue(),
                jsonob.getJsonNumber("quantity").intValue());
    }

    public static OrderItemDTO createOrderItemDTOfromDocument(Document doc) {
        return new OrderItemDTO(
                doc.getString("productID"),
                doc.getString("productName"),
                doc.getDouble("cost"),
                doc.getDouble("discount"),
                doc.getInteger("quantity"));
    }

    public static Document createDocumentFromOrderItemDTO(OrderItemDTO orderItem) {
        return new Document().append("productID", orderItem.getProductID())
                .append("productName", orderItem.getProductName())
                .append("cost", orderItem.getCost())
                .append("quantity", orderItem.getQuantity())
                .append("discount", orderItem.getDiscount());
    }

    public static VoucherDTO createVoucherDTOfromJSON(JsonObject jsonob) {
        return new VoucherDTO(
                jsonob.getString("voucherID"),
                jsonob.getJsonNumber("discount").doubleValue(),
                jsonob.getJsonNumber("deduct").doubleValue());
    }

    public static VoucherDTO createVoucherDTOfromDocument(Document doc) {
        return new VoucherDTO(
                doc.getString("voucherID"),
                doc.getDouble("discount"),
                doc.getDouble("deduct"));
    }

    public static Document createDocumentFromVoucherDTO(VoucherDTO voucher) {
        return new Document().append("voucherID", voucher.getVoucherID())
                .append("discount", voucher.getDiscount())
                .append("deduct", voucher.getDeduct());
    }

    public static OrderDAO createOrderFromJson(JsonObject jsonob) {
        JsonArray jsonItemsArr = jsonob.getJsonArray("items");
        List<OrderItemDTO> items = new LinkedList<>();
        for (JsonValue e : jsonItemsArr) {
            items.add(createOrderItemDTOfromJSON(e.asJsonObject()));
        }
        // JsonArray jsonVoucherArr = jsonob.getJsonArray("vouchers");
        // List<VoucherDTO> vouchers = new LinkedList<>();
        // for (JsonValue e : jsonVoucherArr) {
        //     vouchers.add(createVoucherDTOfromJSON(e.asJsonObject()));
        // }

        return new OrderDAO(
                "o" + GeneralUtils.generateUUID(8),
                new Date(),
                jsonob.getString("customerID"),
                jsonob.getString("customerName"),
                jsonob.getString("customerPhone"),
                jsonob.getString("customerEmail"),
                jsonob.getString("shippingAddress"),
                ShippingType.valueOf(jsonob.getString("shippingType")),
                jsonob.getJsonNumber("subtotal").doubleValue(),
                jsonob.getJsonNumber("shippingCost").doubleValue(),
                jsonob.getJsonNumber("total").doubleValue(),
                items);
    }

    public static Document createDocFromOrder(OrderDAO order) {
        List<Document> itemsDocuments = new LinkedList<>();
        for (OrderItemDTO item : order.getItems()) {
            itemsDocuments.add(createDocumentFromOrderItemDTO(item));
        }
        // List<Document> vouchersDocuments = new LinkedList<>();
        // for (VoucherDTO voucher : order.getVouchers()) {
        //     vouchersDocuments.add(createDocumentFromVoucherDTO(voucher));
        // }
        Document newDoc = new Document();
        newDoc.append("orderID", order.getOrderID());
        newDoc.append("orderDate", order.getOrderDate());
        newDoc.append("customerID", order.getCustomerID());
        newDoc.append("customerName", order.getCustomerName());
        newDoc.append("customerPhone", order.getCustomerPhone());
        newDoc.append("customerEmail", order.getCustomerEmail());
        newDoc.append("shippingAddress", order.getShippingAddress());
        newDoc.append("shippingType", order.getShippingType().toString());
        newDoc.append("shippingCost", order.getShippingCost());
        newDoc.append("subtotal", order.getSubtotal());
        newDoc.append("total", order.getTotal());
        newDoc.append("items", itemsDocuments);
        // newDoc.append("vouchers", vouchersDocuments);
        return newDoc;
    }

    public static OrderDAO createOrderFromBson(Document doc) {
        List<Document> itemsDocuments = doc.getList("items", Document.class);
        List<OrderItemDTO> items = new LinkedList<>();
        for (Document itemDoc : itemsDocuments) {
            items.add(createOrderItemDTOfromDocument(itemDoc));
        }

        // List<Document> itemsVouchers = doc.getList("vouchers", Document.class);
        // List<VoucherDTO> vouchers = new LinkedList<>();
        // for (Document voucherDoc : itemsVouchers) {
        //     vouchers.add(createVoucherDTOfromDocument(voucherDoc));
        // }
        System.out.println(doc.getString("shippingType"));
        System.out.println("BREAKPOINT");
        return new OrderDAO(
                doc.getString("orderID"),
                doc.getDate("orderDate"),
                doc.getString("customerID"),
                doc.getString("customerName"),
                doc.getString("customerPhone"),
                doc.getString("customerEmail"),
                doc.getString("shippingAddress"),
                ShippingType.valueOf(doc.getString("shippingType")),
                doc.getDouble("subtotal"),
                doc.getDouble("shippingCost"),
                doc.getDouble("total"),
                items);
                // vouchers);
    }

}
