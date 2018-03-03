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
import { AddCategoryComponent } from './components/add-category/add-category.component';
import { HttpCategoryService } from './services/category/http-category.service';
import { AuthenticationService } from './services/authentication.service';
import { HomeComponent } from './components/home/home.component';
import { RoutingModule } from './routing.module';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthGuardsService } from './services/auth-guards.service';
import { CategoryService } from './services/category.service';

@NgModule({
  declarations: [
    AppComponent,
    AddCategoryComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    RoutingModule,
    HttpClientModule,
    HttpModule
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
