import { Component, Inject, inject } from '@angular/core';
import { UserService } from '../services/user.service';
import { landingImgURL } from '../endpoint.constants';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent {
  userSvc = inject(UserService);
  imgURLroot:string = landingImgURL;

}
