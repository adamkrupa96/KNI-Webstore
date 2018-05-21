import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../../../models/product';
import { ProductService } from '../../../../services/product.service';
import { ShoppingCartManagementService } from '../../../order-handler/services/shopping-cart-management.service';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent implements OnInit {
  private productId: number;
  public product: Product = new Product();
  productAmount: FormControl;

  constructor(private route: ActivatedRoute, private productService: ProductService,
    private cartService: ShoppingCartManagementService) {
  }

  ngOnInit() {
    this.initProduct();
    this.productAmount = new FormControl(1, Validators.min(1));
  }

  addToCart() {
    const amount = this.productAmount.value;
    if (amount < 1) {
      return;
    }

    this.cartService.addProductToCart(this.product, amount);
    this.productAmount.setValue(1);
  }

  initProduct() {
    this.route.params.subscribe(param => {
      this.productId = param['id'];
    });
    this.productService.getProductById(this.productId).subscribe(product => {
      this.product = product;
    });
  }
}
