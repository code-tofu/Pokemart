import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterRequest } from 'src/app/model/auth.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  fb = inject(FormBuilder);
  userSvc=inject(UserService);
  signupForm!: FormGroup;

  ngOnInit(): void {
    this.signupForm = this.fb.group({
      username: this.fb.control<string>('', [Validators.required]),
      password: this.fb.control<string>('', [Validators.required]),
      customerName: this.fb.control<string>('', [Validators.required]),
      customerEmail: this.fb.control<string>('', [Validators.required,Validators.email]),
      customerPhone: this.fb.control<string>('', [Validators.required]),
      shippingAddress: this.fb.control<string>('', [Validators.required]),
      birthdate: this.fb.control<string>('', [Validators.required]),
      gender: this.fb.control<string>('', [Validators.required]),
    });
  }

  signup(){
    const signup:RegisterRequest={
      username: this.signupForm.value.username,
      password: this.signupForm.value.password,
      customerName: this.signupForm.value.customerName,
      customerEmail: this.signupForm.value.customerEmail,
      customerPhone: this.signupForm.value.customerPhone,
      shippingAddress: this.signupForm.value.shippingAddress,
      birthdate: (new Date(this.signupForm.value.birthdate)).getTime(),
      gender: this.signupForm.value.gender,
    }
    this.userSvc.signup(signup);
  }
}
