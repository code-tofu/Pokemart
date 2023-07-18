import { Component, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { OrderSummary } from 'src/app/model/order.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css'],
})
export class HistoryComponent {

  orderSvc = inject(OrderService);
  orderSummaries!: OrderSummary[];

  ngOnInit(): void {
    this.loadHistory()
  }

  loadHistory(){
    firstValueFrom(this.orderSvc.getOrderHistory())
    .then((resp) => {
      console.info('>> [INFO] Server Response:', resp);
      this.orderSummaries = resp;
    })
    .catch((err) => {
      alert('Connection Issue: Please Try Again Later');
      console.error('>> [ERROR] Server Error:', err);
    });
  }

  markDelivered(orderID: string) {
    firstValueFrom(this.orderSvc.markDelivered(orderID))
    .then(()=>this.loadHistory())
  }
}
