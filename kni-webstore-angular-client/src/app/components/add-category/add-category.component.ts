import { Component, OnInit } from '@angular/core';
import { Category } from '../../models/Category';
import { CategoryService } from '../../services/category/category.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {
  categoriesList = [];
  addCategoryForm: FormGroup;
  category: Category;

  constructor(private catService: CategoryService) {
    this.catService.getCategoryListObservable().subscribe(list => {
      this.categoriesList = list;
    });
  }

  readList() {
    this.catService.getCategoryListObservable().subscribe(list => {
      this.categoriesList = list;
    });
  }

  ngOnInit() {
    this.addCategoryForm = new FormGroup({
      categoryName: new FormControl(null, Validators.required)
    });
    this.category = new Category();
  }

  onSubmit() {
    this.category.name = this.addCategoryForm.value.categoryName;
    this.catService.addCategory(this.category);
    this.onReset();
  }

  onReset() {
    this.addCategoryForm.reset();
  }
}
