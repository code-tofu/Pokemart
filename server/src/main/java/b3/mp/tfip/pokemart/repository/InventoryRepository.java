package b3.mp.tfip.pokemart.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import b3.mp.tfip.pokemart.model.CatalogueComponentDTO;

import static b3.mp.tfip.pokemart.repository.InventoryQueries.*;

import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InventoryRepository {

    @Autowired
    JdbcTemplate jTemplate;

    public boolean insertInventoryItem(String productID, int quantity) throws DataAccessException {
        try {
            jTemplate.update(INSERT_NEW_INVENTORY_ITEM, productID, quantity);
            return true;
        } catch (DataAccessException daErr) {
            System.out.println(">> [ERROR] " + daErr);
            System.out.println(">> [WARNING] Insert Inventory: Existing Stock Already Exists");
            return false;
        }
    }

    public List<CatalogueComponentDTO> getShopMainData(int limit, int offset) throws DataAccessException {
        return jTemplate.query(SELECT_SHOPMAIN_ITEMDATA_WLIMIT, new CatalogueComponentMapper(), limit, offset);
    }

    public CatalogueComponentDTO getShopMainItemByID(String productID) throws DataAccessException {
        return jTemplate.queryForObject(SELECT_SHOPMAIN_ITEMDATA_BY_ID, new CatalogueComponentMapper(), productID);
    }

    public Map<String, Integer> getAllInventoryCategories() throws DataAccessException {
        SqlRowSet rs = jTemplate.queryForRowSet(SELECT_ALL_CATEGORIES_IN_INVENTORY);
        Map<String, Integer> queryMap = new TreeMap<>();
        while (rs.next()) {
            queryMap.put(rs.getString("category"), rs.getInt("count"));
        }
        return queryMap;
    }

    public List<CatalogueComponentDTO> getStoreComponentDataByCategory(String category)
            throws DataAccessException {
        return jTemplate.query(SELECT_INVENTORY_BY_CATEGORY, new CatalogueComponentMapper(), category);
    }

    public List<CatalogueComponentDTO> getStoreComponentDataBySearch(String searchQuery, int limit, int offset)
            throws DataAccessException {
        return jTemplate.query(SELECT_INVENTORY_BY_SEARCH_WLIMIT, new CatalogueComponentMapper(),
                "%" + searchQuery + "%", limit, offset);
    }

    public Optional<Integer> getStockofProduct(String productID) throws DataAccessException {
        SqlRowSet rs = jTemplate.queryForRowSet(SELECT_QUANTITY_OF_PRODUCT, productID);
        if (rs.next()) {
            return Optional.of(rs.getInt("quantity"));
        }
        return Optional.empty();
    }

    public Optional<Integer> getProductsTotalCount() throws DataAccessException {
        return Optional.of(jTemplate.queryForObject(SELECT_TOTAL_COUNT_ALL_PRODUCTS, Integer.class));
    }

    public Optional<Integer> getProductsTotalCountByCategory(String category) throws DataAccessException {
        return Optional.of(jTemplate.queryForObject(SELECT_TOTAL_COUNT_BY_CATEGORY, Integer.class, category));
    }

    public Optional<Integer> getProductsTotalCountBySearch(String searchQuery) throws DataAccessException {
        return Optional
                .of(jTemplate.queryForObject(SELECT_TOTAL_COUNT_BY_SEARCH, Integer.class, "%" + searchQuery + "%"));
    }

}
