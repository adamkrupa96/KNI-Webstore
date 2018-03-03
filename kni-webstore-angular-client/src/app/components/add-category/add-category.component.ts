import { Component, OnInit } from '@angular/core';
import { Category } from '../../models/Category';
import { CategoryService } from '../../services/category.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { SubCategory } from '../../models/subcategory';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {

  categoriesList = [];
  addCategoryForm: FormGroup;

  constructor(private catService: CategoryService) {
  }

  ngOnInit() {
    this.addCategoryForm = new FormGroup({
      categoryName: new FormControl(null, Validators.required)
    });
    this.readList();
  }

  readList() {
    this.catService.getAllCategories().subscribe(list => {
      this.categoriesList = list;
    });
  }

  onSubmit() {
    const toAdd: Category = new Category();
    toAdd.name = this.addCategoryForm.value.categoryName;
    this.catService.addCategory(toAdd).subscribe(added => {
      this.categoriesList.push(added);
    });

    this.onReset();
  }

  onReset() {
    this.addCategoryForm.reset();
  }
}
