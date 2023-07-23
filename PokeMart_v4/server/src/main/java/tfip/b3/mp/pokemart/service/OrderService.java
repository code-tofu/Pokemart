package tfip.b3.mp.pokemart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tfip.b3.mp.pokemart.model.OrderDAO;
import tfip.b3.mp.pokemart.model.OrderItemDTO;
import tfip.b3.mp.pokemart.model.OrderSummaryDAO;
import tfip.b3.mp.pokemart.repository.CartRepository;
import tfip.b3.mp.pokemart.repository.InventoryRepository;
import tfip.b3.mp.pokemart.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    CartRepository cartRepo;

    @Autowired
    InventoryRepository invRepo;

    public OrderDAO createNewOrder(OrderDAO order) {
        for (OrderItemDTO item : order.getItems()) {
            cartRepo.deleteCartItem(order.getCustomerID(), item.getProductID());
            Optional<Integer> currentStock = invRepo.getStockofProduct(item.getProductID());
            if (currentStock.isPresent()) {
                invRepo.updateInventoryStock(item.getProductID(), currentStock.get() - item.getQuantity());
                System.out.println(">> [INFO] Updated Stock:" + item.getProductID() + " | Stock:"
                        + (currentStock.get() - item.getQuantity()));
            }

        }
        return orderRepo.createNewOrder(order);
    }

    public OrderDAO getOrderbyOrderID(String orderID) {
        return orderRepo.getOrderbyOrderID(orderID);
    }

    public List<OrderSummaryDAO> getOrderSummaryByCustomerID(String customerID) {
        return orderRepo.getOrderSummaryByCustomerID(customerID);
    }

    public boolean markOrderDelivered(String orderId) {
        return orderRepo.markOrderDelivered(orderId);
    }

}
