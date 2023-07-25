package tfip.b3.mp.pokemart.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tfip.b3.mp.pokemart.model.AttributeDAO;
import tfip.b3.mp.pokemart.model.ProductDAO;

import static tfip.b3.mp.pokemart.repository.ProductQueries.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    @Autowired
    JdbcTemplate jTemplate;

    @Transactional
    public boolean insertProduct(ProductDAO newProd) throws DataAccessException {
        if (jTemplate.queryForObject(EXISTS_PRODUCT_BY_API_ID, Boolean.class, newProd.getApiID())) {
            System.out.println(">> [WARNING] Insert Product: Product(API_ID) Already Exists");
            return false;
        }
        jTemplate.update(INSERT_NEW_PRODUCT,
                newProd.getProductID(),
                newProd.getApiID(),
                newProd.getNameID(),
                newProd.getCategory(),
                newProd.getCost(),
                newProd.getDescription(),
                newProd.getProductName());
        return true;
    }

    @Transactional
    public boolean insertProduct(ProductDAO newProd, AttributeDAO attributes) throws DataAccessException {
        if (jTemplate.queryForObject(EXISTS_PRODUCT_BY_API_ID, Boolean.class, newProd.getApiID())) {
            System.out.println(">> [WARNING] Insert Product: Product (API_ID) Already Exists");
            return false;
        }
        jTemplate.update(INSERT_NEW_PRODUCT,
                newProd.getProductID(),
                newProd.getApiID(),
                newProd.getNameID(),
                newProd.getCategory(),
                newProd.getCost(),
                newProd.getDescription(),
                newProd.getProductName());
        for (String attribute : attributes.getAttributes()) {
            jTemplate.update(INSERT_ATTRIBUTE, newProd.getProductID(), attribute);
        }
        return true;
    }

    @Transactional
    public boolean insertCustomProduct(ProductDAO newProd, AttributeDAO attributes) throws DataAccessException {
        jTemplate.update(INSERT_NEW_PRODUCT,
                newProd.getProductID(),
                // null,
                0000,
                newProd.getNameID(),
                newProd.getCategory(),
                newProd.getCost(),
                newProd.getDescription(),
                newProd.getProductName());
        for (String attribute : attributes.getAttributes()) {
            jTemplate.update(INSERT_ATTRIBUTE, newProd.getProductID(), attribute);
        }
        return true;
    }

    public Optional<ProductDAO> getProductByApiID(int apiID) throws DataAccessException {
        return Optional.of(jTemplate.queryForObject(SELECT_PRODUCT_BY_API_ID, new ProductDAOMapper(), apiID));
    }

    public Optional<ProductDAO> getProductByProductID(String productID) throws DataAccessException {
        return Optional.of(jTemplate.queryForObject(SELECT_PRODUCT_BY_PRODUCT_ID, new ProductDAOMapper(), productID));
    }

    public List<String> getAllProductIDs(int limit, int offset) throws DataAccessException {
        List<String> item_ids = new ArrayList<>();
        SqlRowSet rs = jTemplate.queryForRowSet(SELECT_ALL_PRODUCT_ID, limit, offset);
        while (rs.next()) {
            item_ids.add(rs.getString("product_id"));
        }
        return item_ids;
    }

    public AttributeDAO getAttributesOfProduct(String productID) {
        return new AttributeDAO(productID,
                jTemplate.queryForList(SELECT_ATTRIBUTES_BY_PRODUCT_ID, String.class, productID));
    }

    public boolean checkProductIDExisting(String productID) throws DataAccessException {
        return jTemplate.queryForObject(EXISTS_PRODUCT_BY_PRODUCT_ID, Boolean.class, productID);
    }
}
