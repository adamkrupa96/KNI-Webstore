import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../../services/category.service';
import { ActivatedRoute, Params } from '@angular/router';
import { Category } from '../../../models/Category';
import { SubCategory } from '../../../models/Subcategory';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {

  category: Category = new Category();
  subCategories: SubCategory[] = [];

  constructor(private catService: CategoryService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.getCategoryFromAddress();
  }

  getCategoryFromAddress() {
    this.route.paramMap.subscribe((param: Params) => {
      this.catService.getCategoryByName(param.get('category')).subscribe((cat: Category) => {
        this.category = cat;
        this.subCategories = cat.subCategories;
      });
    });
  }

}
