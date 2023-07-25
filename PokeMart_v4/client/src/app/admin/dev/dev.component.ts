import { Component, inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { firstValueFrom } from 'rxjs';
import { DevService } from 'src/app/services/dev.service';

@Component({
  selector: 'app-dev',
  templateUrl: './dev.component.html',
  styleUrls: ['./dev.component.css'],
})
export class DevComponent {
  devSvc = inject(DevService);
  processingCreate: boolean = false;

  size: FormControl = new FormControl<number>(0, [
    Validators.required,
    Validators.min(1),
    Validators.max(99),
  ]);

  generateDefaultDB() {
    this.processingCreate = true;
    firstValueFrom(this.devSvc.getDefaultDB())
      .then((resp) => {
        alert(resp);
        this.processingCreate = false;
      })
      .catch((err) => {
        alert(err);
        this.processingCreate = false;
      });
  }

  generateDefaultInventory() {
    this.processingCreate = true;
    firstValueFrom(this.devSvc.getDefaultInventory())
      .then((resp) => {
        alert(JSON.stringify(resp));
        this.processingCreate = false;
      })
      .catch((err) => {
        alert(JSON.stringify(err));
        this.processingCreate = false;
      });
  }

  generateDefaultStore() {
    this.processingCreate = true;
    firstValueFrom(this.devSvc.getDefaultStores())
      .then((resp) => {
        alert(JSON.stringify(resp));
        this.processingCreate = false;
      })
      .catch((err) => {
        alert(JSON.stringify(err));
        this.processingCreate = false;
      });
  }

  generateSizedDB() {
    this.processingCreate = true;
    firstValueFrom(this.devSvc.getSizedDB(this.size.value))
      .then((resp) => {
        alert(JSON.stringify(resp));
        this.processingCreate = false;
      })
      .catch((err) => {
        alert(JSON.stringify(err));
        this.processingCreate = false;
      });
  }

  
}
