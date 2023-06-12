import { ItemCount } from './catalogue.model';

export interface Order {
  orderID?: string;
  orderDate?: Date;
  customerID: string;
  customerName: string;
  customerPhone: string;
  shippingAddress: string;
  items: ItemCount[];
  total: number;
}

export interface OrderSummary {
  orderID: string;
  orderDate: Date;
  total: number;
}
