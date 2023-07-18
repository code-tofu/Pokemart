package tfip.b3.mp.pokemart.model;

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
    private String customerEmail;
    private String shippingAddress;
    private ShippingType shippingType;
    private double subtotal;
    private double shippingCost;
    private double total;
    private List<OrderItemDTO> items;
    // private List<VoucherDTO> vouchers;
    private boolean delivered; //primitive initialises as false

}