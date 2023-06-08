import { Component, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { CartItem } from 'src/app/model/cart.model';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-cart-main',
  templateUrl: './cart-main.component.html',
  styleUrls: ['./cart-main.component.css'],
})
export class CartMainComponent {
  cartSvc = inject(CartService);

  cart$!: Observable<CartItem[]>;

  ngOnInit(): void {
    this.cart$ = this.cartSvc.getFullCart();
  }
}
