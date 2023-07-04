import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, of, retry } from 'rxjs';
import { CatalogueItem, ItemDetail } from 'src/app/model/catalogue.model';
import { inventoryURL,productURL, itemsPerPage } from 'src/app/endpoint.constants';
import { CategoryCount } from '../model/count.model';
import { ErrorService } from './error.service';

@Injectable({
  providedIn: 'root'
})
export class CatalogueService {

  constructor() { }
  http = inject(HttpClient);
  error = inject(ErrorService)

  getCatalogue(page: Number): Observable<CatalogueItem[]> {
    console.info('>> [INFO]: Query Store Catalogue, Page:', page);
    let queryParams = new HttpParams().append('page', page.toString()).append('limit', itemsPerPage);
    return this.http.get<CatalogueItem[]>(inventoryURL + 'shop', {
      params: queryParams,
    }).pipe(catchError(err => this.error.httpArrErrorHandler(err)));
  }

  getTotalCountFullCatalogue(): Observable<number> {
    return this.http.get<number>(inventoryURL + 'productCount')
    .pipe(catchError(err => this.error.httpNumErrorHandler(err)));
  }

  getCataloguebyCategory(category: string,page: Number): Observable<CatalogueItem[]> {
    console.info('>> [INFO]: Query Store Catalogue by Category, Page:', page);
    let queryParams = new HttpParams().set('page', page.toString());
    return this.http.get<CatalogueItem[]>(
      inventoryURL + 'category/' + category,{
        params: queryParams,}
    ).pipe(catchError(err => this.error.httpArrErrorHandler(err)));
  }

  getTotalCountByCategory(category: string): Observable<number> {
    let queryParams = new HttpParams().append('category', category);
    return this.http.get<number>(inventoryURL + 'productCountByCategory', {
      params: queryParams,
    }).pipe(catchError(err => this.error.httpNumErrorHandler(err)));
  }

  getCataloguebySearch(searchquery: string,page: Number): Observable<CatalogueItem[]> {
    console.info('>> [INFO]: Query Store Catalogue by Search, Page:', page);
    let queryParams = new HttpParams().append('query', searchquery).append('page', page.toString());
    return this.http.get<CatalogueItem[]>(inventoryURL + 'search', {
      params: queryParams,
    }).pipe(catchError(err => this.error.httpArrErrorHandler(err)));
  }

  getTotalCountBySearch(searchquery: string): Observable<number> {
    let queryParams = new HttpParams().append('query', searchquery);
    return this.http.get<number>(inventoryURL + 'productCountBySearch', {
      params: queryParams,
    }).pipe(catchError(err => this.error.httpNumErrorHandler(err)));
  }

  getProductDetails(productID: String){
    return this.http.get<ItemDetail>(productURL + "detail/" + productID);
  }


  getCategories(page:number): Observable<CategoryCount[]> {
    let queryParams = new HttpParams().set('page', page.toString());
    return this.http.get<CategoryCount[]>(inventoryURL + 'category',{
      params: queryParams,});
  }

  getCategoryCount(): Observable<number> {
    return this.http.get<number>(inventoryURL + 'categoryCount');
  }

}
