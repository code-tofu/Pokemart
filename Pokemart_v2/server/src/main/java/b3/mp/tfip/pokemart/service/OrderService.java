package b3.mp.tfip.pokemart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import b3.mp.tfip.pokemart.model.ItemCountDAO;
import b3.mp.tfip.pokemart.model.OrderDAO;
import b3.mp.tfip.pokemart.model.OrderSummaryDAO;
import b3.mp.tfip.pokemart.repository.CartRepository;
import b3.mp.tfip.pokemart.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    CartRepository cartRepo;

    // this should be transactional but mongo and redis do not have transactional?
    public OrderDAO createNewOrder(OrderDAO order) {
        for (ItemCountDAO item : order.getItems()) {
            cartRepo.deleteCartItem(order.getCustomerID(), item.getProductID());
        }
        return orderRepo.createNewOrder(order);
    }

    public OrderDAO getOrderbyOrderID(String orderID) {
        return orderRepo.getOrderbyOrderID(orderID);
    }

    public List<OrderSummaryDAO> getOrderSummaryByCustomerID(String customerID) {
        return orderRepo.getOrderSummaryByCustomerID(customerID);
    }
}
