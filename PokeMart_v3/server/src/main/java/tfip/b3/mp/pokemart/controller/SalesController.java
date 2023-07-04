package tfip.b3.mp.pokemart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import tfip.b3.mp.pokemart.service.InventoryService;
import tfip.b3.mp.pokemart.utils.ControllerUtil;
import tfip.b3.mp.pokemart.utils.GeneralUtils;

@Controller
public class SalesController {

    @Autowired
    InventoryService invSvc;

    @PostMapping("api/sales/editDiscount/{productID}")
    public ResponseEntity<String> editDiscountOfProduct(@PathVariable String productID, @RequestBody String payload) {
        try {
            System.out.println(">>[INFO] Edit Discount of " + productID + "- Discount:" + payload);
            JsonObject editJson = GeneralUtils.getJsonObjectFromStr(payload);
            Double editValue = Double.parseDouble(editJson.getString("discount"));
            if (invSvc.updateInventoryDiscount(productID, editValue) < 1) {
                throw new Exception("ProductID not in inventory");
            }
            return ResponseEntity.status(HttpStatus.OK).body(Json.createObjectBuilder()
                    .add("200 Edited Discount For: " + productID + "- Value", editValue).build().toString());
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @PostMapping("api/sales/editStock/{productID}")
    public ResponseEntity<String> editStockOfProduct(@PathVariable String productID, @RequestBody String payload) {
        try{
            System.out.println(">>[INFO] Edit Stock of " + productID + "- Stock:" + payload);
            JsonObject editJson = GeneralUtils.getJsonObjectFromStr(payload);
            int stock = Integer.parseInt(editJson.getString("stock"));
            if(invSvc.updateInventoryStock(productID,stock) < 1){
                throw new Exception("ProductID not in inventory");
            }
            return ResponseEntity.status(HttpStatus.OK).body(Json.createObjectBuilder()
                    .add("200 Edited Stock For: " + productID , stock).build().toString());
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

        @PostMapping("api/sales/editComments/{productID}")
    public ResponseEntity<String> editCommentsOfProduct(@PathVariable String productID, @RequestBody String payload) {
        try {
            System.out.println(">>[INFO] Edit Comment of " + productID + "- Comment:" + payload);
            JsonObject editJson = GeneralUtils.getJsonObjectFromStr(payload);
            String comment = editJson.getString("comments");
            if (invSvc.updateInventoryComments(productID, comment) < 1) {
                throw new Exception("ProductID not in inventory");
            }
            return ResponseEntity.status(HttpStatus.OK).body(Json.createObjectBuilder()
                    .add("200 Edited Comment For: " + productID , comment).build().toString());
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }


}
