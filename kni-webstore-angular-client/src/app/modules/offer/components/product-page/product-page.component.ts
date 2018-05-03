import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../../../models/product';
import { ProductService } from '../../../../services/product.service';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent implements OnInit {
  private productId: number;
  public product: Product = new Product();

  constructor(private route: ActivatedRoute, private productService: ProductService) {
  }

  ngOnInit() {
    this.initProduct();
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
