package b3.mp.tfip.pokemart.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import b3.mp.tfip.pokemart.model.CatalogueComponentDTO;
import b3.mp.tfip.pokemart.service.InventoryService;
import b3.mp.tfip.pokemart.utils.ControllerUtil;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;

@RestController
public class InventoryController {

    @Autowired
    InventoryService invSvc;

    @GetMapping("api/inventory/storeMain")
    public ResponseEntity<String> getStoreMain(@RequestParam(defaultValue = "10") String limit,
            @RequestParam(defaultValue = "0") String offset) {
        try {
            List<CatalogueComponentDTO> storeCompList = invSvc.getStoreComponentData(Integer.parseInt(limit),
                    Integer.parseInt(offset));
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(storeCompList));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("api/inventory/categoryMain")
    public ResponseEntity<String> getAllInventoryCategories() {
        try {
            Map<String, Integer> categoryMap = invSvc.getAllInventoryCategories();
            JsonArrayBuilder jsonAB = Json.createArrayBuilder();
            JsonObjectBuilder jsonOB = Json.createObjectBuilder();
            for (Map.Entry<String, Integer> entry : categoryMap.entrySet()) {
                jsonAB.add(jsonOB.add("category", entry.getKey()).add("count", entry.getValue()));
            }
            return ResponseEntity.status(HttpStatus.OK).body(jsonAB.build().toString());
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("api/inventory/category/{category}")
    public ResponseEntity<String> getInventoryByCategory(@PathVariable String category) {
        try {
            List<CatalogueComponentDTO> storeCompListCategory = invSvc.getStoreComponentDataByCategory(category);
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(storeCompListCategory));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("api/inventory/{productID}")
    public ResponseEntity<String> getStockofProduct(@PathVariable String productID) {
        try {
            Optional<Integer> quantity = invSvc.getStockofProduct(productID);
            if (quantity.isEmpty())
                throw new Exception("ProductID not in inventory");
            JsonObjectBuilder jsonOB = Json.createObjectBuilder().add("productID", productID).add("quantity",
                    quantity.get());
            return ResponseEntity.status(HttpStatus.OK).body(jsonOB.build().toString());
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }
}
