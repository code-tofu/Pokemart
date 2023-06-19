package tfip.b3.mp.pokemart.utils;

import java.util.UUID;

public class Utils {

    public static String generateUUID(int numOfChar) {
        return UUID.randomUUID().toString().substring(0, numOfChar);
    }
    
}
