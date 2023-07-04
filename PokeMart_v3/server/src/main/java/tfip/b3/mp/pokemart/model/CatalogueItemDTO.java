package tfip.b3.mp.pokemart.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogueItemDTO {

    private String productID;
    private String nameID;
    private String productName;
    private double cost;
    private int stock;
    private double discount;
    private String comments;

}

