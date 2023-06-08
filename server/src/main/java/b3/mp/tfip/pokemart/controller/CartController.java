package b3.mp.tfip.pokemart.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import b3.mp.tfip.pokemart.service.CartService;
import b3.mp.tfip.pokemart.utils.ControllerUtil;
import b3.mp.tfip.pokemart.utils.Utils;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@RestController
public class CartController {

    @Autowired
    CartService cartSvc;

    @PostMapping(path = "/api/cart/upsert/{userID}", consumes = "application/json")
    public ResponseEntity<String> insertNewCartItem(@PathVariable String userID, @RequestBody String payload) {
        try {
            System.out.println(payload);
            JsonObject cartJson = Utils.getJsonObjectFromStr(payload);
            cartSvc.insertNewCartItem(userID, cartJson.getString("productID"), cartJson.getInt("quantity"));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Json.createObjectBuilder().add("201 CREATED", "Inserted New Cart Item").build().toString());

        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("/api/cart/{userID}")
    public ResponseEntity<String> getFullCartbyUser(@PathVariable String userID) {
        try {
            Map<String, Integer> cartMap = cartSvc.getFullCart(userID);
            JsonArrayBuilder jsonAB = Json.createArrayBuilder();
            JsonObjectBuilder jsonOB = Json.createObjectBuilder();
            for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
                jsonAB.add(jsonOB.add("productID", entry.getKey()).add("quantity", entry.getValue()));
            }
            return ResponseEntity.status(HttpStatus.OK).body(jsonAB.build().toString());
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

}
