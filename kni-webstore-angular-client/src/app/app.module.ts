import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpModule } from '@angular/http';
import { HttpClient } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';
import { SortPipe } from './pipes/sortBy';
import { AuthenticationService } from './services/authentication.service';
import { ProductService } from './services/product.service';
import { AuthGuardsService } from './services/auth-guards.service';
import { CategoryService } from './services/category.service';

import { AppComponent } from './app.component';
import { HomeComponent } from './modules/home/home.component';
import { MenuComponent } from './modules/menu/menu.component';
import { LoginComponent } from './modules/login/login.component';
import { RegisterComponent } from './modules/register/register.component';

import { AdminPanelModule } from './modules/admin-panel/admin-panel.module';
import { OfferModule } from './modules/offer/offer.module';
import { SharedModule } from './shared/shared.module';
import { RoutingModule } from './routing.module';
import { AdminAuthGuardsService } from './services/admin-auth-guards.service';
import { OrderHandlerModule } from './modules/order-handler/order-handler.module';
import { ShoppingCartManagementService } from './modules/order-handler/services/shopping-cart-management.service';

@NgModule({
  declarations: [
    AppComponent,
    SortPipe,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    MenuComponent
  ],
  imports: [
    BrowserModule,
    SharedModule,
    OfferModule,
    AdminPanelModule,
    OrderHandlerModule,
    RoutingModule,
    HttpClientModule
  ],
  providers: [
    ProductService,
    CategoryService,
    HttpClient,
    AuthenticationService,
    AuthGuardsService,
    AdminAuthGuardsService,
    ShoppingCartManagementService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
