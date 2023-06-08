import { Component, OnInit, inject } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, firstValueFrom } from 'rxjs';
import { Product } from 'src/app/model/catalogue-item.model';
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
  cartSvc = inject(CartService);
  fb = inject(FormBuilder);

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
        this.imgsrc = imgURL + this.item.nameID + imgType;
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

  quantity: FormControl = new FormControl<number>(1);
  increaseQty() {
    if (this.quantity!.value < this.stock)
      this.quantity.setValue(this.quantity!.value + 1);
  }

  decreaseQty() {
    if (this.quantity!.value > 1)
      this.quantity.setValue(this.quantity!.value - 1);
  }

  addToCart() {
    const productString = {
      productID: this.item.productID,
      nameID: this.item.nameID,
      productName: this.item.productName,
    };
    console.info(productString);
    firstValueFrom(this.cartSvc.addToCart(productString, this.quantity!.value))
      .then(() => {
        this.router.navigate(['/cart']);
      })
      .catch((err) => {
        alert('Connection Issue: Please Try Again Later');
        console.log(err);
      });
  }
}
