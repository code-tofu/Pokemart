export interface CartString {
  productID: string;
  nameID: string;
  productName: string;
}

export interface CartItem {
  productString: CartString;
  quantity: number;
}
