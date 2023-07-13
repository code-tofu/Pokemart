package tfip.b3.mp.pokemart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDTO {

    private String productID;
    private String productName;
    private double cost;
    private double discount;
    private int quantity;
}
