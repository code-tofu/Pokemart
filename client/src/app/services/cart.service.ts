import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { UserService } from './user.service';
import { CartItem, CartReq } from '../model/cart.model';
import { Observable } from 'rxjs';

const cartURL = 'api/cart/';
@Injectable({
  providedIn: 'root',
})
export class CartService {
  constructor() {}
  http = inject(HttpClient);
  userSvc = inject(UserService);

  getFullCart() {
    return this.http.get<CartItem[]>(cartURL + this.userSvc.tempuser);
  }

  addToCart(req: CartReq): Observable<any> {
    return this.http.post<any>(
      cartURL + 'upsert/' + this.userSvc.tempuser,
      req
    );
  }
}
