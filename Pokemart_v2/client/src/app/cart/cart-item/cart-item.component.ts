import { Component, Input, OnInit, inject } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { CatalogueItem } from 'src/app/model/catalogue.model';
import { CartService } from 'src/app/services/cart.service';

const imgURL: String =
  'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/';
const imgType: String = '.png';

@Component({
  selector: 'app-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.css'],
})
export class CartItemComponent implements OnInit {
  @Input()
  item!: CatalogueItem;
  @Input()
  index!: number;
  imgsrc!: string;
  cartSvc = inject(CartService);

  ngOnInit(): void {
    this.imgsrc = imgURL + this.item.nameID + imgType;
  }

  router = inject(Router);
  editPage() {
    this.router.navigate(['/detail', this.item.productID], {
      queryParams: { quantity: this.item.quantity },
    });
  }

  deleteItem() {
    firstValueFrom(this.cartSvc.deleteItem(this.item.productID))
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
