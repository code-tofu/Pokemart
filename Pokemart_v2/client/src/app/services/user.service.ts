import { Injectable } from '@angular/core';
import { Customer, NewCustomer } from '../model/user.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  user: Customer = {
    customerID: 'uA0001',
    customerName: 'Testerman',
    phone: '98765432',
    email: 'mailtest@testmail.com',
    shippingAddress: '21 Jump Street Beverly Hills 90210',
  };
  testuser: NewCustomer = {
    customerID: 'uA0001',
    customerName: undefined,
    phone: undefined,
    email: undefined,
    shippingAddress: undefined,
  };

  constructor() {}
}

//TODO: USERSERVICE
