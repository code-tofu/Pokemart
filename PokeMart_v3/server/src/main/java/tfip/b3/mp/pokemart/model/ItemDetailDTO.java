package tfip.b3.mp.pokemart.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDetailDTO {

    private String productID;
    private String nameID;
    private String category;
    private double cost;
    private String description;
    private String productName;
    private int stock;
    private double discount;
    private String comments;
    private List<String> attributes;

    public static ItemDetailDTO ItemDetailFromDAOs(ProductDAO productDAO, InventoryDAO inventoryDAO, AttributeDAO attributeDAO){
        return new ItemDetailDTO(
            productDAO.getProductID(),
            productDAO.getNameID(),
            productDAO.getCategory(),
            productDAO.getCost(),
            productDAO.getDescription(),
            productDAO.getProductName(),
            inventoryDAO.getStock(),
            inventoryDAO.getDiscount(),
            inventoryDAO.getComments(),
            attributeDAO.getAttributes()
        );
    }

}
