import { Injectable, OnInit, inject } from '@angular/core';
import { cartURL } from '../endpoint.constants';
import { HttpClient } from '@angular/common/http';
import { UserService } from './user.service';
import { Observable, catchError, of, tap } from 'rxjs';
import { CartItem, CartReq } from '../model/cart.model';
import { ErrorService } from './error.service';

@Injectable({
  providedIn: 'root'
})
export class CartService{

  constructor() { }
  http = inject(HttpClient);
  error = inject(ErrorService)
  userSvc = inject(UserService);
  total!:number;
  cart!: CartItem[];

  getCart():Observable<CartItem[]>{
    if(!this.userSvc.userID){
      console.info('>> [INFO] LoadCart:', 'User Not Signed In');
      return of([]);
    }
    console.info('>> [INFO] Get Cart User:',this.userSvc.userID);
    return this.http.get<CartItem[]>(cartURL + this.userSvc.userID)
    .pipe(tap((resp) => {
    this.cart = resp;
    this.total = this.calculateTotal();
    }))
    .pipe(catchError(err => this.error.httpArrErrorHandler(err)))
  }

  addToCart(req: CartReq): Observable<any> {
    console.info('>> [INFO] Add to Cart', req)
    return this.http.post<any>(
      cartURL + 'upsert/' + this.userSvc.userID,
      req
    ).pipe(catchError(err => this.error.httpArrErrorHandler(err)));
  }

  deleteItem(productID: string): Observable<any> {
    return this.http.delete<any>(
      cartURL + this.userSvc.userID+ '/' + productID
    ).pipe(catchError(err => this.error.httpArrErrorHandler(err)));
  }

  calculateTotal() {
    let calc = 0;
    this.cart.forEach((cartItem) => (calc += cartItem.quantity * (cartItem.item.cost-cartItem.item.discount)));
    return calc;
  }

}
