package b3.mp.tfip.pokemart.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import b3.mp.tfip.pokemart.repository.CartRepo;

@Service
public class CartService {

    @Autowired
    CartRepo cartRepo;

    public void insertNewCartItem(String userID, String productString, int quantity) {
        System.out.println(userID);
        System.out.println(productString);
        System.out.println(quantity);
        cartRepo.insertNewCartItem(userID, productString, quantity);
    }

    public Map<String, Integer> getFullCart(String userID) {
        return cartRepo.getFullCart(userID);
    }

}
