package b3.mp.tfip.pokemart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDAO {

    private String productID;
    private int quantity;
    private double discount;
    private double deduction;

}
