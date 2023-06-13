import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { UserService } from './user.service';
import { CartReq } from '../model/cart.model';
import { Observable } from 'rxjs';
import { CatalogueItem, ItemCount } from '../model/catalogue.model';

const cartURL = 'api/cart/';
@Injectable({
  providedIn: 'root',
})
export class CartService {
  total!: number;
  constructor() {}
  http = inject(HttpClient);
  userSvc = inject(UserService);

  getFullCart() {
    console.info('>> [INFO] ', 'Get User Cart from Server');
    return this.http.get<CatalogueItem[]>(
      cartURL + this.userSvc.user.customerID
    );
  }

  getFullCartSummary() {
    console.info('>> [INFO] ', 'Get User Cart Summary from Server');
    return this.http.get<ItemCount[]>(
      cartURL + 'summary/' + this.userSvc.user.customerID
    );
  }

  addToCart(req: CartReq): Observable<any> {
    return this.http.post<any>(
      cartURL + 'upsert/' + this.userSvc.user.customerID,
      req
    );
  }

  deleteItem(productID: string): Observable<any> {
    return this.http.delete<CatalogueItem[]>(
      cartURL + this.userSvc.user.customerID + '/' + productID
    );
  }
}
