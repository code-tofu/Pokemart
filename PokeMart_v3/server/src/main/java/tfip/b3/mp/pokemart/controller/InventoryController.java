package tfip.b3.mp.pokemart.controller;

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

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import tfip.b3.mp.pokemart.model.CatalogueItemDTO;
import tfip.b3.mp.pokemart.model.InventoryDAO;
import tfip.b3.mp.pokemart.service.InventoryService;
import tfip.b3.mp.pokemart.utils.ControllerUtil;
import tfip.b3.mp.pokemart.utils.GeneralUtils;

@RestController
public class InventoryController {

    public static final String PAGE_ELEMENT_DEFAULT = "10";
    public static final String CATEGORY_ELEMENT_DEFAULT = "15";

    @Autowired
    InventoryService invSvc;

    @GetMapping("api/inventory/shop")
    public ResponseEntity<String> getShopInventory(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue=PAGE_ELEMENT_DEFAULT) String limit) {
        try {
            System.out.println(">> [INFO] Client Query Shop Main Offset:" + page);
            List<CatalogueItemDTO> shopCatalogue = invSvc.getShopCatalogue(
                    Integer.parseInt(limit),
                    Integer.parseInt(limit) * Integer.parseInt(page));
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(shopCatalogue));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("api/inventory/category/{category}")
    public ResponseEntity<String> getInventoryByCategory(@PathVariable String category, @RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue=PAGE_ELEMENT_DEFAULT) String limit) {
        try {
            List<CatalogueItemDTO> shopCatalogueByCategory = invSvc.getCatalogueByCategory(category,
            Integer.parseInt(limit),
                    Integer.parseInt(limit) * Integer.parseInt(page));
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(shopCatalogueByCategory));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("api/inventory/search")
    public ResponseEntity<String> getInventoryBySearch(@RequestParam String query,
            @RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue=PAGE_ELEMENT_DEFAULT) String limit) {
        try {
            List<CatalogueItemDTO> shopCatalogueBySearch = invSvc.getCatalogueBySearch(query,
                    Integer.parseInt(limit), Integer.parseInt(limit) *
                            Integer.parseInt(page));
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(shopCatalogueBySearch));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("api/inventory/productCount")
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

    @GetMapping("api/inventory/category")
    public ResponseEntity<String> getAllInventoryCategories(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue=CATEGORY_ELEMENT_DEFAULT) String limit) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GeneralUtils.createItemJsonABFromMap(invSvc.getAllInventoryCategories(
                        Integer.parseInt(limit), Integer.parseInt(limit) *
                            Integer.parseInt(page)
                    ), "category", "count").build()
                            .toString());
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("api/inventory/{productID}")
    public ResponseEntity<String> getInventoryDetailsByProductID (@PathVariable String productID) {
        try {
            InventoryDAO itemInvDetails = invSvc.getInventoryDetailsofProduct(productID);
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(itemInvDetails));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("api/inventory/stock/{productID}")
    public ResponseEntity<String> getStockofProduct(@PathVariable String productID) {
        try {
            Optional<Integer> quantity = invSvc.getStockofProduct(productID);
            if (quantity.isEmpty())
                throw new Exception("ProductID not in inventory");
            JsonObjectBuilder jsonOB = Json.createObjectBuilder().add("productID", productID).add("count",
                    quantity.get());
            return ResponseEntity.status(HttpStatus.OK).body(jsonOB.build().toString());
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @GetMapping("api/inventory/productCountBySearch")
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

    @GetMapping("api/inventory/productCountByCategory")
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

    @GetMapping("api/inventory/categoryCount")
    public ResponseEntity<String> getCategoryCount(){
        try {
            Optional<Integer> count = invSvc.getCategoryCount();
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
