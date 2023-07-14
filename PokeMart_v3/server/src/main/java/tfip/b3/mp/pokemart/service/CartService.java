package tfip.b3.mp.pokemart.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import tfip.b3.mp.pokemart.repository.CartRepository;


@Service
public class CartService {

    @Autowired
    CartRepository cartRepo;

    public void insertNewCartItem(String userID, String productString, int quantity) {
        System.out.println(userID);
        System.out.println(productString);
        System.out.println(quantity);
        cartRepo.insertNewCartItem(userID, productString, quantity);
    }

    public Map<String, Integer> getFullCart(String userID) {
        return cartRepo.getFullCart(userID);
    }

    public long deleteCartItem(String userID, String productID) {
        return cartRepo.deleteCartItem(userID, productID);
    }

}
