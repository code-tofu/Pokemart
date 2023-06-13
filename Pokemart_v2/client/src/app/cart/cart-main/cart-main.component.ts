import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, firstValueFrom } from 'rxjs';
import { CatalogueItem } from 'src/app/model/catalogue.model';
import { CartService } from 'src/app/services/cart.service';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-cart-main',
  templateUrl: './cart-main.component.html',
  styleUrls: ['./cart-main.component.css'],
})
export class CartMainComponent {
  cartSvc = inject(CartService);
  orderSvc = inject(OrderService);
  router = inject(Router);

  cart!: CatalogueItem[];

  ngOnInit(): void {
    firstValueFrom(this.cartSvc.getFullCart())
      .then((resp) => {
        this.cart = resp;
        this.cartSvc.total = this.calculateTotal();
      })
      .catch((err) => {
        alert('Connection Issue: Please Try Again Later');
        console.error('>> [ERROR] Server Error:', err);
      });
  }

  calculateTotal(): number {
    let calc = 0;
    this.cart.forEach((item) => (calc += item.quantity * item.cost));
    return calc;
  }

  checkout() {
    this.router.navigate(['/checkout']);
  }
}
