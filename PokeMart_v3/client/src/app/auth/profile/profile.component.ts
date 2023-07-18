import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  profileForm!: FormGroup;
  fb = inject(FormBuilder);
  userSvc=inject(UserService);

  ngOnInit(): void {
    this.profileForm = this.fb.group({
      customerName: this.fb.control<string>(!!this.userSvc.user!.customerName ? this.userSvc.user!.customerName : '', [Validators.required]),
      customerEmail: this.fb.control<string>(!!this.userSvc.user!.customerEmail ? this.userSvc.user!.customerEmail : '', [Validators.required,Validators.email]),
      customerPhone: this.fb.control<string>(!!this.userSvc.user!.customerPhone ? this.userSvc.user!.customerPhone : '', [Validators.required]),
      shippingAddress: this.fb.control<string>(!!this.userSvc.user!.shippingAddress ? this.userSvc.user!.shippingAddress : '', [Validators.required]),
      birthdate: this.fb.control<Date>(!!this.userSvc.user!.birthdate ? new Date(this.userSvc.user!.birthdate) : new Date(), [Validators.required]),
      gender: this.fb.control<string>(!!this.userSvc.user!.gender ? this.userSvc.user!.gender: " ", [Validators.required]),
    });
  }
}
