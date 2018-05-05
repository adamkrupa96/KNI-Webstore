import { Injectable } from '@angular/core';
import { Product } from '../../../models/product';
import { ProductAmount } from '../product-amount';

@Injectable()
export class ShoppingCartManagementService {

  cartKeyInLocalStorage = 'shoppingCart';
  productAmount: ProductAmount[] = [];

  constructor() {
    this.putCartToLocalStorage();
  }

  private putCartToLocalStorage() {
    localStorage.setItem(this.cartKeyInLocalStorage, JSON.stringify({ productAmountList: this.productAmount }));
  }

  addProductToCart(productToAdd: Product, amountOfProduct: number): void {
    const productInCart = this.findProductInCart(productToAdd.id);
    if (productInCart !== undefined) {
      productInCart.amount += amountOfProduct;
    } else {
      const productAmountToAdd = this.collectProducts(productToAdd, amountOfProduct);
      this.productAmount.push(productAmountToAdd);
    }

    this.putCartToLocalStorage();
  }

  findProductInCart(productId: number): ProductAmount {
    return this.productAmount.find(productAndAmount => productAndAmount.product.id === productId);
  }

  collectProducts(productToAdd: Product, amountOfProduct: number): ProductAmount {
    return {
      product: productToAdd,
      amount: amountOfProduct
    };
  }

  updateProductAmount(productId: number, newAmount: number) {
    this.productAmount.forEach((eachProductAmount: ProductAmount) => {
      if (eachProductAmount.product.id === productId) {
        eachProductAmount.amount = newAmount;
      }
    });

    this.putCartToLocalStorage();
  }

  removeProductFromCart(productId: number) {
    this.productAmount = this.productAmount.filter((eachProductAmount: ProductAmount) => eachProductAmount.product.id !== productId);
    this.putCartToLocalStorage();
  }

  getAllProductAmount(): ProductAmount[] {
    this.productAmount = JSON.parse(localStorage.getItem(this.cartKeyInLocalStorage)).productAmountList;
    return this.productAmount;
  }

  removeAllProductsFromCart() {
    this.productAmount = [];
    this.putCartToLocalStorage();
  }
}
