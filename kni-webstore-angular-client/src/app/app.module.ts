import { HttpModule } from '@angular/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { ProductService } from './services/product.service';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';
import { HttpClientModule } from '@angular/common/http';
import { AuthenticationService } from './services/authentication.service';
import { HomeComponent } from './components/home/home.component';
import { RoutingModule } from './routing.module';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthGuardsService } from './services/auth-guards.service';
import { CategoryService } from './services/category.service';
import { SortPipe } from './pipes/sortBy';
import { MenuComponent } from './components/menu/menu.component';

import { OfferComponent } from './components/offer/offer.component';
import { CategoriesComponent } from './components/offer/categories/categories.component';
import { ProductsOfCategoryComponent } from './components/offer/products-of-category/products-of-category.component';
import { ProductPageComponent } from './components/offer/product-page/product-page.component';
import { AdminPanelModule } from './components/admin-panel/admin-panel.module';
import { AdminPanelRoutingModule } from './components/admin-panel/admin-panel-routing.module';
import { PathNavigationComponent } from './components/path-navigation/path-navigation.component';

@NgModule({
  declarations: [
    AppComponent,
    SortPipe,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    MenuComponent,
    OfferComponent,
    CategoriesComponent,
    ProductsOfCategoryComponent,
    ProductPageComponent,
    PathNavigationComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AdminPanelRoutingModule,
    RoutingModule,
    HttpClientModule
  ],
  providers: [
    ProductService,
    CategoryService,
    HttpClient,
    AuthenticationService,
    AuthGuardsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
