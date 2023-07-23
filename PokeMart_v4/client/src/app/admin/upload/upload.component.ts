import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { WebcamImage, WebcamInitError } from 'ngx-webcam';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css'],
})
export class UploadComponent {
  fb = inject(FormBuilder);
  uploadForm!: FormGroup;
  attributeArr!: FormArray;

  @ViewChild('imageFile')
  uploadFile!: ElementRef;
  @ViewChild('camDiv')
  camDiv!: ElementRef;

  cameraOn:boolean = false;
  viewWidth = 0;
  viewHeight = 0;
  trigger$ = new Subject<void>();
  photoImg!:WebcamImage //imageAsDataUrl

  initCam(){
    this.cameraOn? this.cameraOn=false : this.cameraOn=true;
  }
  takePicture() {
    this.trigger$.next()
  }
  getImage(camImage: WebcamImage) {
    console.info('>>[INFO] Camera Image:', camImage)
    this.photoImg=camImage;
  }
  handleInitError(error: WebcamInitError): void {
    if (error.mediaStreamError && error.mediaStreamError.name === "NotAllowedError") {
      console.warn("Camera access was not allowed by user!");
    }
  }

  ngOnInit(): void {
    (this.attributeArr = this.fb.array([])),
      (this.uploadForm = this.fb.group({
        category: this.fb.control<string>('', [Validators.required]),
        cost: this.fb.control<number>(1, [Validators.required]),
        description: this.fb.control<string>('', [Validators.required]),
        productName: this.fb.control<string>('', [Validators.required]),
        attribute: this.attributeArr,
        image: this.fb.control<File | null>(null, [Validators.required]),
        file: this.fb.control<File | null>(null, [Validators.required]),
      }));
  }

  url: string|ArrayBuffer|null = '';
  onSelectFile(event:any) {
    if (event.target.files && event.target.files[0]) {
      let reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]); // read file as data url
      reader.onload = (event) => { // called once readAsDataURL is completed
        this.url = event.target!.result;
      }
    }
  }

  addAttribute() {
    this.attributeArr.push(this.fb.control<string>('', [Validators.required]));
  }
  delAttribute(i: number) {
    this.attributeArr.removeAt(i);
  }
  
  upload(){
    const uploadImg: File = this.uploadFile.nativeElement.files[0]
    const uploadData = this.uploadForm.value;
    const uploadPhoto = this.photoImg.imageAsBase64.length;
    console.info(uploadData,uploadImg,uploadPhoto)
  } 

}

// upload() {
//   const f: File = this.uploadFile.nativeElement.files[0]
//   //this.form.patchValue({ 'file': f })
//   const data = this.form.value
//   console.info('>>> data: ', data)
//   console.info('>>> file: ', f)

//   firstValueFrom(this.uploadSvc.upload(data['title'], f))
//     .then(result => {
//       alert('uploaded')
//       this.form.reset()
//     })
//     .catch(err => {
//       alert(JSON.stringify(err))
//     })

// }
