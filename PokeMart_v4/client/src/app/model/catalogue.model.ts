export interface CatalogueItem {
    productID: string;
    nameID: string;
    productName: string;
    cost: number;
    stock: number;
    discount: number;
    comments:string;
  }
  
export interface ItemDetail {
  productID: string;
  nameID: string;
  category: string;
  cost: number;
  description: string;
  productName: string;
  stock:number;
  discount:number;
  comments:string;
  attributes:string[];
}