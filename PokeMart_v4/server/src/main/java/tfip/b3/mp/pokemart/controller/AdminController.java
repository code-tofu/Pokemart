package tfip.b3.mp.pokemart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.security.RolesAllowed;
import jakarta.json.Json;
import tfip.b3.mp.pokemart.service.ProductService;
import tfip.b3.mp.pokemart.utils.ControllerUtil;
import tfip.b3.mp.pokemart.utils.GeneralUtils;

@RestController
public class AdminController {

    @Autowired
    ProductService productSvc;

    @PostMapping(path = "/api/admin/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_DEVELOPER" })
    public ResponseEntity<String> createNewProduct(@RequestPart String itemDetails, @RequestPart MultipartFile image) {
        System.out.println(
                "Recieved:" + itemDetails.toString() + "," + image.getOriginalFilename() + "| DATA:" + image);
        try {
            String productID = productSvc.insertCustomProductFromJson(GeneralUtils.getJsonObjectFromStr(itemDetails),
                    image, image.getContentType());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Json.createObjectBuilder().add("201 CREATED", productID).build().toString());
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

}
