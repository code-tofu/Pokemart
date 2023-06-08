package b3.mp.tfip.pokemart.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import b3.mp.tfip.pokemart.model.CatalogueComponentDTO;
import b3.mp.tfip.pokemart.repository.InventoryRepo;

@Service
public class InventoryService {

    @Autowired
    InventoryRepo invRepo;

    public List<CatalogueComponentDTO> getStoreComponentData(int limit, int offset) throws DataAccessException {
        return invRepo.getStoreComponentData(limit, offset);
    }

    public Map<String, Integer> getAllInventoryCategories() throws DataAccessException {
        return invRepo.getAllInventoryCategories();
    }

    public List<CatalogueComponentDTO> getStoreComponentDataByCategory(String category) {
        return invRepo.getStoreComponentDataByCategory(category);
    }

    public Optional<Integer> getStockofProduct(String productID) throws DataAccessException {
        return invRepo.getStockofProduct(productID);
    }

    public List<CatalogueComponentDTO> getStoreComponentDataBySearch(String searchQuery, int limit, int offset) {
        return invRepo.getStoreComponentDataBySearch(searchQuery, limit, offset);
    }

}