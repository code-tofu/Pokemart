import { Component, Input, Output, inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { CartItem, CartReq } from 'src/app/model/cart.model';
import { CartService } from 'src/app/services/cart.service';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'app-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.css']
})
export class CartItemComponent {
  @Input()
  cartItem!: CartItem;
  imgsrc!: string;
  cartSvc = inject(CartService);
  router = inject(Router);
  @Output()
  onEdit$ = new Subject<void>();

  quantity: FormControl = new FormControl<number>(0);

  ngOnInit():void{
    this.imgsrc = Utils.generateImgURL(this.cartItem.item.nameID);
    this.quantity.setValue(this.cartItem.quantity)
    this.quantity.setValidators([
      Validators.required,
      Validators.min(1),
      Validators.max(this.cartItem.item.stock),
    ]);
  }

  deleteItem() {
    this.cartSvc.deleteItem(this.cartItem.item.productID).subscribe(()=>this.onEdit$.next());
  }

  detailPage() {
    this.router.navigate(['/detail', this.cartItem.item.productID]);
  }
  
  increaseQty() {
    if (this.quantity!.value < this.cartItem.item.stock)
      this.quantity.setValue(+this.quantity!.value + 1);
  }

  decreaseQty() {
    if (this.quantity!.value > 1)
      this.quantity.setValue(+this.quantity!.value - 1);
  }

  addToCart() {
    const req: CartReq = {
      productID: this.cartItem.item.productID,
      quantity: +this.quantity.value,
    };
    this.cartSvc.addToCart(req).subscribe(()=>this.onEdit$.next())
  }

}
