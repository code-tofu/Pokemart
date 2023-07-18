package tfip.b3.mp.pokemart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import tfip.b3.mp.pokemart.model.OrderDAO;
import tfip.b3.mp.pokemart.model.OrderSummaryDAO;
import tfip.b3.mp.pokemart.service.EmailService;
import tfip.b3.mp.pokemart.service.OrderService;
import tfip.b3.mp.pokemart.utils.ControllerUtil;
import tfip.b3.mp.pokemart.utils.GeneralUtils;
import tfip.b3.mp.pokemart.utils.OrderUtil;

@RestController
public class OrderController {

    @Autowired
    OrderService orderSvc;

    @Autowired
    EmailService emailSvc;

    @PostMapping(path = "/api/order/newOrder", consumes = "application/json")
    public ResponseEntity<String> placeOrder(@RequestBody String orderJson) {
        System.out.println(">> [INFO] Recieved:" + orderJson);
        try {
            OrderDAO processedOrder = orderSvc
                    .createNewOrder(OrderUtil.createOrderFromJson(GeneralUtils.getJsonObjectFromStr(orderJson)));
            System.out.println(
                    ">> [INFO] Created Order:" + processedOrder.getOrderID() + " |Date: "
                            + processedOrder.getOrderDate());
                        
            emailSvc.sendOrderEmail("pokemart.tofucode@gmail.com", //processedOrder.getCustomerEmail()
            "Your Order " + processedOrder.getOrderID() + " has been confirmed!", processedOrder);

            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.writeValueAsString(processedOrder));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("/api/order/{orderID}")
    public ResponseEntity<String> getOrderDetailByOrderID(@PathVariable String orderID) {
        try {
            System.out.println(">> [INFO] Retrieving Order:" + orderID);
            OrderDAO order = orderSvc.getOrderbyOrderID(orderID);
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.writeValueAsString(order));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("/api/order/history/{customerID}")
    public ResponseEntity<String> getAllOrderSummariesByUser(@PathVariable String customerID) {
        try {
            System.out.println(">> [INFO] Retrieving Purchase History from:" + customerID);
            List<OrderSummaryDAO> orderSummaries = orderSvc.getOrderSummaryByCustomerID(customerID);
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.writeValueAsString(orderSummaries));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @DeleteMapping(path = "/api/order/{orderId}")
    @ResponseBody
    public ResponseEntity<String> deliveredOrder(@PathVariable String orderId) {
        if (orderSvc.markOrderDelivered(orderId))
            return ResponseEntity.ok().build();
        JsonObject error = Json.createObjectBuilder().add("404 Error", "Order Not Found").build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.toString());

    }

}
