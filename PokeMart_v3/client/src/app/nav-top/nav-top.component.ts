import { Component, Input, Output, inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RoutesRecognized } from '@angular/router';
import { Subject } from 'rxjs';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-nav-top',
  templateUrl: './nav-top.component.html',
  styleUrls: ['./nav-top.component.css']
})
export class NavTopComponent {
  searchQuery: FormControl = new FormControl<string>('', [
    Validators.minLength(3),
  ]);
  router = inject(Router);
  actRoute = inject(ActivatedRoute);
  userSvc = inject(UserService);
  page!:String

  @Input()
  logoimg!:String;
  // For class.dropdown-menu-end. Remove?
  window:number=window.innerWidth;
  
  @Output()
  onSwitch$ = new Subject<void>()

  ngOnInit() {
    this.router.events.subscribe((event) => {
    if (event instanceof RoutesRecognized) {
      console.log('>> [INFO] Nav Path:', event.url.toString());
      this.page = event.url.toString();
    }
  });
  }

  switchThemeNav(){
    this.onSwitch$.next();
    //TODO: USE CLASS.SELECTED AND FILTER BRIGHTNESS TO CHANGE IMAGE INSTEAD?
    this.logoimg === 'light' ? (this.logoimg = 'dark') : (this.logoimg = 'light');

  }


  search() {
    if (this.searchQuery.invalid || this.searchQuery.value.trim() < 3) {
      this.searchQuery.setErrors({ length: true });
    } else {
      this.router.navigate(['/shop', 'search'], {
        queryParams: { query: this.searchQuery.value.trim() },
      });
    }
  }

  signout():void{
    this.userSvc.signout();
    this.router.navigate(['/']);
  }
}
