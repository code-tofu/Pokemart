package tfip.b3.mp.pokemart.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import tfip.b3.mp.pokemart.model.AttributeDAO;
import tfip.b3.mp.pokemart.model.ProductDAO;
import tfip.b3.mp.pokemart.repository.ProductRepository;
import tfip.b3.mp.pokemart.repository.SpacesRepository;
import tfip.b3.mp.pokemart.utils.GeneralUtils;
import tfip.b3.mp.pokemart.utils.ProductUtil;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepo;
    @Autowired
    SpacesRepository spacesRepo;
    @Value("${api.img.file.type}")
    private String productImgType;

    public Optional<ProductDAO> getProductByProductID(String productID) throws DataAccessException {
        return productRepo.getProductByProductID(productID);
    }

    public Optional<ProductDAO> getProductByApiID(int apiID) throws DataAccessException {
        return productRepo.getProductByApiID(apiID);
    }

    public List<String> getAllProductIDs(int limit, int offset) throws DataAccessException {
        return productRepo.getAllProductIDs(limit, offset);
    }

    public AttributeDAO getAttributesOfProduct(String productID) {
        return productRepo.getAttributesOfProduct(productID);
    }

    @Transactional
    public String insertCustomProductFromJson(JsonObject jsonObj, MultipartFile file, String fileType)
            throws java.io.IOException {
        String productID = "p" + GeneralUtils.generateUUID(8);
        while (productRepo.checkProductIDExisting(productID)) {
            System.out.println(">> [WARNING] Insert Product: ProductID Already Exists. Generating New Product ID");
            productID = "p" + GeneralUtils.generateUUID(8);
        }
        ProductDAO newProduct = new ProductDAO(productID, 0, GeneralUtils.concatWords(jsonObj.getString("productName")),
                jsonObj.getString("category"), jsonObj.getJsonNumber("cost").doubleValue(), jsonObj.getString("description"),
                jsonObj.getString("productName"));

        JsonArray pokeJsonArr = jsonObj.getJsonArray("attributes");
        ObjectMapper mapper = new ObjectMapper();
        List<String> attrList = Arrays.asList(mapper.readValue(pokeJsonArr.toString(), String[].class));
        System.out.println(attrList);
        AttributeDAO attributeObj = new AttributeDAO(productID, attrList);
        productRepo.insertCustomProduct(newProduct,
                attributeObj);

        Map<String, String> prodData = new HashMap<>();
        prodData.put("productID", productID);
        prodData.put("productName", newProduct.getProductName());
        spacesRepo.uploadImage(prodData, file, newProduct.getNameID(), productImgType);
        return productID;
    }

    @Transactional // for api/sprite creation/upload - remove?
    public String insertCustomProductFromJson(JsonObject jsonObj, MultipartFile file, String fileType, int apiID,
            String category) throws java.io.IOException {
        String productID = "p" + GeneralUtils.generateUUID(8);
        while (productRepo.checkProductIDExisting(productID)) {
            System.out.println(">> [WARNING] Insert Product: ProductID Already Exists. Generating New Product ID");
            productID = "p" + GeneralUtils.generateUUID(8);
        }

        ProductDAO newProduct = new ProductDAO(productID,
                apiID,
                GeneralUtils.concatWords(jsonObj.getString("productName")),
                category,
                jsonObj.getJsonNumber("cost").doubleValue(),
                jsonObj.getString("description"),
                jsonObj.getString("productName"));

        productRepo.insertCustomProduct(newProduct,
                ProductUtil.createAttributeDAOFromJson(jsonObj, productID));

        Map<String, String> prodData = new HashMap<>();
        prodData.put("productID", productID);
        prodData.put("apiID", Integer.toString(newProduct.getApiID()));
        prodData.put("productName", newProduct.getProductName());
        spacesRepo.uploadImage(prodData, file, newProduct.getNameID(), fileType);
        return productID;
    }

}
