import { Component, OnInit, inject } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, firstValueFrom } from 'rxjs';
import { Product } from 'src/app/model/catalogue-item.model';
import { CartService } from 'src/app/services/cart.service';
import { CatalogueService } from 'src/app/services/catalogue.service';

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

  item!: Product;
  imgsrc!: String;
  quantity: number = 1;
  stock!: number;

  imgURL: String =
    'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/';
  imgType: String = '.png';

  ngOnInit(): void {
    firstValueFrom(
      this.catSvc.getProduct(this.actRoute.snapshot.params['productID'])
    )
      .then((resp) => {
        console.info(resp);
        this.item = resp;
      })
      .catch((err) => {
        console.log(err);
      });

    firstValueFrom(
      this.catSvc.getStock(this.actRoute.snapshot.params['productID'])
    )
      .then((resp) => {
        this.stock = resp.quantity;
      })
      .catch((err) => {
        console.log(err);
      });
  }

  increaseQty() {
    if (this.quantity < this.stock) this.quantity += 1;
  }

  decreaseQty() {
    if (this.quantity > 1) this.quantity -= 1;
  }

  addToCart() {
    const productString = {
      productID: this.item.productID,
      nameID: this.item.nameID,
      productName: this.item.productName,
    };
    console.info(productString);
    firstValueFrom(this.cartSvc.addToCart(productString, +this.quantity))
      .then((resp) => {
        this.router.navigate(['/cart']);
      })
      .catch((err) => {
        console.log(err);
      });
  }
}
