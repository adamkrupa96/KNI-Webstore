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
import { CategoryService } from './services/category/category.service';
import { HttpCategoryService } from './services/category/http-category.service';

@NgModule({
  declarations: [
    AppComponent,
    AddCategoryComponent
  ],
  imports: [
    BrowserModule, HttpClientModule, ReactiveFormsModule
  ],
  providers: [ProductService, CategoryService, HttpCategoryService, HttpClient],
  bootstrap: [AppComponent]
})
export class AppModule { }
