export interface Customer {
  customerID: string;
  customerName: string;
  phone: string;
  email: string;
  shippingAddress: string;
}

export interface NewCustomer {
  customerID: string;
  customerName: string | undefined;
  phone: string | undefined;
  email: string | undefined;
  shippingAddress: string | undefined;
}
