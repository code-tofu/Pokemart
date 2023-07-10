package tfip.b3.mp.pokemart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreDAO {

    private int storeId;
    private String storeAddress;
    private String storeName;
    private String storePhone;
    private double storeLat;
    private double storeLng;
    
}
