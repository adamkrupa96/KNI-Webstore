import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import { OrderHandlerComponent } from './order-handler.component';
import { OrderHandlerRoutingModule } from './order-handler-routing.module';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    OrderHandlerRoutingModule
  ],
  declarations: [ShoppingCartComponent, OrderHandlerComponent]
})
export class OrderHandlerModule { }
