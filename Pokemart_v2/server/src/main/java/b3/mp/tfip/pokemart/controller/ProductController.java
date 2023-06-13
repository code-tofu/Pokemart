package b3.mp.tfip.pokemart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import b3.mp.tfip.pokemart.model.ProductDAO;
import b3.mp.tfip.pokemart.service.ProductService;
import b3.mp.tfip.pokemart.utils.ProductUtils;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
public class ProductController {

    @Autowired
    ProductService productSvc;

    @GetMapping("/api/product/{id}")
    public ResponseEntity<String> getProductByProductID(@PathVariable String id) {
        if (id.length() != 8) {
            JsonObject resp = Json.createObjectBuilder()
                    .add("400 Error", "Product ID has to be 8 characters long").build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp.toString());
        }

        try {
            ProductDAO product = productSvc.getProductByProductID(id).get();
            return ResponseEntity.status(HttpStatus.OK).body(ProductUtils.createJsonFromProductDAO(product).toString());

        } catch (DataAccessException daErr) {
            System.out.println(">> [ERROR] " + daErr);
            JsonObject resp = Json.createObjectBuilder()
                    .add("404 Error", "Product ID does not exist").build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp.toString());
        }
    }

    @GetMapping("/api/product/apiID/{id}")
    public ResponseEntity<String> getProductByApiID(@PathVariable String id) {
        try {
            ProductDAO product = productSvc.getProductByApiID(Integer.parseInt(id)).get();
            return ResponseEntity.status(HttpStatus.OK).body(ProductUtils.createJsonFromProductDAO(product).toString());

        } catch (NumberFormatException NFerr) {
            System.err.println(">> [ERROR] " + NFerr);
            JsonObject resp = Json.createObjectBuilder()
                    .add("400 Error", "Path Variable <id> needs to be a integer").build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp.toString());
        } catch (DataAccessException daErr) {
            System.out.println(">> [ERROR] " + daErr);
            JsonObject resp = Json.createObjectBuilder()
                    .add("404 Error", "API ID does not exist").build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp.toString());
        }
    }

    @GetMapping("/api/products")
    public ResponseEntity<String> getAllProductIDs(@RequestParam(defaultValue = "100") String limit,
            @RequestParam(defaultValue = "0") String offset) {
        try {
            List<String> productsIDs = productSvc.getAllProductIDs(Integer.parseInt(limit), Integer.parseInt(offset));
            JsonObject productsIDJson = Json.createObjectBuilder()
                    .add("productIds", Json.createArrayBuilder(productsIDs)).build();
            return ResponseEntity.status(HttpStatus.OK).body(productsIDJson.toString());
        } catch (NumberFormatException NFerr) {
            System.err.println(">> [ERROR] " + NFerr);
            JsonObject resp = Json.createObjectBuilder()
                    .add("400 Error", "Request Params limit and/or offset needs to be a integer").build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp.toString());
        } catch (DataAccessException daErr) {
            System.out.println(">> [ERROR] " + daErr);
            JsonObject resp = Json.createObjectBuilder()
                    .add("404 Error", "Database does not exist or is empty").build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp.toString());
        }
    }

}
