import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { ErrorService } from './error.service';
import { devURL } from '../endpoint.constants';
import { catchError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DevService {

  http = inject(HttpClient);
  error = inject(ErrorService);


  getSizedDB(size:number){
    let queryParams = new HttpParams().append('size', size.toString());
    return this.http.get<any>(devURL + 'create/productDB', {
      params: queryParams,
    })
  }

  getDefaultDB(){
    return this.http.get<any>(devURL + 'create/productDB')
  }
  
  getDefaultInventory(){
    return this.http.get<any>(devURL + 'create/inventory')
  }

  getDefaultStores(){
    return this.http.get<any>(devURL + 'create/stores')
  }
  
}
