import { HttpClient } from '@angular/common/http';
import {
  AfterViewInit,
  Component,
  OnInit,
  ViewChild,
  inject,
} from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { GoogleMap } from '@angular/google-maps';
import { Router } from '@angular/router';
import { Observable, Subscription, firstValueFrom } from 'rxjs';
import { API_KEY, GMAP_GEOCODE_URL, HQ_ISS } from 'src/app/endpoint.constants';
import { CartItem } from 'src/app/model/cart.model';
import { DistanceDTO, Shipping, StoreDTO } from 'src/app/model/map.model';
import { CartService } from 'src/app/services/cart.service';
import { MapService } from 'src/app/services/map.service';
import { OrderService } from 'src/app/services/order.service';
import { UserService } from 'src/app/services/user.service';

const shipping = ['Default', 'Express', 'Self Collect'];

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent implements OnInit {
  cartSvc = inject(CartService);
  userSvc = inject(UserService);
  orderSvc = inject(OrderService);
  mapSvc = inject(MapService);
  router = inject(Router);
  fb = inject(FormBuilder);
  http = inject(HttpClient);
  

  cart$!: Observable<CartItem[]>;
  shipForm!: FormGroup;
  shippingCost!: DistanceDTO;
  shippingType: Shipping = Shipping.DEFAULT;
  selectedShippingCost!: number;
  shipping = shipping;

  APILoaded!: boolean;
  APIStatusSub!: Subscription;

  deliveryMarker: google.maps.LatLngLiteral = HQ_ISS; //default
  deliveryMarkerOptions!: google.maps.MarkerOptions;
  @ViewChild('map')
  googleMap!: GoogleMap;
  mapOptions: google.maps.MapOptions = {
    mapId: '4e4dc093ebd5865d',
    zoom: 16,
    clickableIcons: false,
  };

  stores$!: Observable<StoreDTO[]>;
  collectionStore: FormControl = new FormControl<string>('');

  addressChangeflag: boolean = false;
  shippingChangeflag: boolean = false;
  EXPRESS_FACTOR:number = 2

  ngOnInit(): void {
    this.cart$ = this.cartSvc.getCart();
    this.stores$ = this.mapSvc.getAllStores();
    this.shipForm = this.createForm();

    this.APIStatusSub = this.mapSvc.APIStatus$.subscribe((status) => {
      console.log('>> [INFO] GMAP JS API LOADED:', status.valueOf());
      this.APILoaded = status.valueOf();
      if (status.valueOf()) {
        this.deliveryMarkerOptions = {
          icon: {
            path: 'M4 4a4 4 0 1 1 4.5 3.969V13.5a.5.5 0 0 1-1 0V7.97A4 4 0 0 1 4 3.999zm2.493 8.574a.5.5 0 0 1-.411.575c-.712.118-1.28.295-1.655.493a1.319 1.319 0 0 0-.37.265.301.301 0 0 0-.057.09V14l.002.008a.147.147 0 0 0 .016.033.617.617 0 0 0 .145.15c.165.13.435.27.813.395.751.25 1.82.414 3.024.414s2.273-.163 3.024-.414c.378-.126.648-.265.813-.395a.619.619 0 0 0 .146-.15.148.148 0 0 0 .015-.033L12 14v-.004a.301.301 0 0 0-.057-.09 1.318 1.318 0 0 0-.37-.264c-.376-.198-.943-.375-1.655-.493a.5.5 0 1 1 .164-.986c.77.127 1.452.328 1.957.594C12.5 13 13 13.4 13 14c0 .426-.26.752-.544.977-.29.228-.68.413-1.116.558-.878.293-2.059.465-3.34.465-1.281 0-2.462-.172-3.34-.465-.436-.145-.826-.33-1.116-.558C3.26 14.752 3 14.426 3 14c0-.599.5-1 .961-1.243.505-.266 1.187-.467 1.957-.594a.5.5 0 0 1 .575.411z',
            fillColor: '#0d6efd',
            fillOpacity: 1,
            strokeWeight: 1,
            strokeColor: 'grey',
            rotation: 0,
            scale: 2,
            anchor: new google.maps.Point(7, 10),
          },
        };
      }
      firstValueFrom(
        this.mapSvc.geocodeAddress(this.userSvc.user.shippingAddress)
      )
        .then((resp) => {
          this.getShippingCosts(resp);
        })
        .catch((err) =>
          console.log('>> [ERROR]: GMAPS GEOCODE API ERROR:' + err)
        );
    });
  }

  createForm(): FormGroup {
    return this.fb.group({
      customerName: this.fb.control<string>(
        !!this.userSvc.user.customerName ? this.userSvc.user.customerName : '',
        [Validators.required, Validators.minLength(3)]
      ),
      customerEmail: this.fb.control<string>(
        !!this.userSvc.user.customerEmail
          ? this.userSvc.user.customerEmail
          : '',
        [Validators.required, Validators.email]
      ),
      customerPhone: this.fb.control<string>(
        !!this.userSvc.user.customerPhone
          ? this.userSvc.user.customerPhone
          : '',
        [Validators.required, Validators.minLength(8)]
      ),
      customerShippingAddress: this.fb.control<string>(
        !!this.userSvc.user.shippingAddress
          ? this.userSvc.user.shippingAddress
          : '',
        [Validators.required]
      ),
    });
  }

  selectShipping(e: any) {
    console.log('>> [INFO]: Shipping Select:', e.target.value);
    switch (e.target.value) {
      case 'express':
        this.shippingType = Shipping.EXPRESS;
        this.selectedShippingCost = this.shippingCost.expressCost;
        ``;
        break;
      case 'self':
        this.shippingType = Shipping.SELFCOLLECT;
        this.selectedShippingCost = 0;
        break;
      default:
        this.shippingType = Shipping.DEFAULT;
        this.selectedShippingCost = this.shippingCost.defaultCost;
    }
  }

  getShippingCosts(newLocation: google.maps.LatLngLiteral) {
    firstValueFrom(this.mapSvc.getDistance(newLocation))
      .then((resp) => {
        console.info('>> [INFO]: DISTANCE SERVICE:', resp);
        this.shippingCost = resp;
        switch (this.shippingType) {
          case Shipping.EXPRESS:
            this.selectedShippingCost = this.shippingCost.expressCost;
            ``;
            break;
          case Shipping.SELFCOLLECT:
            this.selectedShippingCost = 0;
            break;
          default:
            this.selectedShippingCost = this.shippingCost.defaultCost;
        }
      })
      .catch((err) =>
        console.log('>> [ERROR]: GMAPS DISTANCE MATRIX API ERROR:' + err)
      );
  }

  changeAddress() {
    this.shippingChangeflag = true;
    this.changeMapCenter(this.shipForm.get('customerShippingAddress')!.value);
  }

  changeMapCenter(address: string) {
    firstValueFrom(this.mapSvc.geocodeAddress(address))
      .then((resp) => {
        console.info('>> [INFO]: GEOCODE SERVICE:', resp);
        this.googleMap.googleMap?.setCenter(new google.maps.LatLng(resp));
        this.changeMarkerLocation(resp);
      })
      .catch((err) =>
        console.log('>> [ERROR]: GMAPS GEOCODE API ERROR:' + err)
      );
  }

  changeMarkerLocation(newLocation: google.maps.LatLngLiteral) {
     this.deliveryMarker = newLocation;
    console.info(">> [INFO] MARKER AT: ", newLocation);
    this.getShippingCosts(newLocation);
  }

  selectOnMap(e: any): void {
    console.log('>> [INFO]: Selected Location', e.latLng);
    this.changeMarkerLocation(e.latLng);
    this.mapSvc.geocoder
      .geocode({
        location: e.latLng,
      })
      .subscribe(({ results }) => {
        console.log(
          '>> [INFO]: REVERSE GEOCODE SERVICE:',
          results[0].formatted_address
        );
        this.shipForm
          .get('customerShippingAddress')
          ?.setValue(results[0].formatted_address);
        this.addressChangeflag = true;
      });
  }

  checkout() {
    console.info(this.collectionStore.value);
  }
}

// coupon: FormControl = new FormControl<string>('', [
//   Validators.minLength(6),
//   Validators.maxLength(6),
// ]);
