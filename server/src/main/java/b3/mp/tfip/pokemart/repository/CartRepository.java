package b3.mp.tfip.pokemart.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {

    @Autowired
    @Qualifier("Cart")
    RedisTemplate<String, String> rTemplate;

    public void insertNewCartItem(String userID, String productString, int quantity) {

        rTemplate.opsForHash().put(userID, productString, Integer.toString(quantity));
    }

    public long deleteCartItem(String userID, String productID) {
        return rTemplate.opsForHash().delete(userID, productID);
        // NOTE: del returns the number of fields that were removed
    }

    public void incrCartItem(String userID, String productString) {
        rTemplate.opsForHash().increment(userID, productString, 1); // NOTE: incr returns value at field after increment
    }

    public void decrCartItem(String userID, String productString) {
        rTemplate.opsForHash().increment(userID, productString, -1);
    }

    public int getCartSize(String userID) {
        return rTemplate.opsForHash().size(userID).intValue();
    }

    public Map<String, Integer> getFullCart(String userID) {
        Map<String, Integer> cart = new HashMap<>();
        Cursor<Map.Entry<Object, Object>> cursor = rTemplate.opsForHash().scan(userID,
                ScanOptions.NONE);
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            cart.put(entry.getKey().toString(), Integer.parseInt(entry.getValue().toString()));
        }
        System.out.println(">>[INFO} Cart for User " + userID + ":" + cart);
        return cart;
    }

}
