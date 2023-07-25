import { Injectable, inject } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { adminURL, inventoryURL, salesURL } from '../endpoint.constants';
import { HttpClient } from '@angular/common/http';
import { InventoryDetail } from '../model/sales.model';
import { ErrorService } from './error.service';

@Injectable({
  providedIn: 'root',
})
export class SalesService {
  constructor() {}
  http = inject(HttpClient);
  error = inject(ErrorService);

  getInventoryDetails(productID: string): Observable<InventoryDetail> {
    return this.http.get<any>(inventoryURL + productID);
  }

  updateStock(productID: string, stock: number): Observable<string> {
    return this.http
      .post<any>(salesURL + 'editStock/' + productID, { stock: stock })
      .pipe(catchError((err) => this.error.httpStrErrorHandler(err)));
  }

  updateDiscount(productID: string, discount: number): Observable<string> {
    return this.http
      .post<any>(salesURL + 'editDiscount/' + productID, { discount: discount })
      .pipe(catchError((err) => this.error.httpStrErrorHandler(err)));
  }

  updateComment(productID: string, comment: string): Observable<string> {
    return this.http
      .post<any>(salesURL + 'editComments/' + productID, { comments: comment })
      .pipe(catchError((err) => this.error.httpStrErrorHandler(err)));
  }

  uploadNewProduct(itemDetails: string, image: File): Observable<any> {
    console.info('>> [INFO] New Product Upload', itemDetails, image);
    const formData = new FormData();
    formData.set('itemDetails', itemDetails);
    formData.set('image', image);
    return this.http.post<any>(adminURL + 'upload', formData);
  }
}
