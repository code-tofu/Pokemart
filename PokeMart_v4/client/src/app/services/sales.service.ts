import { Injectable, inject } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { inventoryURL, salesURL } from '../endpoint.constants';
import { HttpClient } from '@angular/common/http';
import { InventoryDetail } from '../model/sales.model';
import { ErrorService } from './error.service';

@Injectable({
  providedIn: 'root'
})
export class SalesService {

  constructor() { }
  http = inject(HttpClient);
  error= inject(ErrorService);

  getInventoryDetails(productID:string):Observable<InventoryDetail>{
    return this.http.get<any>(inventoryURL + productID);
  }

  updateStock(productID: string, stock:number ):Observable<string>{
    return this.http.post<any>(salesURL + 'editStock/'+ productID, {"stock": stock}).pipe(catchError(err => this.error.httpStrErrorHandler(err)));
  }
 
  updateDiscount(productID: string, discount:number ):Observable<string>{
    return this.http.post<any>(salesURL + 'editDiscount/' + productID, {"discount":discount}).pipe(catchError(err => this.error.httpStrErrorHandler(err)));
  }

  updateComment(productID: string, comment:string ):Observable<string>{
    return this.http.post<any>(salesURL + 'editComments/' + productID, {"comments":comment}).pipe(catchError(err => this.error.httpStrErrorHandler(err)));;
  }

}
  