import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { firstValueFrom } from 'rxjs';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent {
  profileForm!: FormGroup;
  fb = inject(FormBuilder);
  userSvc = inject(UserService);
  editingProfile: boolean = false;

  modalService = inject(NgbModal);
  modalConfig = inject(NgbModalConfig);
  countdown: number = 3;
  @ViewChild('success')
  success!: ElementRef;
  @ViewChild('unsuccess')
  unsuccess!: ElementRef;
  updateProcessing: Boolean = false;

  ngOnInit(): void {
    this.initProfile();
  }

  initProfile() {
    const birthdate = new Date(this.userSvc.user!.birthdate);
    const birthdateStr =
      birthdate.getDay() +
      '/' +
      (birthdate.getMonth() + 1) +
      '/' +
      birthdate.getFullYear();
    this.profileForm = this.fb.group({
      customerName: this.fb.control<string>(
        !!this.userSvc.user!.customerName
          ? this.userSvc.user!.customerName
          : '',
        [Validators.required, Validators.minLength(3)]
      ),
      customerEmail: this.fb.control<string>(
        !!this.userSvc.user!.customerEmail
          ? this.userSvc.user!.customerEmail
          : '',
        [Validators.required, Validators.email]
      ),
      customerPhone: this.fb.control<string>(
        !!this.userSvc.user!.customerPhone
          ? this.userSvc.user!.customerPhone
          : '',
        [Validators.required, Validators.minLength(8)]
      ),
      shippingAddress: this.fb.control<string>(
        !!this.userSvc.user!.shippingAddress
          ? this.userSvc.user!.shippingAddress
          : '',
        [Validators.required, Validators.minLength(8)]
      ),
      birthdate: this.fb.control<string>(
        !!this.userSvc.user!.birthdate ? birthdateStr : '',
        [Validators.required]
      ),
      gender: this.fb.control<string>(
        !!this.userSvc.user!.gender ? this.userSvc.user!.gender : ' ',
        [Validators.required]
      ),
    });
  }

  editProfile() {
    this.editingProfile = true;
  }

  updateProfile() {
    this.updateProcessing = true;
    firstValueFrom(this.userSvc.updateUser(this.profileForm))
      .then((status) => {
        console.info(status);
        this.openVerticallyCentered(this.success);
        this.updateProcessing = false;
        this.userSvc
          .getUserDetails(this.userSvc.username as string)
          .subscribe(() => this.initProfile());
      })
      .catch((err) => {
        console.info(err);
        this.openVerticallyCentered(this.unsuccess);
        this.updateProcessing = false;
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
