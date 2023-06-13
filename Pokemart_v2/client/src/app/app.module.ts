import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { ErrorpageComponent } from './error/errorpage.component';
import { HttpClientModule } from '@angular/common/http';
import { CatItemComponent } from './catalogue/cat-item/cat-item.component';
import { CatMainComponent } from './catalogue/cat-main/cat-main.component';
import { ItemDetailComponent } from './catalogue/item-detail/item-detail.component';
import { CategoryListComponent } from './catalogue/category-list/category-list.component';

import { NavTopComponent } from './nav-top/nav-top.component';
import { CartMainComponent } from './cart/cart-main/cart-main.component';
import { CartItemComponent } from './cart/cart-item/cart-item.component';
import { HistoryComponent } from './orders/history/history.component';
import { CheckoutComponent } from './orders/checkout/checkout.component';
import { OrderDetailComponent } from './orders/order-detail/order-detail.component';

const routes: Routes = [
  { path: '', redirectTo: 'shop', pathMatch: 'full' },
  { path: 'shop', component: CatMainComponent },
  { path: 'shop/category/:category', component: CatMainComponent },
  { path: 'shop/search', component: CatMainComponent },
  { path: 'category', component: CategoryListComponent },
  { path: 'cart', component: CartMainComponent },
  { path: 'construction', component: ErrorpageComponent },
  { path: 'detail/:productID', component: ItemDetailComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'order/:orderID', component: OrderDetailComponent },
  { path: 'history', component: HistoryComponent },
  { path: '**', component: ErrorpageComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    ErrorpageComponent,
    CatItemComponent,
    CatMainComponent,
    ItemDetailComponent,
    CategoryListComponent,
    NavTopComponent,
    CartMainComponent,
    CartItemComponent,
    HistoryComponent,
    CheckoutComponent,
    OrderDetailComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes, { useHash: true }),
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
