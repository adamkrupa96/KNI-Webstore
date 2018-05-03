import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './modules/home/home.component';
import { RegisterComponent } from './modules/register/register.component';
import { LoginComponent } from './modules/login/login.component';
import { AuthGuardsService } from './services/auth-guards.service';
import { AdminPanelComponent } from './modules/admin-panel/admin-panel.component';
import { OfferComponent } from './modules/offer/offer.component';
import { CategoriesComponent } from './modules/offer/components/categories/categories.component';
import { ProductsOfCategoryComponent } from './modules/offer/components/products-of-category/products-of-category.component';
import { ProductPageComponent } from './modules/offer/components/product-page/product-page.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  // { path: 'adminpanel', component: AdminPanelComponent, canActivate: [AuthGuardsService] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '**', component: HomeComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class RoutingModule { }
