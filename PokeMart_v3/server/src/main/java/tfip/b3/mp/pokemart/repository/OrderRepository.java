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
import org.springframework.stereotype.Repository;

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
        MatchOperation matchEmail = Aggregation.match(Criteria.where("customerID").is(customerID));
        SortOperation sortByDate = Aggregation.sort(Sort.Direction.DESC, "orderDate");
        ProjectionOperation projectFields = Aggregation.project("orderID", "total", "orderDate");
        Aggregation agg = Aggregation.newAggregation(matchEmail, projectFields, sortByDate);
        AggregationResults<Document> resultDocs = mTemplate.aggregate(agg, "orders", Document.class);

        List<OrderSummaryDAO> resultList = new LinkedList<>();
        for (Document doc : resultDocs) {
            System.out.println(doc.getDate("orderDate"));
            resultList.add(
                    new OrderSummaryDAO(
                            doc.getString("orderID"),
                            doc.getDate("orderDate"),
                            doc.getDouble("total")));
        }
        return resultList;
    }



}
