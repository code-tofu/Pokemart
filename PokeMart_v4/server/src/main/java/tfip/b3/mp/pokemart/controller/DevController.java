package tfip.b3.mp.pokemart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import tfip.b3.mp.pokemart.service.DevService;
import tfip.b3.mp.pokemart.utils.ControllerUtil;

@RestController
public class DevController {

    public static final String PRODUCT_DB_DEFAULT_SIZE = "50";

    @Autowired
    DevService devSvc;
    
    @GetMapping("/api/dev/create/productDB")
    public ResponseEntity<String> createProductDB(@RequestParam(defaultValue = PRODUCT_DB_DEFAULT_SIZE) String size) {
        try {
            devSvc.createNewDatabase(Integer.parseInt(size));
            JsonObject resp = Json.createObjectBuilder().add("201 Created: Database Size", size).build();
            return ResponseEntity.status(HttpStatus.CREATED).body(resp.toString());
            } catch (Exception ex) {
                 return ControllerUtil.exceptionHandler(ex);
            }
    }

    @GetMapping("/api/dev/create/inventory")
    public ResponseEntity<String> createNewInventory() {
            devSvc.createNewInventory();
            JsonObject resp = Json.createObjectBuilder().add("201 Created", "Inventory Creation Success").build();
            System.out.println(">> [INFO] Inventory Creation Success");
            return ResponseEntity.status(HttpStatus.CREATED).body(resp.toString());
    }

    @GetMapping("/api/dev/create/stores")
        public ResponseEntity<String> createNewStores() {
            devSvc.createNewStores();
            JsonObject resp = Json.createObjectBuilder().add("201 Created", "Store Database Creation Success").build();
            System.out.println(">> [INFO] Store Database Creation Success");
            return ResponseEntity.status(HttpStatus.CREATED).body(resp.toString());
    }
}
