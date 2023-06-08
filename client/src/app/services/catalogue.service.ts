import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import {
  CatalogueItem,
  CategoryCount,
  Product,
  Stock,
} from '../model/catalogue.model';
import { HttpClient, HttpParams } from '@angular/common/http';

const inventoryURL = 'api/inventory/';
const productURL = 'api/product/';

@Injectable({
  providedIn: 'root',
})
export class CatalogueService {
  constructor() {}
  http = inject(HttpClient);

  getCatalogue(offset: Number): Observable<CatalogueItem[]> {
    return this.http.get<CatalogueItem[]>(inventoryURL + 'shopMain');
  }

  getCataloguebyCategory(category: string): Observable<CatalogueItem[]> {
    return this.http.get<CatalogueItem[]>(
      inventoryURL + 'category/' + category
    );
  }

  getCataloguebySearch(searchquery: string): Observable<CatalogueItem[]> {
    let queryParams = new HttpParams().append('query', searchquery);
    return this.http.get<CatalogueItem[]>(inventoryURL + 'search', {
      params: queryParams,
    });
  }

  getProduct(productID: String): Observable<Product> {
    return this.http.get<Product>(productURL + productID);
  }

  getCategories(): Observable<CategoryCount[]> {
    return this.http.get<CategoryCount[]>(inventoryURL + 'categoryMain');
  }

  getStock(productID: String): Observable<Stock> {
    return this.http.get<Stock>(inventoryURL + productID);
  }
}
