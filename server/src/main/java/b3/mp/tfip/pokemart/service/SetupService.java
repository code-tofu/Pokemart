package b3.mp.tfip.pokemart.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import b3.mp.tfip.pokemart.repository.InventoryRepo;
import b3.mp.tfip.pokemart.repository.ProductRepo;
import b3.mp.tfip.pokemart.utils.ProductUtils;
import b3.mp.tfip.pokemart.utils.Utils;
import jakarta.json.JsonObject;

@Service
public class SetupService {

    public static final int MAX_ITEM_ID = 999;
    public static final int MAX_PRODUCT_DB_SIZE = 10;
    public static final int MAX_INVENTORY_SIZE = 99;

    @Value("${api.poke.data.uri}")
    private String pokeAPI;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    InventoryRepo invRepo;

    public void createNewDatabase(int size) {
        Random rand = new Random();
        for (int i = 0; i < size;) {
            try {
                System.out.println(">> [INFO] Creating Product #" + (i + 1));
                int randInt = rand.nextInt(MAX_ITEM_ID) + 1;
                Optional<String> itemJsonStr = getProductDataFromAPI(randInt);

                if (itemJsonStr.isEmpty()) {
                    System.out.println(">> [WARNING] Empty Response. Restarting Download");
                    continue;
                }

                JsonObject productJson = Utils.getJsonObjectFromStr(itemJsonStr.get());
                if (!productRepo.insertProduct(ProductUtils.createProductDAOFromJson(productJson))) {
                    System.out.println(">> [WARNING] Insertion into DB Failed. Restarting Download");
                    continue;
                }

                i += 1;
                Thread.sleep(1000); // rate limiter

            } catch (InterruptedException interErr) {
                System.out.println(">> [ERROR] " + interErr);
                System.out.println(">> [WARNING] Rate Limiter Interrupted. Restarting Download.");
            } catch (DataAccessException daErr) {
                System.out.println(">> [ERROR] " + daErr);
                System.out.println(">> [ERROR] DataAccessException. Restarting Download.");
            }

        }
        System.out.println(">> [INFO] Created New Database of Size:" + size);
    }

    public void createNewInventory() throws DataAccessException {
        Random rand = new Random();
        try {
            List<String> products = productRepo.getAllProductIDs(MAX_PRODUCT_DB_SIZE, 0);
            for (String productID : products) {
                invRepo.insertInventoryItem(productID, rand.nextInt(MAX_INVENTORY_SIZE) + 1);
            }
        } catch (DataAccessException daErr) {
            System.out.println(">> [ERROR] " + daErr);
        }
    }

    public Optional<String> getProductDataFromAPI(int productID) {
        String itemURL = UriComponentsBuilder.fromUriString(pokeAPI).pathSegment("item")
                .pathSegment(Integer.toString(productID)).toUriString();
        System.out.println(">> [INFO] Retrieve Product Data from:" + itemURL);
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> req = RequestEntity.get(itemURL).build();

        try {
            ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
            System.out.println(">> [INFO] API RESPONSE:" + resp.getBody());
            return Optional.of(resp.getBody());
        } catch (HttpClientErrorException httpErr) {
            System.out.println(">> [ERROR] PokeAPI Server Error:" + httpErr);
            return Optional.empty();
        }
    }

}
