import { Component, Input, OnInit, inject } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { CatalogueItem } from 'src/app/model/catalogue-item.model';
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
  cartSvc = inject(CartService);
  fb = inject(FormBuilder);
  quantity: FormControl = new FormControl<number>(1);

  ngOnInit(): void {
    this.imgsrc = imgURL + this.item.nameID + imgType;
    this.quantity.setValidators([
      Validators.required,
      Validators.min(1),
      Validators.max(this.item.stock),
    ]);
  }

  detailPage() {
    this.router.navigate(['/detail', this.item.productID]);
  }

  increaseQty() {
    if (this.quantity!.value < this.item.stock)
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
      .then((resp) => {
        this.router.navigate(['/cart']);
      })
      .catch((err) => {
        console.log(err);
      });
  }
}
