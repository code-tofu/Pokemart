import { Component, inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Observable, tap } from 'rxjs';
import { InventoryDetail } from 'src/app/model/sales.model';
import { SalesService } from 'src/app/services/sales.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent {
  name = new FormControl('');
  salesSvc = inject(SalesService);

  id: FormControl = new FormControl<string>('',  [Validators.required,Validators.minLength(8),Validators.maxLength(8)]);
  stock: FormControl = new FormControl<number>(0,[Validators.required,Validators.min(0), Validators.max(99)]);
  discount: FormControl = new FormControl<number>(0, [Validators.required,Validators.min(0), Validators.max(100)]);
  comment: FormControl = new FormControl<string>('',[Validators.required,Validators.maxLength(30)]);
  product!: Observable<InventoryDetail>; //change to observable
  productID!:string

  getInventoryDetails(){
    console.log("[INFO] Get Inventory Details:",this.id.value);
    this.product = this.salesSvc.getInventoryDetails("p"+this.id.value).pipe(tap((resp)=> this.productID = resp.productID));
  }
  updateStock(){
    this.salesSvc.updateStock(this.productID,this.stock.value).subscribe(()=>this.getInventoryDetails());
  }

  updateDiscount(){
    this.salesSvc.updateDiscount(this.productID,this.discount.value).subscribe(()=>this.getInventoryDetails());
  }

  updateComment(){
    this.salesSvc.updateComment(this.productID,this.comment.value).subscribe(()=>this.getInventoryDetails());
  }
}
