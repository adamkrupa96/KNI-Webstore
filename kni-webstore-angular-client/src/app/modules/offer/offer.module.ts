import { NgModule } from '@angular/core';

import {
  OfferComponent,
  CategoriesComponent,
  ProductPageComponent,
  ProductsOfCategoryComponent
} from './index';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PathNavigationComponent } from '../path-navigation/path-navigation.component';

@NgModule({
  declarations: [
    OfferComponent,
    CategoriesComponent,
    ProductPageComponent,
    ProductsOfCategoryComponent,
    PathNavigationComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class OfferModule {}
