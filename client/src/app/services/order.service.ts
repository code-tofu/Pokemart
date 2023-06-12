import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Order, OrderSummary } from '../model/order.model';
import { Observable } from 'rxjs';
import { UserService } from './user.service';

const orderURL = 'api/order/';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  constructor() {}

  http = inject(HttpClient);
  userSvc = inject(UserService);

  postOrder(order: Order): Observable<any> {
    console.info('>>[INFO] Posting Order:', order);
    return this.http.post<any>(orderURL + 'newOrder', order);
  }

  getOrderByID(orderID: string): Observable<Order> {
    console.info('>>[INFO] Retrieve Order:', orderID);
    return this.http.get<Order>(orderURL + orderID);
  }

  getOrderHistory(): Observable<OrderSummary[]> {
    console.info(
      '>>[INFO] Retrieve History of User:',
      this.userSvc.user.customerID
    );
    return this.http.get<OrderSummary[]>(
      orderURL + 'history/' + this.userSvc.user.customerID
    );
  }
}
