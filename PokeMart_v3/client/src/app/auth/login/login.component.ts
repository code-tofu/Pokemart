import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { firstValueFrom } from 'rxjs';
import { AuthRequest } from 'src/app/model/auth.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  userSvc = inject(UserService);
  fb = inject(FormBuilder);
  modalService = inject(NgbModal);
  modalConfig=inject(NgbModalConfig);
  router = inject(Router);
  loginForm!: FormGroup;
  welcomeimg = '';
  loginSuccess: boolean = false;
  countdown:number=3;

  @ViewChild('shpassword')
  pwInput!: ElementRef;
  @ViewChild('success')
  success!: ElementRef;
  @ViewChild('unsuccess')
  unsuccess!: ElementRef;

  ngOnInit(): void {
    this.modalConfig.backdrop = 'static';
    this.modalConfig.keyboard = false;
    this.welcomeimg =
      'https://tofu-pokemart.sgp1.digitaloceanspaces.com/main/welcome' +
      Math.ceil(Math.random() * 2) +
      '.png';
    this.loginForm = this.fb.group({
      username: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(6),
      ]),
      password: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(8),
      ]),
    });
  }

  authenticate() {
    let authSub = this.userSvc.authenticationStatus.subscribe(
      (status) => {
        console.info(status);
        this.loginSuccess = status;
        if (this.loginSuccess) {
          this.openVerticallyCentered(this.success);
          let 
            timeinterval = setInterval(() => {
              this.countdown-- ;
              if (this.countdown <= 0) {
                clearInterval(timeinterval);
                this.modalService.dismissAll();
                this.router.navigate(['/shop']);
              }
            }, 1000);
        } else {
          this.openVerticallyCentered(this.unsuccess);
        }
      }
    )
    this.userSvc.authenticate(this.loginForm.value as AuthRequest)
  }

  showHidePassword() {
    console.log('>> [INFO]:Show Password');
    this.pwInput.nativeElement.type === 'text'
      ? (this.pwInput.nativeElement.type = 'password')
      : (this.pwInput.nativeElement.type = 'text');
  }

  openVerticallyCentered(content: any) {
    this.modalService.open(content, { centered: true });
  }

  skipCountdown(){
    this.countdown=0;
    console.info(">> [INFO] Countdown Skipped - ",this.countdown )
  }
}
