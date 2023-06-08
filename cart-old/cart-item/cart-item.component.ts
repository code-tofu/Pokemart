import { Component, Input, OnInit, inject } from '@angular/core';
import { CartItem } from 'src/app/model/cart-item.model';

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
  item!: CartItem;
  imgsrc!: String;

  ngOnInit(): void {
    this.imgsrc = imgURL + this.item.productString.nameID + imgType;
  }
}
