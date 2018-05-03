import { NgModule } from '@angular/core';

import {
  OfferComponent,
  CategoriesComponent,
  ProductPageComponent,
  ProductsOfCategoryComponent
} from './index';

import { OfferRoutingModule } from './offer.routing.module';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  declarations: [
    OfferComponent,
    CategoriesComponent,
    ProductPageComponent,
    ProductsOfCategoryComponent
  ],
  imports: [
    OfferRoutingModule,
    SharedModule
  ]
})
export class OfferModule {}
