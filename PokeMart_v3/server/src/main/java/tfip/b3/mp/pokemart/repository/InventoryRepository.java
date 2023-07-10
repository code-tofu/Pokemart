package tfip.b3.mp.pokemart.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import tfip.b3.mp.pokemart.model.CatalogueItemDTO;
import tfip.b3.mp.pokemart.model.InventoryDAO;

import static tfip.b3.mp.pokemart.repository.InventoryQueries.*;

import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

    //TODO: REWRITE WITH TRY CATCH FOR EmptyResultDataAccessException

@Repository
public class InventoryRepository {

    @Autowired
    JdbcTemplate jTemplate;

    public boolean insertInventoryItem(String productID, int quantity) {
        try {
            jTemplate.update(INSERT_NEW_INVENTORY_ITEM, productID, quantity);
            return true;
        } catch (DataAccessException daErr) {
            System.out.println(">> [ERROR] " + daErr);
            System.out.println(">> [WARNING] Insert Inventory: Existing Item Already Exists");
            return false;
        }
    }

    public boolean changeInventoryStock(String productID, int delta) throws DataAccessException{
        Optional<Integer> currentStock = getStockofProduct(productID);
        if(currentStock.isEmpty()){
            System.out.println(" >> [WARNING] Update Stock: Item Does Not Exist");
            return false;
        }
        jTemplate.update(UPDATE_INVENTORY_ITEM_STOCK,currentStock.get()-delta,productID);
        return true;
    }

    public List<CatalogueItemDTO> getShopCatalogue(int limit, int offset) throws DataAccessException {
        return jTemplate.query(SELECT_CATALOGUE_ITEM_WLIMIT, new CatalogueItemDTOMapper(), limit, offset);
    }

    public CatalogueItemDTO getCatalogueItemByID(String productID) throws DataAccessException {
        return jTemplate.queryForObject(SELECT_CATALOGUE_ITEM_BY_ID, new CatalogueItemDTOMapper(), productID);
    }

    public Map<String, Integer> getAllInventoryCategories(int limit, int offset) throws DataAccessException {
        SqlRowSet rs = jTemplate.queryForRowSet(SELECT_ALL_CATEGORIES_IN_INVENTORY, limit, offset);
        Map<String, Integer> queryMap = new TreeMap<>();
        while (rs.next()) {
            queryMap.put(rs.getString("category"), rs.getInt("count"));
        }
        return queryMap;
    }

    public List<CatalogueItemDTO> getCatalogueByCategory(String category, int limit, int offset)
            throws DataAccessException {
        return jTemplate.query(SELECT_INVENTORY_BY_CATEGORY_WLIMIT, new CatalogueItemDTOMapper(), category,limit, offset);
    }

    
    public List<CatalogueItemDTO> getCatalogueBySearch(String searchQuery, int limit, int offset)
            throws DataAccessException {
        return jTemplate.query(SELECT_INVENTORY_BY_SEARCH_WLIMIT, new CatalogueItemDTOMapper(),
                "%" + searchQuery + "%", limit, offset);
    }

    public InventoryDAO getInventoryDetailsofProduct(String productID) throws DataAccessException {
        return jTemplate.queryForObject(SELECT_INVENTORY_ITEM_DETAILS_BY_ID, new InventoryDAOMapper(), productID);
    }

    public Optional<Integer> getStockofProduct(String productID) throws DataAccessException {
        SqlRowSet rs = jTemplate.queryForRowSet(SELECT_STOCK_OF_PRODUCT, productID);
        if (rs.next()) {
            return Optional.of(rs.getInt("stock"));
        }
        return Optional.empty();
    }


    //FIXME: FIX NULL ISSUES
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

    public Optional<Integer> getCategoryCount() throws DataAccessException {
        return Optional.of(jTemplate.queryForObject(SELECT_TOTAL_COUNT_CATEGORIES, Integer.class));
    }


    public boolean checkProductExistsInInventory(String productID){
        return jTemplate.queryForObject(EXISTS_INVENTORY_BY_PRODUCT_ID,Boolean.class, productID);
    }

    public int updateInventoryDiscount(String productID, Double editValue)throws DataAccessException{
        return jTemplate.update(UPDATE_INVENTORY_ITEM_DISCOUNT,editValue,productID);
    }

    public int updateInventoryComments(String productID, String comments)throws DataAccessException{
        return jTemplate.update(UPDATE_INVENTORY_ITEM_COMMENTS,comments,productID);
    }

    public int updateInventoryStock(String productID, int stock) throws DataAccessException{
        return jTemplate.update(UPDATE_INVENTORY_ITEM_STOCK, stock,productID);
    }

}
