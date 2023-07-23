package tfip.b3.mp.pokemart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.LatLng;

import jakarta.json.JsonObject;
import tfip.b3.mp.pokemart.model.StoreDAO;
import tfip.b3.mp.pokemart.service.MapService;
import tfip.b3.mp.pokemart.utils.ControllerUtil;
import tfip.b3.mp.pokemart.utils.GeneralUtils;

@Controller
public class MapController {

    @Autowired
    MapService mapSvc;

    @GetMapping("/api/location/stores")
    public ResponseEntity<String> getAllStores() {
        try {
            List<StoreDAO> storesList = mapSvc.getAllStores();
            System.out.println(">> [INFO] Showing Stores List of Size: " + storesList.size());
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.writeValueAsString(storesList));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

    @PostMapping("/api/map/distance")
    public ResponseEntity<String> getDistancePrice(@RequestBody String location) {
        try {
            System.out.println(">>[INFO] Distance Matrix Request" + location);
            JsonObject destJson = GeneralUtils.getJsonObjectFromStr(location);
            LatLng destLatLng = new LatLng(destJson.getJsonNumber("lat").doubleValue(), destJson.getJsonNumber("lng").doubleValue());
            String resp = mapSvc.getDistanceCost(destLatLng).toString();
            System.out.println(resp);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }

}
