import { Component, OnInit, inject } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, firstValueFrom } from 'rxjs';
import { CartReq } from 'src/app/model/cart.model';
import { Product } from 'src/app/model/catalogue.model';
import { CartService } from 'src/app/services/cart.service';
import { CatalogueService } from 'src/app/services/catalogue.service';

const imgURL: String =
  'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/';
const imgType: String = '.png';

@Component({
  selector: 'app-item-detail',
  templateUrl: './item-detail.component.html',
  styleUrls: ['./item-detail.component.css'],
})
export class ItemDetailComponent implements OnInit {
  actRoute = inject(ActivatedRoute);
  catSvc = inject(CatalogueService);
  router = inject(Router);
  fb = inject(FormBuilder);
  cartSvc = inject(CartService);

  item!: Product;
  imgsrc!: String;
  stock!: number;

  ngOnInit(): void {
    firstValueFrom(
      this.catSvc.getProduct(this.actRoute.snapshot.params['productID'])
    )
      .then((resp) => {
        console.info(resp);
        this.item = resp;
        if (
          !(this.item.nameID.includes('tm') || this.item.nameID.includes('hm'))
        ) {
          this.imgsrc = imgURL + this.item.nameID + imgType;
        } else {
          this.imgsrc = '/api/img/tm.png';
        }
      })
      .catch((err) => {
        console.log(err);
      });

    firstValueFrom(
      this.catSvc.getStock(this.actRoute.snapshot.params['productID'])
    )
      .then((resp) => {
        this.stock = resp.quantity;
        this.quantity.setValidators([
          Validators.required,
          Validators.min(1),
          Validators.max(this.stock),
        ]);
      })
      .catch((err) => {
        console.log(err);
      });
  }

  quantity: FormControl = new FormControl<number>(
    !!this.actRoute.snapshot.queryParams['quantity']
      ? this.actRoute.snapshot.queryParams['quantity']
      : 1
  );
  increaseQty() {
    if (this.quantity!.value < this.stock)
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
