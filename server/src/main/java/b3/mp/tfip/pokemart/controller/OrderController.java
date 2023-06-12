package b3.mp.tfip.pokemart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import b3.mp.tfip.pokemart.model.OrderDAO;
import b3.mp.tfip.pokemart.model.OrderSummaryDAO;
import b3.mp.tfip.pokemart.service.OrderService;
import b3.mp.tfip.pokemart.utils.ControllerUtil;
import b3.mp.tfip.pokemart.utils.OrderUtil;
import b3.mp.tfip.pokemart.utils.Utils;

@RestController
public class OrderController {

    @Autowired
    OrderService orderSvc;

    @PostMapping(path = "/api/order/newOrder", consumes = "application/json")
    public ResponseEntity<String> placeOrder(@RequestBody String orderJson) {
        System.out.println(">> [INFO] Recieved:" + orderJson);
        OrderDAO processedOrder = orderSvc
                .createNewOrder(OrderUtil.createOrderFromJson(Utils.getJsonObjectFromStr(orderJson)));
        System.out.println(
                ">> [INFO] Created Order:" + processedOrder.getOrderID() + " |Date: " + processedOrder.getOrderDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderUtil.createJsonFromOrder(processedOrder).toString());
    }

    @GetMapping("/api/order/{orderID}")
    public ResponseEntity<String> getOrderDetailByOrderID(@PathVariable String orderID) {
        try {
            System.out.println(">> [INFO] Retrieving Order:" + orderID);
            OrderDAO order = orderSvc.getOrderbyOrderID(orderID);
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.writeValueAsString(order));
        } catch (JsonProcessingException jpErr) {
            return ControllerUtil.exceptionHandler(jpErr);
        }
    }

    @GetMapping("/api/order/history/{customerID}")
    public ResponseEntity<String> getAllOrderSummariesByUser(@PathVariable String customerID) {
        try {
            System.out.println(">> [INFO] Retrieving Purchase History from:" + customerID);
            List<OrderSummaryDAO> orderSummaries = orderSvc.getOrderSummaryByCustomerID(customerID);
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.writeValueAsString(orderSummaries));
        } catch (JsonProcessingException jpErr) {
            return ControllerUtil.exceptionHandler(jpErr);
        }
    }

}
