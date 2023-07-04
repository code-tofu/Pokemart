import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { UserService } from './user.service';
import { Order } from '../model/order.model';
import { orderURL } from '../endpoint.constants';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor() { }
  
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
  
}
