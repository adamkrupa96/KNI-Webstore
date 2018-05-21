import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { OrderHandlerComponent } from './order-handler.component';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';

const routes: Routes = [
  {
      path: 'order',
      component: OrderHandlerComponent,
      children: [
        { path: 'shoppingcart', component: ShoppingCartComponent },
        // shippinginfo component
        // summary component
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrderHandlerRoutingModule { }
