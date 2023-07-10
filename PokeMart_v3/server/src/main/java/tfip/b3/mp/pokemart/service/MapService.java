package tfip.b3.mp.pokemart.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import tfip.b3.mp.pokemart.model.StoreDAO;
import tfip.b3.mp.pokemart.repository.StoreRepository;

@Service
public class MapService {

    @Autowired
    StoreRepository storeRepo;
    @Value("${google.maps.key}")
    private String GMAP_KEY;
    private static int QPS_MAX = 10;
    private static double costPerM = 0.05;
    private static double expressMultiplier = 1.8;

    public static final LatLng originLatLng = new LatLng(1.2923021783210697, 103.77657727116461);

    public List<StoreDAO> getAllStores() {
        return storeRepo.getAllStores();
    }

    //THIS CAN BE DONE ON CLIENT SIDE, BUT USING SERVER FOR FAMILIARISATION WITH JAVA CLIENT
    public JsonObject getDistanceCost(LatLng destination) throws ApiException, InterruptedException, IOException, Exception {
        GeoApiContext context = new GeoApiContext.Builder().apiKey(GMAP_KEY).queryRateLimit(QPS_MAX).build();
        System.out.println(context.toString());
        System.out.println(">> [INFO] Distance Matrix Request From: " + destination.toString());
        DistanceMatrixApiRequest distanceRequest = DistanceMatrixApi.newRequest(context);
        DistanceMatrix resp = distanceRequest.origins(originLatLng).destinations(destination).mode(TravelMode.DRIVING)
                .await();
        if(resp.rows[0].elements[0].status.toString() == "ZERO_RESULTS"){
            System.out.println(">> [ERROR] Destination Location Does Not Exist By road");
            throw new Exception("Destination Location Does Not Exist By Road");
        }
        context.close();
        context.shutdown();
        return Json.createObjectBuilder()
        .add("distance",resp.rows[0].elements[0].distance.inMeters)
        .add("defaultCost",resp.rows[0].elements[0].distance.inMeters * costPerM)
        .add("expressCost",resp.rows[0].elements[0].distance.inMeters * costPerM * expressMultiplier)
        .add("duration",resp.rows[0].elements[0].duration.inSeconds)
        .build();
    }

}
