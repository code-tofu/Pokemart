import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import {
  CatalogueItem,
  CategoryCount,
  Product,
  Qty,
} from '../model/catalogue-item.model';
import { HttpClient } from '@angular/common/http';

const inventoryURL = 'api/inventory/';
const productURL = 'api/product/';

@Injectable({
  providedIn: 'root',
})
export class CatalogueService {
  constructor() {}
  http = inject(HttpClient);

  getCatalogue(offset: Number): Observable<CatalogueItem[]> {
    return this.http.get<CatalogueItem[]>(inventoryURL + 'storeMain');
  }

  getCataloguebyCategory(category: String): Observable<CatalogueItem[]> {
    return this.http.get<CatalogueItem[]>(
      inventoryURL + 'category/' + category
    );
  }

  getProduct(productID: String): Observable<Product> {
    return this.http.get<Product>(productURL + productID);
  }

  getCategories(): Observable<CategoryCount[]> {
    return this.http.get<CategoryCount[]>(inventoryURL + 'categoryMain');
  }

  getStock(productID: String): Observable<Qty> {
    return this.http.get<Qty>(inventoryURL + productID);
  }
}
