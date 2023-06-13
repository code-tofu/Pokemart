import { Component, Input, OnInit, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { CartReq } from 'src/app/model/cart.model';
import { CatalogueItem } from 'src/app/model/catalogue.model';
import { CartService } from 'src/app/services/cart.service';

const imgURL: String =
  'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/';
const imgType: String = '.png';

@Component({
  selector: 'app-cat-item',
  templateUrl: './cat-item.component.html',
  styleUrls: ['./cat-item.component.css'],
})
export class CatItemComponent implements OnInit {
  @Input()
  item!: CatalogueItem;
  imgsrc!: String;

  router = inject(Router);
  fb = inject(FormBuilder);
  cartSvc = inject(CartService);
  quantity: FormControl = new FormControl<number>(1);

  ngOnInit(): void {
    if (!(this.item.nameID.includes('tm') || this.item.nameID.includes('hm'))) {
      this.imgsrc = imgURL + this.item.nameID + imgType;
    } else {
      this.imgsrc = '/api/img/tm.png';
    }
    this.quantity.setValidators([
      Validators.required,
      Validators.min(1),
      Validators.max(this.item.quantity),
    ]);
  }

  detailPage() {
    this.router.navigate(['/detail', this.item.productID]);
  }

  increaseQty() {
    if (this.quantity!.value < this.item.quantity)
      this.quantity.setValue(+this.quantity!.value + 1);
  }

  decreaseQty() {
    if (this.quantity!.value > 1)
      this.quantity.setValue(+this.quantity!.value - 1);
  }

  addToCart() {
    const req: CartReq = {
      productID: this.item.productID,
      quantity: this.quantity.value,
    };
    firstValueFrom(this.cartSvc.addToCart(req))
      .then((resp) => {
        console.info('>> [INFO] Server Response:', resp);
        this.router.navigate(['/cart']);
      })
      .catch((err) => {
        alert('Connection Issue: Please Try Again Later');
        console.error('>> [ERROR] Server Error:', err);
      });
  }
}
