package tfip.b3.mp.pokemart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDAO {

    private String productID;
    private int apiID;
    private String nameID;
    private String category;
    private double cost;
    private String description;
    private String productName;
    
}
