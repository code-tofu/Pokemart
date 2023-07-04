import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { of, retry } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor() { }

  httpErrorHandler(err: HttpErrorResponse) {
    console.error('>> [ERROR] Server Error:', err);
    alert('Connection Issue: Please Try Again Later');
  }
  httpArrErrorHandler(err: HttpErrorResponse){
    retry(2)
    console.error('>> [ERROR] Server Error:', err);
    alert('Connection Issue: Please Try Again Later');
    return of([]);
  }
  httpNumErrorHandler(err: HttpErrorResponse){
    retry(2)
    console.error('>> [ERROR] Server Error:', err);
    alert('Connection Issue: Please Try Again Later');
    return of(0);
  }
  httpStrErrorHandler(err: HttpErrorResponse){
    retry(2)
    console.error('>> [ERROR] Server Error:', err);
    alert('Connection Issue: Please Try Again Later');
    return of("");
  }

  // Type 'Observable<InventoryDetail | null>' is not assignable to type 'Observable<InventoryDetail>'.
  // Type 'InventoryDetail | null' is not assignable to type 'InventoryDetail'.
  // Type 'null' is not assignable to type 'InventoryDetail'.
  httpObjErrorHandler(err: HttpErrorResponse){
    retry(2)
    console.error('>> [ERROR] Server Error:', err);
    alert('Connection Issue: Please Try Again Later');
    return of(null);
  }
}
