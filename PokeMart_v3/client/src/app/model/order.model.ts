export interface Order {
  orderID: string;
  orderDate: number;
  customerID: string;
  customerName: string;
  customerPhone: string;
  customerEmail: string;
  shippingAddress: string;
  shippingType: string;
  shippingCost: number;
  subtotal: number;
  total: number;
  items: OrderItem[];
  delivered: boolean;
}

export interface OrderItem {
  productID: string;
  productName: string;
  cost: number;
  discount: number;
  quantity: number;
}

export interface DeliveryDetails {
  customerName: string;
  customerPhone: string;
  customerEmail: string;
  customerShippingAddress: string;
}

export interface OrderSummary {
  orderID: string;
  orderDate: Date;
  total: number;
  delivered:boolean;
}
