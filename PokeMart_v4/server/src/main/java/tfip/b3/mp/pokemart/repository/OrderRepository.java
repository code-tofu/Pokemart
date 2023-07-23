package tfip.b3.mp.pokemart.repository;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import tfip.b3.mp.pokemart.model.OrderDAO;
import tfip.b3.mp.pokemart.model.OrderSummaryDAO;
import tfip.b3.mp.pokemart.utils.OrderUtil;

@Repository
public class OrderRepository {

    @Autowired
    MongoTemplate mTemplate;

    public OrderDAO createNewOrder(OrderDAO order) {
        Document newDoc = OrderUtil.createDocFromOrder(order);
        System.out.println(">> [INFO] Inserting:" + newDoc);
        Document returnDoc = mTemplate.insert(newDoc, "orders");
        System.out.println(returnDoc);
        return OrderUtil.createOrderFromBson(returnDoc);
    }

    public OrderDAO getOrderbyOrderID(String orderID) {
        MatchOperation matchOrder = Aggregation.match(Criteria.where("orderID").is(orderID));
        Aggregation agg = Aggregation.newAggregation(matchOrder);
        AggregationResults<Document> resultDocs = mTemplate.aggregate(agg, "orders", Document.class);
        if (resultDocs.iterator().hasNext())
            return OrderUtil.createOrderFromBson(resultDocs.iterator().next());
        return null;
    }

    public List<OrderSummaryDAO> getOrderSummaryByCustomerID(String customerID) {
        MatchOperation matchID = Aggregation.match(Criteria.where("customerID").is(customerID));
        SortOperation sortByDate = Aggregation.sort(Sort.Direction.DESC, "orderDate");
        ProjectionOperation projectFields = Aggregation.project("orderID", "total", "orderDate","delivered");
        Aggregation agg = Aggregation.newAggregation(matchID, projectFields, sortByDate);
        AggregationResults<Document> resultDocs = mTemplate.aggregate(agg, "orders", Document.class);

        List<OrderSummaryDAO> resultList = new LinkedList<>();
        for (Document doc : resultDocs) {
            System.out.println(doc.toString());
            resultList.add(
                    new OrderSummaryDAO(
                            doc.getString("orderID"),
                            doc.getDate("orderDate"),
                            doc.getDouble("total"),
                            doc.getBoolean("delivered", false)));
        }
        return resultList;
    }

    public String getCustomerIDbyOrderID(String orderID) {
        MatchOperation matchOrder = Aggregation.match(Criteria.where("orderID").is(orderID));
        ProjectionOperation projectFields = Aggregation.project("customerID");
        Aggregation agg = Aggregation.newAggregation(matchOrder,projectFields);
        AggregationResults<Document> resultDocs = mTemplate.aggregate(agg, "orders", Document.class);
        if (resultDocs.iterator().hasNext())
            return resultDocs.iterator().next().getString("customerID");
        return null;
    }

    public boolean markOrderDelivered(String orderId) {
        System.out.println(orderId);
        Query query = Query.query(Criteria.where("orderID").is(orderId));
        Update updateField = new Update().set("delivered", true);
        UpdateResult result = mTemplate.updateFirst(query, updateField, Document.class, "orders");
        System.out.println(result);
        if (result.getModifiedCount() > 0 ) {
            System.out.println(">> [INFO] ORDER " + orderId + " Delivered");
            return true;
        } else {
        System.out.println(">> [INFO] ORDER " + orderId + " Not Found");
        return false;
        }
    }




}
