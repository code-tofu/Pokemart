package b3.mp.tfip.pokemart.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDAO {

    private String orderID;
    private Date orderDate;
    private String customerID;
    private String customerName;
    private String customerPhone;
    private String shippingAddress;
    private List<ItemCountDAO> items;
    private double total;

}
