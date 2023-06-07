import { Component, OnInit, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { CartItem, Qty } from 'src/app/model/catalogue-item.model';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-cart-main',
  templateUrl: './cart-main.component.html',
  styleUrls: ['./cart-main.component.css'],
})
export class CartMainComponent implements OnInit {
  cartSvc = inject(CartService);
  cart: CartItem[] = [];

  ngOnInit(): void {
    let respArr: Qty[] = [];
    firstValueFrom(this.cartSvc.getFullCart())
      .then((resp) => {
        respArr = resp;
        respArr.forEach((qty) => {
          console.info(qty);
          this.cart.push({
            productString: this.cartSvc.fromProductString(
              JSON.parse(JSON.stringify(qty))['productString']
            ),
            quantity: qty.quantity,
          });
        });
      })
      .catch((err) => {
        console.log(err);
      });
  }
}
