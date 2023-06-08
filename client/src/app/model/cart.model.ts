import { CatalogueItem } from "./catalogue.model";

export interface CartReq {
  productID: string;
  quantity: number;
}

export interface CartItem{
  product: CatalogueItem;
  quantity: number;
}
