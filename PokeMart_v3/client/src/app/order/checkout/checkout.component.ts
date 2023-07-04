import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { GoogleMap } from '@angular/google-maps';
import { Router } from '@angular/router';
import { Observable, catchError, firstValueFrom, map, of } from 'rxjs';
import { API_KEY, GMAP_GEOCODE_URL, GMAP_JS_URL, center_ISS } from 'src/app/endpoint.constants';
import { CartItem } from 'src/app/model/cart.model';
import { LatLng } from 'src/app/model/order.model';
import { CartService } from 'src/app/services/cart.service';
import { OrderService } from 'src/app/services/order.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent{

  cartSvc = inject(CartService);
  userSvc = inject(UserService);
  orderSvc = inject(OrderService);
  router = inject(Router);
  fb = inject(FormBuilder);
  http = inject(HttpClient);

  shipForm!: FormGroup;
  cart$!: Observable<CartItem[]>;
  apiLoaded$!: Observable<boolean>;

  @ViewChild('map')
  googleMap!:GoogleMap;

  shippingLatLong!:LatLng
  HQmarkerOptions!: google.maps.MarkerOptions
  mapOptions: google.maps.MapOptions = {
    mapId: '4e4dc093ebd5865d',
    center: center_ISS,
    zoom: 16,
  };
  HQmarker = {
    position: center_ISS,
    label: {
      color: 'black',
      text: 'PokeMart HQ',
    },
  };

  ngOnInit(): void {
    this.cart$ = this.cartSvc.getCart()
    this.shipForm = this.fb.group({
      customerName: this.fb.control<string>(
        !!this.userSvc.user.customerName ? this.userSvc.user.customerName : '',
        [Validators.required, Validators.minLength(3)]
      ),
      customerEmail: this.fb.control<string>(
        !!this.userSvc.user.customerEmail ? this.userSvc.user.customerEmail : '',
        [Validators.required, Validators.email]
      ),
      customerPhone: this.fb.control<string>(
        !!this.userSvc.user.customerPhone ? this.userSvc.user.customerPhone : '',
        [Validators.required, Validators.minLength(8)]
      ),
      customerShippingAddress: this.fb.control<string>(
        !!this.userSvc.user.shippingAddress
          ? this.userSvc.user.shippingAddress
          : '',
        [Validators.required]
      ),
    });

    this.apiLoaded$ = this.http
      .jsonp(
        GMAP_JS_URL + API_KEY,
        'callback'
      )
      .pipe(
        map(() => {
          console.log('>> [INFO] GMAP JS API LOADED');
          this.HQmarkerOptions = {
            icon: {
              path:'M4 4a4 4 0 1 1 4.5 3.969V13.5a.5.5 0 0 1-1 0V7.97A4 4 0 0 1 4 3.999zm2.493 8.574a.5.5 0 0 1-.411.575c-.712.118-1.28.295-1.655.493a1.319 1.319 0 0 0-.37.265.301.301 0 0 0-.057.09V14l.002.008a.147.147 0 0 0 .016.033.617.617 0 0 0 .145.15c.165.13.435.27.813.395.751.25 1.82.414 3.024.414s2.273-.163 3.024-.414c.378-.126.648-.265.813-.395a.619.619 0 0 0 .146-.15.148.148 0 0 0 .015-.033L12 14v-.004a.301.301 0 0 0-.057-.09 1.318 1.318 0 0 0-.37-.264c-.376-.198-.943-.375-1.655-.493a.5.5 0 1 1 .164-.986c.77.127 1.452.328 1.957.594C12.5 13 13 13.4 13 14c0 .426-.26.752-.544.977-.29.228-.68.413-1.116.558-.878.293-2.059.465-3.34.465-1.281 0-2.462-.172-3.34-.465-.436-.145-.826-.33-1.116-.558C3.26 14.752 3 14.426 3 14c0-.599.5-1 .961-1.243.505-.266 1.187-.467 1.957-.594a.5.5 0 0 1 .575.411z',
              fillColor: 'green',
              fillOpacity: 1,
              strokeWeight: 1,
              strokeColor:"black",
              rotation: 0,
              scale: 2,
              anchor: new google.maps.Point(0,0),
            },
          };
          firstValueFrom(this.http.get<any>(GMAP_GEOCODE_URL + API_KEY + "&address=" + this.userSvc.user.shippingAddress))
          .then((resp) => {
            console.info("geocode",resp.results[0].geometry.location);
            this.googleMap.googleMap?.setCenter(new google.maps.LatLng(resp.results[0].geometry!.location));
            this.HQmarker.position = {
              lat:resp.results[0].geometry.location.lat,
              lng:resp.results[0].geometry.location.lng
            }
          }  
          ).catch( (err) =>
            console.log(">> [ERROR]: GMAPS GEOCODE API ERROR:" + err)
          )
          return true;
        }),
        catchError((error) => {
          console.log('>> [INFO] GMAP JS API ERROR', error);
          return of(false);
        })
      );
  }


  changeCenter(address:string){
    firstValueFrom(this.http.get<any>(GMAP_GEOCODE_URL + API_KEY + "&address=" + address))
    .then((resp) => {
      console.info(">> [INFO]: GEOCODE SERVICE:",resp.results[0].geometry.location);
      this.googleMap.googleMap?.setCenter(new google.maps.LatLng(resp.results[0].geometry!.location));
    }  
    ).catch( (err) =>
      console.log(">> [ERROR]: GMAPS GEOCODE API ERROR:" + err)
    )
  }

  coupon: FormControl = new FormControl<string>('', [
    Validators.minLength(6), Validators.maxLength(6)
  ]);

}