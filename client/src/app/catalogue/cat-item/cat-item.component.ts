import { Component, Input, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { CatalogueItem } from 'src/app/model/catalogue-item.model';
import { CartService } from 'src/app/services/cart.service';

const imgURL: String =
  'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/';
const imgType: String = '.png';
const defaultqty:number = 1;

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

  ngOnInit(): void {
    this.imgsrc = imgURL + this.item.nameID + imgType;
  }

  detailPage() {
    this.router.navigate(['/detail', this.item.productID]);
  }

  //TODO: add quantity
  addToCart() {
    const productString = {
      productID: this.item.productID,
      nameID: this.item.nameID,
      productName: this.item.productName,
    };
    console.info(productString);
    firstValueFrom(this.cartSvc.addToCart(productString, defaultqty))
      .then((resp) => {
        this.router.navigate(['/cart']);
      })
      .catch((err) => {
        console.log(err);
      });
  }
}
