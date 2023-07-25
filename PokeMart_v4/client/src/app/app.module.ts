import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { NavTopComponent } from './nav-top/nav-top.component';
import { ErrorComponent } from './error/error.component';
import { Router, RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  HttpClientModule,
  HttpClientJsonpModule,
  HTTP_INTERCEPTORS,
} from '@angular/common/http';
import { LandingComponent } from './landing/landing.component';
import { CatalogueComponent } from './catalogue/catalogue/catalogue.component';
import { CatalogueItemComponent } from './catalogue/catalogue-item/catalogue-item.component';
import { ItemDetailComponent } from './item-detail/item-detail.component';
import { CategoryComponent } from './catalogue/category/category.component';
import { CartComponent } from './cart/cart/cart.component';
import { CartItemComponent } from './cart/cart-item/cart-item.component';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { UploadComponent } from './admin/upload/upload.component';
import { EditComponent } from './sales/edit/edit.component';
import { WebcamModule } from 'ngx-webcam';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CheckoutComponent } from './order/checkout/checkout.component';
import { ProfileComponent } from './auth/profile/profile.component';
import { OrderDetailComponent } from './order/order-detail/order-detail.component';
import { LocationComponent } from './about/location/location.component';
import { GoogleMapsModule } from '@angular/google-maps';
import { HistoryComponent } from './order/history/history.component';
import { HttpJWTInterceptor } from './auth.interceptor';
import { DevComponent } from './admin/dev/dev.component';
import { NgxStripeModule } from 'ngx-stripe';
import { STRIPE_PKEY } from './endpoint.constants';

const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'shop', component: CatalogueComponent },
  { path: 'shop/category/:category', component: CatalogueComponent },
  { path: 'shop/search', component: CatalogueComponent },
  { path: 'category', component: CategoryComponent },
  { path: 'detail/:productID', component: ItemDetailComponent },

  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/signup', component: SignupComponent },
  { path: 'user/profile', component: ProfileComponent },

  { path: 'sales/edit', component: EditComponent },
  { path: 'admin/upload', component: UploadComponent },

  { path: 'cart', component: CartComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'order/:orderID', component: OrderDetailComponent },

  { path: 'history', component: HistoryComponent },

  { path: 'about', component: LocationComponent },

  { path: 'dev', component: DevComponent },

  { path: 'construction', component: ErrorComponent },
  { path: '**', component: ErrorComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    NavTopComponent,
    ErrorComponent,
    LandingComponent,
    CatalogueComponent,
    CatalogueItemComponent,
    ItemDetailComponent,
    CategoryComponent,
    CartComponent,
    CartItemComponent,
    LoginComponent,
    SignupComponent,
    UploadComponent,
    EditComponent,
    CheckoutComponent,
    ProfileComponent,
    OrderDetailComponent,
    LocationComponent,
    HistoryComponent,
    DevComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes, {
      useHash: true,
      scrollPositionRestoration: 'enabled',
    }),
    HttpClientModule,
    FormsModule,
    NgxStripeModule.forRoot(STRIPE_PKEY),
    ReactiveFormsModule,
    WebcamModule,
    NgbModule,
    GoogleMapsModule,
    HttpClientJsonpModule,
  ],

  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpJWTInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
