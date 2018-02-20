import { Component } from '@angular/core';
import { ProductService } from './services/product.service';
import { Category } from './models/Category';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Sklep KNI. JEDZIEMY!';
  categories: Category[];

  constructor(private prodService: ProductService) {

  }

  getCategories(): void {
    this.prodService.getCategories().subscribe(cat => this.categories = cat);
  }
}
