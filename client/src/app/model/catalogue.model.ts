export interface CatalogueItem {
  productID: string;
  nameID: string;
  productName: string;
  cost: number;
  stock: number;
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

export interface Stock {
  productID: string;
  quantity: number;
}
