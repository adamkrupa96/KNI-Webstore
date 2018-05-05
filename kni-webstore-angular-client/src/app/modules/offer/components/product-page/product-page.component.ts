import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../../../models/product';
import { ProductService } from '../../../../services/product.service';
import { ShoppingCartManagementService } from '../../../order-handler/services/shopping-cart-management.service';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent implements OnInit {
  private productId: number;
  public product: Product = new Product();

  constructor(private route: ActivatedRoute, private productService: ProductService,
    private cartService: ShoppingCartManagementService) {
  }

  ngOnInit() {
    this.initProduct();
  }

  addToCart() {
    // TODO - zmienić jeden na wartość inputu przy dodaniu opcji wpisania ilości sztuk
    this.cartService.addProductToCart(this.product, 1);
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
