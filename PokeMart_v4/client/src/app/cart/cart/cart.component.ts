import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, firstValueFrom } from 'rxjs';
import { CartItem } from 'src/app/model/cart.model';
import { CatalogueItem } from 'src/app/model/catalogue.model';
import { CartService } from 'src/app/services/cart.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  cartSvc = inject(CartService);
  userSvc = inject(UserService);
  router = inject(Router);
  cart$!: Observable<CartItem[]>;
  

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(){
    this.cart$ = this.cartSvc.getCart()
  }

  checkout() {
    this.router.navigate(['/checkout']);
  }

}
