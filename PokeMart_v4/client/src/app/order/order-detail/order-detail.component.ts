import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, firstValueFrom } from 'rxjs';
import { Order } from 'src/app/model/order.model';
import { OrderService } from 'src/app/services/order.service';


@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent {

  actRoute = inject(ActivatedRoute);
  orderSvc = inject(OrderService);
  order$!: Observable<Order>;

  ngOnInit(): void {
    this.loadOrderDetail()
  }

  loadOrderDetail(){
    this.order$ = this.orderSvc.getOrderByID(
      this.actRoute.snapshot.params['orderID']
    );
  }

  markDelivered(orderID: string) {
    firstValueFrom(this.orderSvc.markDelivered(orderID))
    .then(()=> this.loadOrderDetail())
  }

}
