import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import {
  CatalogueItem,
  CategoryCount,
  Product,
  ItemCount,
} from '../model/catalogue.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { query } from '@angular/animations';

const inventoryURL = 'api/inventory/';
const productURL = 'api/product/';

@Injectable({
  providedIn: 'root',
})
export class CatalogueService {
  constructor() {}
  http = inject(HttpClient);

  getCatalogue(page: Number): Observable<CatalogueItem[]> {
    console.info('>> [INFO]: Query Store Main Page`:', page);
    let queryParams = new HttpParams().set('page', page.toString());
    return this.http.get<CatalogueItem[]>(inventoryURL + 'shopMain', {
      params: queryParams,
    });
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

  getStock(productID: String): Observable<ItemCount> {
    return this.http.get<ItemCount>(inventoryURL + productID);
  }

  getTotalCountAllProducts(): Observable<number> {
    return this.http.get<number>(inventoryURL + 'count');
  }

  getTotalCountBySearch(searchquery: string): Observable<number> {
    let queryParams = new HttpParams().append('query', searchquery);
    return this.http.get<number>(inventoryURL + 'countSearch', {
      params: queryParams,
    });
  }

  getTotalCountByCategory(category: string): Observable<number> {
    let queryParams = new HttpParams().append('category', category);
    return this.http.get<number>(inventoryURL + 'countCategory', {
      params: queryParams,
    });
  }
}
