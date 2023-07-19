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
  editingProfile:boolean = false;

  ngOnInit(): void {
    const birthdate = new Date(this.userSvc.user!.birthdate);
    const birthdateStr = birthdate.getDay() + "/" + (birthdate.getMonth()+1) + "/" + birthdate.getFullYear();
    this.profileForm = this.fb.group({
      customerName: this.fb.control<string>(!!this.userSvc.user!.customerName ? this.userSvc.user!.customerName : '', [Validators.required]),
      customerEmail: this.fb.control<string>(!!this.userSvc.user!.customerEmail ? this.userSvc.user!.customerEmail : '', [Validators.required,Validators.email]),
      customerPhone: this.fb.control<string>(!!this.userSvc.user!.customerPhone ? this.userSvc.user!.customerPhone : '', [Validators.required]),
      shippingAddress: this.fb.control<string>(!!this.userSvc.user!.shippingAddress ? this.userSvc.user!.shippingAddress : '', [Validators.required]),
      birthdate: this.fb.control<string>(!!this.userSvc.user!.birthdate ? birthdateStr : '', [Validators.required]),
      gender: this.fb.control<string>(!!this.userSvc.user!.gender ? this.userSvc.user!.gender: " ", [Validators.required]),
    });
  }

  editProfile(){
    this.editingProfile=true;
  }
}
