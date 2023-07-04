import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthRequest } from 'src/app/model/auth.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  userSvc=inject(UserService);
  fb = inject(FormBuilder);
  loginForm!: FormGroup;
  router = inject(Router);
  welcomeimg="";

  @ViewChild('shpassword')
  pwInput!:ElementRef;

  ngOnInit(): void {
    this.welcomeimg = "https://tofu-pokemart.sgp1.digitaloceanspaces.com/main/welcome" + Math.ceil(Math.random()*2) + ".png"
    this.loginForm = this.fb.group({
      username: this.fb.control<string>('', [Validators.required, Validators.minLength(6)]),
      password: this.fb.control<string>('', [Validators.required, Validators.minLength(8)]),
    });

  }
  
  authenticate(){
    this.userSvc.authenticate(this.loginForm.value as AuthRequest);
    this.router.navigate([""]);
  }

  showHidePassword(){
    console.log(">>[INFO]:Show Password");
    this.pwInput.nativeElement.type==="text"?
    this.pwInput.nativeElement.type = "password":
    this.pwInput.nativeElement.type = "text";
  }
  
}
