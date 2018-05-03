import { Component, OnInit } from '@angular/core';
import { SubCategory } from '../../../../models/subcategory';
import { CategoryService } from '../../../../services/category.service';
import { ActivatedRoute, Params } from '@angular/router';

@Component({
  selector: 'app-products-of-category',
  templateUrl: './products-of-category.component.html',
  styleUrls: ['./products-of-category.component.css']
})
export class ProductsOfCategoryComponent implements OnInit {

  subCategory: SubCategory = new SubCategory();

  constructor(private catService: CategoryService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.getCategoryFromAddress();
  }

  getCategoryFromAddress() {
    this.route.paramMap.subscribe((param: Params) => {
      this.catService.getSubCategoryByName(param.get('subcategory')).subscribe((subCat: SubCategory) => {
        this.subCategory = subCat;
      });
    });
  }

}
