import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { UserService } from './user.service';
import { DeliveryDetails, Order, OrderItem, OrderSummary } from '../model/order.model';
import { orderURL } from '../endpoint.constants';
import { CartItem } from '../model/cart.model';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  constructor() {}

  http = inject(HttpClient);
  userSvc = inject(UserService);

  createOrderItem(cartItem: CartItem): OrderItem {
    return {
      productID: cartItem.item.productID,
      productName: cartItem.item.productName,
      cost: cartItem.item.cost,
      discount: cartItem.item.discount,
      quantity: cartItem.quantity,
    };
  }

  postOrder(order: Order): Observable<any> {
    console.info('>>[INFO] Posting Order:', order);
    return this.http.post<any>(orderURL + 'newOrder', order);
  }

  getOrderByID(orderID: string): Observable<Order> {
    console.info('>>[INFO] Retrieve Order:', orderID);
    return this.http.get<Order>(orderURL + orderID);
  }

  generateOrder(cart: CartItem[],customerID :string, deliveryDetails:DeliveryDetails,shippingType:string,shippingCost:number,subtotal:number, total:number):Order {
    let items: OrderItem[] = [];
    cart.forEach((item) => {
      items.push(this.createOrderItem(item));
    });
    const newOrder:Order = {
      orderID: "",
      orderDate: 0,
      customerID: customerID,
      customerName: deliveryDetails.customerName ,
      customerPhone: deliveryDetails.customerPhone,
      customerEmail: deliveryDetails.customerEmail,
      shippingAddress: deliveryDetails.customerShippingAddress,
      shippingType: shippingType,
      shippingCost: shippingCost,
      subtotal: subtotal,
      total: total,
      items:items
    }
    return(newOrder)
  }

  getOrderHistory(): Observable<OrderSummary[]> {
    console.info(
      '>>[INFO] Retrieve History of User:',
      this.userSvc.user.userID
    );
    return this.http.get<OrderSummary[]>(
      orderURL + 'history/' + this.userSvc.user.userID
    );
  }


  


}
