import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { WebcamImage, WebcamInitError } from 'ngx-webcam';
import { Subject, firstValueFrom } from 'rxjs';
import { SalesService } from 'src/app/services/sales.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css'],
})
export class UploadComponent {
  fb = inject(FormBuilder);
  salesSvc = inject(SalesService);
  uploadForm!: FormGroup;
  attributeArr!: FormArray;
  router = inject(Router);

  @ViewChild('imageFile')
  uploadFile!: ElementRef;
  @ViewChild('camDiv')
  camDiv!: ElementRef;

  cameraOn: boolean = false;
  viewWidth = 0;
  viewHeight = 0;
  trigger$ = new Subject<void>();

  imgUrl: string | ArrayBuffer | null = '';

  initCam() {
    this.cameraOn ? (this.cameraOn = false) : (this.cameraOn = true);
  }
  takeCamPicture() {
    this.trigger$.next();
  }
  getCamPicture(camImage: WebcamImage) {
    console.info('>>[INFO] Camera Image:', camImage);
    this.imgUrl = camImage.imageAsDataUrl;
    this.cameraOn = false;
  }
  handleInitError(error: WebcamInitError): void {
    if (
      error.mediaStreamError &&
      error.mediaStreamError.name === 'NotAllowedError'
    ) {
      console.warn('Camera access was not allowed by user!');
    }
  }

  ngOnInit(): void {
    (this.attributeArr = this.fb.array([])),
      (this.uploadForm = this.fb.group({
        category: this.fb.control<string>('', [Validators.required]),
        cost: this.fb.control<number>(1, [Validators.required]),
        description: this.fb.control<string>('', [Validators.required]),
        productName: this.fb.control<string>('', [Validators.required]),
        attributes: this.attributeArr,
        image: this.fb.control<File | null>(null),
        file: this.fb.control<File | null>(null),
      }));
  }

  onSelectFile(event: any) {
    if (event.target.files && event.target.files[0]) {
      let reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]); // read file as data url
      reader.onload = (event) => {
        // called once readAsDataURL is completed
        this.imgUrl = event.target!.result;
      };
    }
  }

  addAttribute() {
    this.attributeArr.push(this.fb.control<string>('', [Validators.required]));
  }
  delAttribute(i: number) {
    this.attributeArr.removeAt(i);
  }

  upload() {
    // const uploadImg: File = this.uploadFile.nativeElement.files[0];
    const uploadImg: File = this.dataURLtoFile(
      this.imgUrl as string,
      this.uploadForm.get('productName')!.value
    );

    firstValueFrom(
      this.salesSvc.uploadNewProduct(
        JSON.stringify(this.uploadForm.value),
        uploadImg
      )
    )
      .then((resp) => {
        alert('Product Successfully Uploaded:\n' + JSON.stringify(resp));
        this.uploadForm.reset();
        this.router.navigate(['/sales', 'edit'], {
          queryParams: { product: resp.createdID.substring(1) },
        });
      })
      .catch((err) => {
        alert('Server Error:' + JSON.stringify(err));
      });
  }

  dataURLtoFile(dataurl: string, filename: string) {
    var arr = dataurl.split(','),
      // @ts-ignore
      mime = arr[0].match(/:(.*?);/)[1],
      bstr = atob(arr[arr.length - 1]),
      n = bstr.length,
      u8arr = new Uint8Array(n);
    while (n--) {
      u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, { type: mime });
  }
}
