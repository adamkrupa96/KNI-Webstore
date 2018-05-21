import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../../../services/product.service';
import { Product } from '../../../../models/product';
import { ShoppingCartManagementService } from '../../services/shopping-cart-management.service';
import { FormControl } from '@angular/forms';
import { ProductAmount } from '../../product-amount';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  productsInCart: ProductAmount[] = [];
  totalValue = 0;

  constructor(private prodService: ProductService,
    private cartService: ShoppingCartManagementService) { }

  ngOnInit() {
    this.reloadProductAmountList();
    this.calculateCartValue();
  }

  defocus(inputTarget, productAndAmountToUpdate: ProductAmount) {
    inputTarget.target.blur(); // czyli defocus
    this.cartService.updateProductAmount(productAndAmountToUpdate.product.id, productAndAmountToUpdate.amount);
    this.reloadProductAmountList();
  }

  reloadProductAmountList() {
    this.productsInCart = this.cartService.getAllProductAmount();
  }

  removeProductFromCart(productAndAmount: ProductAmount) {
    this.cartService.removeProductFromCart(productAndAmount.product.id);
    this.totalValue -= productAndAmount.product.price * productAndAmount.amount;
    this.reloadProductAmountList();
  }

  calculateCartValue() {
    this.totalValue = 0;
    if (this.productsInCart.length === 0) {
      return;
    }

    this.productsInCart.forEach((eachProductAmount: ProductAmount) => {
      let value = 0;
      value += eachProductAmount.product.price * eachProductAmount.amount;
      this.totalValue += value;
    });
  }

  cartIsEmpty(): boolean {
    return this.productsInCart.length === 0;
  }

  clearCart() {
    this.cartService.removeAllProductsFromCart();
    this.productsInCart = [];
  }

}


