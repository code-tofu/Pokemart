import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
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
    this.order$ = this.orderSvc.getOrderByID(
      this.actRoute.snapshot.params['orderID']
    );
  }

}
