package b3.mp.tfip.pokemart.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import b3.mp.tfip.pokemart.model.CatalogueComponentDTO;
import b3.mp.tfip.pokemart.service.CartService;
import b3.mp.tfip.pokemart.service.InventoryService;
import b3.mp.tfip.pokemart.utils.ControllerUtil;
import b3.mp.tfip.pokemart.utils.Utils;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
public class CartController {

    @Autowired
    CartService cartSvc;

    @Autowired
    InventoryService invSvc;

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

    @GetMapping("/api/cart/itemsOnly/{userID}")
    public ResponseEntity<String> getFullCartProductIDsbyUser(@PathVariable String userID) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(Utils
                    .createItemJsonABFromMap(cartSvc.getFullCart(userID), "productID", "quantity").build().toString());
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("/api/cart/{userID}")
    public ResponseEntity<String> getFullCartbyUser(@PathVariable String userID) {
        try {
            Map<String, Integer> cartMap = cartSvc.getFullCart(userID);
            System.out.println(cartMap);
            List<CatalogueComponentDTO> cartList = new LinkedList<>();
            for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
                CatalogueComponentDTO item = invSvc.getShopMainItemByID(entry.getKey());
                item.setQuantity(entry.getValue());
                cartList.add(item);
            }
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(cartList));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("/api/cart/summary/{userID}")
    public ResponseEntity<String> getFullCartSummarybyUser(@PathVariable String userID) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    Utils.createItemJsonABFromMap(cartSvc.getFullCart(userID), "productID", "quantity")
                            .build().toString());
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @DeleteMapping(path = "/api/cart/{userID}/{productID}")
    public ResponseEntity<String> deleteCartItem(@PathVariable String userID, @PathVariable String productID) {
        try {
            if (cartSvc.deleteCartItem(userID, productID) < 1) {
                throw new Exception(productID + " not found in Cart:" + userID);
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(Json.createObjectBuilder().add("200 OK", "Deleted:" + productID + " from Cart:" + userID)
                                .build()
                                .toString());
            }

        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }
}
