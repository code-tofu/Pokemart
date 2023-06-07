package b3.mp.tfip.pokemart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import b3.mp.tfip.pokemart.model.ProductDAO;
import b3.mp.tfip.pokemart.repository.ProductRepo;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public Optional<ProductDAO> getProductByProductID(String productID) throws DataAccessException {
        return productRepo.getProductByProductID(productID);
    }

    public Optional<ProductDAO> getProductByApiID(int apiID) throws DataAccessException {
        return productRepo.getProductByApiID(apiID);
    }

    public List<String> getAllProductIDs(int limit, int offset) throws DataAccessException {
        return productRepo.getAllProductIDs(limit, offset);
    }

}
