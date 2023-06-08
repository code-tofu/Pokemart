import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { ErrorpageComponent } from './errorpage/errorpage.component';
import { HttpClientModule } from '@angular/common/http';
import { CatItemComponent } from './catalogue/cat-item/cat-item.component';
import { CatMainComponent } from './catalogue/cat-main/cat-main.component';
import { ItemDetailComponent } from './catalogue/item-detail/item-detail.component';
import { CategoryListComponent } from './catalogue/category-list/category-list.component';
import { CartMainComponent } from './cart/cart-main/cart-main.component';
import { CartItemComponent } from './cart/cart-item/cart-item.component';

const routes: Routes = [
  { path: '', redirectTo: 'shop', pathMatch: 'full' },
  { path: 'shop', component: CatMainComponent },
  { path: 'shop/category/:category', component: CatMainComponent },
  { path: 'shop/search', component: CatMainComponent },
  { path: 'category', component: CategoryListComponent },
  { path: 'cart', component: CartMainComponent },
  { path: 'uc', component: ErrorpageComponent },
  { path: 'detail/:productID', component: ItemDetailComponent },
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
    CartMainComponent,
    CartItemComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
