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
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { AddCatComponent } from './components/admin-panel/components/add-cat/add-cat.component';
import { AddSubcategoryComponent } from './components/admin-panel/components/add-subcategory/add-subcategory.component';
import { AddProductComponent } from './components/admin-panel/components/add-product/add-product.component';
import { TreeViewComponent } from './components/admin-panel/components/tree-view/tree-view.component';


@NgModule({
  declarations: [
    AppComponent,
    SortPipe,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    MenuComponent,
    AdminPanelComponent,
    AddCatComponent,
    AddSubcategoryComponent,
    AddProductComponent,
    TreeViewComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
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
