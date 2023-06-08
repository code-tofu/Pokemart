import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { UserService } from './user.service';
import { CartItem, CartString } from '../model/cart-item.model';
import { Stock } from '../model/catalogue-item.model';

const cartURL = 'api/cart/';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  constructor() {}
  http = inject(HttpClient);
  userSvc = inject(UserService);

  addToCart(product: CartString, quantity: number): Observable<any> {
    let payload = {
      productString: this.toProductString(
        product.productID,
        product.nameID,
        product.productName
      ),
      quantity: quantity,
    };
    console.info;
    return this.http.post<any>(
      cartURL + 'upsert/' + this.userSvc.tempuser,
      payload
    );
  }

  toProductString(productId: string, nameID: string, productName: string): any {
    return productId + '|' + nameID + '|' + productName;
  }

  fromProductString(productString: string): CartString {
    const str: string[] = productString.split('|');
    return {
      productID: str[0],
      nameID: str[1],
      productName: str[2],
    };
  }

  getFullCart() {
    return this.http.get<Stock[]>(cartURL + this.userSvc.tempuser);
  }
}
