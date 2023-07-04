package tfip.b3.mp.pokemart.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.JsonObject;
import tfip.b3.mp.pokemart.model.ProductDAO;
import tfip.b3.mp.pokemart.repository.InventoryRepository;
import tfip.b3.mp.pokemart.repository.ProductRepository;
import tfip.b3.mp.pokemart.repository.SpacesRepository;
import tfip.b3.mp.pokemart.utils.ProductUtil;
import tfip.b3.mp.pokemart.utils.GeneralUtils;

@Service
public class DevService {

    public static final int MAX_ITEM_ID = 999;
    public static final int MAX_PRODUCT_DB_SIZE = 500;
    public static final int MAX_STOCK_SIZE = 99;

    @Value("${api.poke.data.uri}")
    private String pokeAPI;
    @Value("${api.img.url}")
    private String productImgURL;
    @Value("${api.img.file.type}")
    private String productImgType;

    @Autowired
    ProductRepository productRepo;
    @Autowired
    InventoryRepository invRepo;
    @Autowired
    SpacesRepository spacesRepo;

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

                JsonObject productJson = GeneralUtils.getJsonObjectFromStr(itemJsonStr.get());

                if (productJson.getJsonNumber("cost").doubleValue() == 0L) {
                    System.out.println(">> [INFO] Item is zero cost. Restarting Download");
                    continue;
                }

                ProductDAO newProduct = ProductUtil.createProductDAOFromAPIJson(productJson);
                while(productRepo.checkProductIDExisting(newProduct.getProductID())){
                    System.out.println(">> [WARNING] Insert Product: ProductID Already Exists. Generating New Product ID");
                    newProduct.setProductID("p"+ GeneralUtils.generateUUID(8));
                }

                if (!productRepo.insertProduct(newProduct,
                    ProductUtil.createAttributeDAOFromJson(productJson,newProduct.getProductID())
                )) {
                    System.out.println(">> [WARNING] Insertion into DB Failed. Restarting Download");
                    continue;
                }

                uploadSprite(newProduct);

                i += 1;
                Thread.sleep(1000); // rate limiter

            } catch (InterruptedException interErr) {
                System.out.println(">> [ERROR] " + interErr);
                System.out.println(">> [WARNING] Rate Limiter Interrupted. Restarting Download.");
            } catch (DataAccessException daErr) {
                System.out.println(">> [ERROR] " + daErr);
                System.out.println(">> [ERROR] DataAccessException. Restarting Download.");
            } catch (MalformedURLException malformedErr){
                System.out.println(">> [ERROR] " + malformedErr);
                System.out.println(">> [ERROR] Image Issue, please manually upload");
            } catch (IOException ioErr){
                System.out.println(">> [ERROR] " + ioErr);
                System.out.println(">> [ERROR] Image Issue, please manually upload");
            }

        }
        System.out.println(">> [INFO] Created New Database of Size:" + size);
    }

    public Optional<String> getProductDataFromAPI(int productApiID) {
        String itemURL = UriComponentsBuilder.fromUriString(pokeAPI).pathSegment("item")
                .pathSegment(Integer.toString(productApiID)).toUriString();
        System.out.println(">> [INFO] Retrieve Product Data from:" + itemURL);
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> req = RequestEntity.get(itemURL).build();

        try {
            ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
            System.out.println(">> [INFO] API RESPONSE:" + resp.getBody());
            return Optional.of(resp.getBody());
        } catch (RestClientException restErr) {
            System.out.println(">> [ERROR] PokeAPI Server Error:" + restErr);
            return Optional.empty();
        }
    }

    public void createNewInventory() {
        Random rand = new Random();
        try {
            List<String> products = productRepo.getAllProductIDs(MAX_PRODUCT_DB_SIZE, 0);
            for (String productID : products) {
                if(invRepo.checkProductExistsInInventory(productID)) continue;
                invRepo.insertInventoryItem(productID, rand.nextInt(MAX_STOCK_SIZE) + 1);
            }
        } catch (DataAccessException daErr) {
            System.out.println(">> [ERROR] " + daErr);
        }
    }

    public void uploadSprite(ProductDAO product) throws MalformedURLException, IOException{
        Map<String, String> prodData = new HashMap<>();
        prodData.put("productID",product.getProductID());
        prodData.put("apiID", Integer.toString(product.getApiID()));
        prodData.put("productName", product.getProductName());
        URL url = new URL(productImgURL + product.getNameID() + "." + productImgType);
        InputStream is = new BufferedInputStream(url.openStream());
        byte[] img = is.readAllBytes();
        spacesRepo.uploadSprite(prodData, img,product.getNameID(), productImgType);
    }

}

