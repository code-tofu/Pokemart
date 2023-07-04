package tfip.b3.mp.pokemart.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import tfip.b3.mp.pokemart.model.CatalogueItemDTO;
import tfip.b3.mp.pokemart.model.InventoryDAO;
import tfip.b3.mp.pokemart.repository.InventoryRepository;



@Service
public class InventoryService {

    @Autowired
    InventoryRepository invRepo;

    public List<CatalogueItemDTO> getShopCatalogue(int limit, int offset) throws DataAccessException {
        return invRepo.getShopCatalogue(limit, offset);
    }

    public CatalogueItemDTO getCatalogueItemByID(String productID) throws DataAccessException {
        return invRepo.getCatalogueItemByID(productID);
    }

    public Map<String, Integer> getAllInventoryCategories(int limit, int offset) throws DataAccessException {
        return invRepo.getAllInventoryCategories(limit, offset);
    }

    public List<CatalogueItemDTO> getCatalogueByCategory(String category, int limit, int offset) {
        return invRepo.getCatalogueByCategory(category, limit, offset);
    }

    public Optional<Integer> getStockofProduct(String productID) throws DataAccessException {
        return invRepo.getStockofProduct(productID);
    }

    public List<CatalogueItemDTO> getCatalogueBySearch(String searchQuery, int limit, int offset) {
        return invRepo.getCatalogueBySearch(searchQuery, limit, offset);
    }

    public Optional<Integer> getProductsTotalCount() throws DataAccessException {
        return invRepo.getProductsTotalCount();
    }

    public Optional<Integer> getProductsTotalCountByCategory(String category) throws DataAccessException {
        return invRepo.getProductsTotalCountByCategory(category);
    }

    public Optional<Integer> getProductsTotalCountBySearch(String search) throws DataAccessException {
        return invRepo.getProductsTotalCountBySearch(search);
    }

    public Optional<Integer> getCategoryCount() throws DataAccessException
    {
        return invRepo.getCategoryCount();
    }

    public InventoryDAO getInventoryDetailsofProduct(String productID) throws DataAccessException{
        return invRepo.getInventoryDetailsofProduct(productID);
    }

    public int updateInventoryDiscount(String productID, Double editValue){
        return invRepo.updateInventoryDiscount(productID, editValue);
    }

    public int updateInventoryComments(String productID, String comments){
        return invRepo.updateInventoryComments(productID, comments);
    }

    public int updateInventoryStock(String productID, int stock){
        return invRepo.updateInventoryStock(productID, stock);
    }
    

}