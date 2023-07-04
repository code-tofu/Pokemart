import { CatalogueItem } from "./catalogue.model";

export interface CartReq {
    productID: string;
    quantity: number;
  }
  
export interface CartItem{
    item: CatalogueItem;
    quantity: number;
}