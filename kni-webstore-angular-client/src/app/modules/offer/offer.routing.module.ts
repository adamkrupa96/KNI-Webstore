import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {
  OfferComponent,
  CategoriesComponent,
  ProductPageComponent,
  ProductsOfCategoryComponent
} from './index';

const offerRouting: Routes = [
  {
    path: 'offer',
    children: [
      { path: '', component: OfferComponent, pathMatch: 'full' },
      { path: ':category', component: CategoriesComponent },
      { path: ':category/:subcategory', component: ProductsOfCategoryComponent },
      { path: ':category/:subcategory/:id', component: ProductPageComponent }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(offerRouting)],
  exports: [RouterModule]
})
export class OfferRoutingModule {}
