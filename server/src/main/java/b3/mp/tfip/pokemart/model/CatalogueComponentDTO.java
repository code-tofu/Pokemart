package b3.mp.tfip.pokemart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogueComponentDTO {

    private String productID;
    private String nameID;
    private String productName;
    private double cost;
    private int quantity; // quantity can be either inventory stock or quantity purchased
    private double discount;
    private double deduct;

}
