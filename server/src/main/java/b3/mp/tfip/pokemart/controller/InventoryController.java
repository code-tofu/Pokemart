package b3.mp.tfip.pokemart.controller;

import java.util.List;
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
import b3.mp.tfip.pokemart.utils.Utils;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;

@RestController
public class InventoryController {

    public static final int PAGE_ELEMENT_LIMIT = 10;

    @Autowired
    InventoryService invSvc;

    @GetMapping("api/inventory/shopMain")
    public ResponseEntity<String> getShopMain(@RequestParam(defaultValue = "0") String page) {
        try {
            System.out.println(">> [INFO] Client Query Shop Main Offset:" + page);
            List<CatalogueComponentDTO> storeCompList = invSvc.getShopMainData(
                    PAGE_ELEMENT_LIMIT, PAGE_ELEMENT_LIMIT *
                            Integer.parseInt(page));
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(storeCompList));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("api/inventory/categoryMain")
    public ResponseEntity<String> getAllInventoryCategories() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Utils.createItemJsonABFromMap(invSvc.getAllInventoryCategories(), "category", "count").build()
                            .toString());
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

    @GetMapping("api/inventory/search")
    public ResponseEntity<String> getInventoryBySearch(@RequestParam String query,
            @RequestParam(defaultValue = "0") String page) {
        try {
            List<CatalogueComponentDTO> storeCompListCategory = invSvc.getStoreComponentDataBySearch(query,
                    PAGE_ELEMENT_LIMIT, PAGE_ELEMENT_LIMIT *
                            Integer.parseInt(page));
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(storeCompListCategory));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("api/inventory/count")
    public ResponseEntity<String> getProductsTotalCount() {
        try {
            Optional<Integer> count = invSvc.getProductsTotalCount();
            System.out.println(count);
            if (count.isPresent()) {
                ObjectMapper mapper = new ObjectMapper();
                return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(count.get()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("api/inventory/countSearch")
    public ResponseEntity<String> getProductsTotalCountBySearch(@RequestParam String query) {
        try {
            Optional<Integer> count = invSvc.getProductsTotalCountBySearch(query);
            System.out.println(count);
            if (count.isPresent()) {
                ObjectMapper mapper = new ObjectMapper();
                return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(count.get()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("api/inventory/countCategory")
    public ResponseEntity<String> getProductsTotalCountByCategory(@RequestParam String category) {
        try {
            System.out.println(">>[INFO] Get Count of:" + category);
            Optional<Integer> count = invSvc.getProductsTotalCountByCategory(category);
            if (count.isPresent()) {
                ObjectMapper mapper = new ObjectMapper();
                return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(count.get()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

}
