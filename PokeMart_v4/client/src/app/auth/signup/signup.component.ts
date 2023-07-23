import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { firstValueFrom } from 'rxjs';
import { RegisterRequest } from 'src/app/model/auth.model';
import { UserService } from 'src/app/services/user.service';

const validatePast = (ctrl: AbstractControl) => {
  const inputDate = new Date(ctrl.value).getTime();
  console.info('input', inputDate);
  const today = new Date().getTime();
  console.info('today', today);
  if (inputDate >= today) {
    //can set minimum age if required
    return { isFuture: true } as ValidationErrors;
  }
  return null;
};

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent {
  fb = inject(FormBuilder);
  userSvc = inject(UserService);
  signupForm!: FormGroup;

  router = inject(Router);

  modalService = inject(NgbModal);
  modalConfig = inject(NgbModalConfig);
  countdown: number = 3;
  signupProcessing: boolean = false;
  signupSuccess: boolean = false;
  @ViewChild('success')
  success!: ElementRef;
  @ViewChild('unsuccess')
  unsuccess!: ElementRef;

  ngOnInit(): void {
    this.modalConfig.backdrop = 'static';
    this.modalConfig.keyboard = false;
    this.signupForm = this.fb.group({
      username: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(6),
      ]),
      password: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(8),
      ]),
      customerName: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(3),
      ]),
      customerEmail: this.fb.control<string>('', [
        Validators.required,
        Validators.email,
      ]),
      customerPhone: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(8),
      ]),
      shippingAddress: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(8),
      ]),
      birthdate: this.fb.control<string>('', [
        Validators.required,
        validatePast,
      ]),
      gender: this.fb.control<string>('', [Validators.required]),
    });
  }

  signup() {
    this.signupProcessing = true;
    const signup: RegisterRequest = {
      username: this.signupForm.value.username,
      password: this.signupForm.value.password,
      customerName: this.signupForm.value.customerName,
      customerEmail: this.signupForm.value.customerEmail,
      customerPhone: this.signupForm.value.customerPhone,
      shippingAddress: this.signupForm.value.shippingAddress,
      birthdate: new Date(this.signupForm.value.birthdate).getTime(),
      gender: this.signupForm.value.gender,
    };
    firstValueFrom(this.userSvc.signup(signup)).then((status) => {
      console.info(status);
      this.signupSuccess = status;
      if (this.signupSuccess) {
        this.openVerticallyCentered(this.success);
        let timeinterval = setInterval(() => {
          this.countdown--;
          if (this.countdown <= 0) {
            clearInterval(timeinterval);
            this.signupProcessing = false;
            this.modalService.dismissAll();
            this.router.navigate(['/auth/login']);
          }
        }, 1000);
      } else {
        this.openVerticallyCentered(this.unsuccess);
        this.signupProcessing = false;
      }
    });
  }

  openVerticallyCentered(content: any) {
    this.modalService.open(content, { centered: true });
  }

  skipCountdown() {
    this.countdown = 0;
    console.info('>> [INFO] Countdown Skipped - ', this.countdown);
  }
}
