import {
  Component,
  OnChanges,
  OnInit,
  SimpleChanges,
  inject,
} from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable, firstValueFrom } from 'rxjs';
import { Product } from 'src/app/model/catalogue-item.model';
import { CatalogueService } from 'src/app/services/catalogue.service';

@Component({
  selector: 'app-item-detail',
  templateUrl: './item-detail.component.html',
  styleUrls: ['./item-detail.component.css'],
})
export class ItemDetailComponent implements OnInit {
  actRoute = inject(ActivatedRoute);
  catSvc = inject(CatalogueService);

  item$!: Observable<Product>;
  imgsrc!: String;
  quantity: number = 1;
  stock!: number;

  imgURL: String =
    'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/';
  imgType: String = '.png';

  ngOnInit(): void {
    this.item$ = this.catSvc.getProduct(
      this.actRoute.snapshot.params['productID']
    );
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
}
