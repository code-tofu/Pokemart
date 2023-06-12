import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, firstValueFrom } from 'rxjs';
import { CatalogueItem, ItemCount } from 'src/app/model/catalogue.model';
import { Order } from 'src/app/model/order.model';
import { CartService } from 'src/app/services/cart.service';
import { OrderService } from 'src/app/services/order.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent implements OnInit {
  cartSvc = inject(CartService);
  userSvc = inject(UserService);
  orderSvc = inject(OrderService);
  router = inject(Router);
  fb = inject(FormBuilder);

  cart$!: Observable<CatalogueItem[]>;
  total!: number; //TODO:this.cartSvc.total?
  shipForm!: FormGroup;

  ngOnInit(): void {
    this.cart$ = this.cartSvc.getFullCart();
    console.info('>>[INFO] Loaded User', this.userSvc.user);
    this.shipForm = this.createShippingForm();
  }

  createShippingForm(): FormGroup {
    return this.fb.group({
      customerName: this.fb.control<string>(
        !!this.userSvc.user.customerName ? this.userSvc.user.customerName : '',
        [Validators.required, Validators.minLength(3)]
      ),
      customerEmail: this.fb.control<string>(
        !!this.userSvc.user.email ? this.userSvc.user.email : '',
        [Validators.required, Validators.email]
      ),
      customerPhone: this.fb.control<string>(
        !!this.userSvc.user.phone ? this.userSvc.user.phone : '',
        [Validators.required, Validators.minLength(8)]
      ),
      customerShippingAddress: this.fb.control<string>(
        !!this.userSvc.user.shippingAddress
          ? this.userSvc.user.shippingAddress
          : '',
        [Validators.required]
      ),
    });
  }

  createOrder() {
    let cartSummary!: ItemCount[];
    let newOrder!: Order;

    firstValueFrom(this.cartSvc.getFullCartSummary())
      .then((resp) => {
        console.info('>> [INFO] Server Response:', resp);
        cartSummary = resp;
        newOrder = {
          orderID: undefined,
          orderDate: undefined,
          customerID: this.userSvc.user.customerID,
          customerName: this.userSvc.user.customerName,
          customerPhone: this.userSvc.user.customerName,
          shippingAddress: this.userSvc.user.shippingAddress,
          items: cartSummary,
          total: this.cartSvc.total,
        };

        firstValueFrom(this.orderSvc.postOrder(newOrder))
          .then((resp) => {
            console.info('>> [INFO] Server Response:', resp);
            alert('Your Order is being processed');
            this.router.navigate(['/order', resp.orderID]);
          })
          .catch((err) => {
            alert('Connection Issue: Please Try Again Later');
            console.error('>> [ERROR] Server Error:', err);
          });
      })
      .catch((err) => {
        alert('Connection Issue: Please Try Again Later');
        console.error('>> [ERROR] Server Error:', err);
      });
  }
}
