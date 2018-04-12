import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuardsService } from './services/auth-guards.service';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { OfferComponent } from './components/offer/offer.component';
import { CategoriesComponent } from './components/offer/categories/categories.component';
import { ProductsOfCategoryComponent } from './components/offer/products-of-category/products-of-category.component';
import { ProductPageComponent } from './components/offer/product-page/product-page.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  // { path: 'adminpanel', component: AdminPanelComponent, canActivate: [AuthGuardsService] },
  {
    path: 'offer',
    children: [
      {
        path: '',
        component: OfferComponent
      },
      {
        path: ':category',
        component: CategoriesComponent
      },
      {
        path: ':category/:subcategory',
        component: ProductsOfCategoryComponent
      },
      {
        path: ':category/:subcategory/:id',
        component: ProductPageComponent
      },
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '**', component: HomeComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class RoutingModule { }
