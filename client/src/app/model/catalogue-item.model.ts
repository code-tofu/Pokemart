import { CartMainComponent } from '../cart/cart-main/cart-main.component';

export interface CatalogueItem {
  productID: string;
  nameID: string;
  productName: string;
  cost: number;
  quantity: number;
  discount: number;
  deduct: number;
}

export interface Product {
  productID: string;
  apiID: number;
  nameID: string;
  category: string;
  cost: number;
  details: string;
  productName: string;
}

export interface CategoryCount {
  category: string;
  count: number;
}

export interface Qty {
  productID: string;
  quantity: number;
}

export interface CartString {
  productID: string;
  nameID: string;
  productName: string;
}

export interface CartItem {
  productString: CartString;
  quantity: number;
}
