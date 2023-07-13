import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { ItemDetail } from '../model/catalogue.model';
import { CatalogueService } from '../services/catalogue.service';
import { ActivatedRoute, Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { FormControl, Validators } from '@angular/forms';
import { CartReq } from '../model/cart.model';
import { CartService } from '../services/cart.service';
import { Utils } from '../utils';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-item-detail',
  templateUrl: './item-detail.component.html',
  styleUrls: ['./item-detail.component.css'],
})
export class ItemDetailComponent {
  catalogueSvc = inject(CatalogueService);
  cartSvc = inject(CartService);
  actRoute = inject(ActivatedRoute);
  router = inject(Router);
  modalService = inject(NgbModal);
  
  item!: ItemDetail;
  imgsrc!: String;
  
  @ViewChild('content')
  content!: ElementRef;


  quantity: FormControl = new FormControl<number>(
    !!this.actRoute.snapshot.queryParams['quantity']
      ? this.actRoute.snapshot.queryParams['quantity']
      : 1
  );

  ngOnInit(): void {
    firstValueFrom(
      this.catalogueSvc.getProductDetails(
        this.actRoute.snapshot.params['productID']
      )
    )
      .then((resp) => {
        console.info(">> [INFO] Product Details: ", resp);
        this.item = resp;
        this.imgsrc = Utils.generateImgURL(this.item.nameID);
        this.quantity.setValidators([
          Validators.required,
          Validators.min(1),
          Validators.max(this.item.stock),
        ]);
      })
      .catch((err) => {
        this.catalogueSvc.error.httpObjErrorHandler(err);
      });

  }


  increaseQty() {
    if (this.quantity!.value < this.item.stock)
      this.quantity.setValue(+this.quantity!.value + 1);
  }

  decreaseQty() {
    if (this.quantity!.value > 1)
      this.quantity.setValue(+this.quantity!.value - 1);
  }

  addToCart() {
    const req: CartReq = {
      productID: this.item.productID,
      quantity: +this.quantity.value,
    };
    this.cartSvc.addToCart(req).subscribe()
    this.openVerticallyCentered(this.content)
  }

	openVerticallyCentered(content:any) {
		this.modalService.open(content, { centered: true });
	}

}
