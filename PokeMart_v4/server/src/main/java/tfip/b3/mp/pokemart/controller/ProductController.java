package tfip.b3.mp.pokemart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import tfip.b3.mp.pokemart.model.AttributeDAO;
import tfip.b3.mp.pokemart.model.InventoryDAO;
import tfip.b3.mp.pokemart.model.ItemDetailDTO;
import tfip.b3.mp.pokemart.model.ProductDAO;
import tfip.b3.mp.pokemart.service.InventoryService;
import tfip.b3.mp.pokemart.service.ProductService;
import tfip.b3.mp.pokemart.utils.ControllerUtil;

@RestController
public class ProductController {

    @Autowired
    ProductService productSvc;
    @Autowired
    InventoryService invSvc;

    @GetMapping("/api/product/{productID}")
    public ResponseEntity<String> getProductByProductID(@PathVariable String productID) {
        if (productID.length() != 9) {
            JsonObject resp = Json.createObjectBuilder()
                    .add("400 Error", "Product ID has to be 9 characters long").build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp.toString());
        }
        try {
            ProductDAO product = productSvc.getProductByProductID(productID).get();
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(product));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        } 
    }

    @GetMapping("/api/product/detail/{productID}")
    public ResponseEntity<String> getProductDetailByProductID(@PathVariable String productID) {
        if (productID.length() != 9) {
            JsonObject resp = Json.createObjectBuilder()
                    .add("400 Error", "Product ID has to be 9 characters long").build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp.toString());
        }
        try {
            ProductDAO productdetail= productSvc.getProductByProductID(productID).get();
            AttributeDAO productAttribute = productSvc.getAttributesOfProduct(productID);
            InventoryDAO itemInvDetails = invSvc.getInventoryDetailsofProduct(productID);
            ItemDetailDTO itemDetail = ItemDetailDTO.ItemDetailFromDAOs(productdetail,itemInvDetails,productAttribute);
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(itemDetail));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("/api/product/apiID/{apiID}")
    public ResponseEntity<String> getProductByApiID(@PathVariable String apiID) {
        try {
            ProductDAO product = productSvc.getProductByApiID(Integer.parseInt(apiID)).get();
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(product));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("/api/product/all")
    public ResponseEntity<String> getAllProductIDs(@RequestParam(defaultValue = "100") String limit, @RequestParam(defaultValue = "0") String offset) {
        try {
            List<String> productsIDs = productSvc.getAllProductIDs(Integer.parseInt(limit), Integer.parseInt(offset));
            JsonObject productsIDJson = Json.createObjectBuilder()
                    .add("productIds", Json.createArrayBuilder(productsIDs)).build();
            return ResponseEntity.status(HttpStatus.OK).body(productsIDJson.toString());
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

}
