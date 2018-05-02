import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Category } from '../../../../models/category';
import { CategoryService } from '../../../../services/category.service';
import { TreeService } from '../../tree.service';
import { notOnlyDigitsValidator } from '../validators/not-only-digits-validator';

@Component({
  selector: 'app-add-cat',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {

  addCategoryForm: FormGroup;
  categoryExists = false;
  categoryAdded = false;
  lastAdded: Category;

  constructor(private formBuilder: FormBuilder,
    private catService: CategoryService,
    private treeService: TreeService) { }

  ngOnInit() {
    this.createForm();
    this.categoryExists = false;
    this.categoryAdded = false;
  }

  createForm() {
    this.addCategoryForm = this.formBuilder.group({
      categoryName: ['',
        [Validators.required,
        Validators.minLength(2),
        Validators.maxLength(32),
          notOnlyDigitsValidator]
      ]
    });
  }

  addCategory() {
    const categoryNameTrimmed = this.addCategoryForm.get('categoryName').value.trim();

    const catchCategoryAddedResponse = (response: Category) => {
      this.resetComponentOnAddedCategory(response);
    };

    const catchError = error => console.log('Backend error');

    const categoryExistResponse = (response: boolean) => {
      if (response) {
        this.categoryExists = true;
        this.categoryAdded = false;
      } else {
        const categoryName = this.addCategoryForm.get('categoryName').value;
        const categoryToAdd = this.createNewCategory(this.trimAndFirstLetterUpper(categoryName));

        this.catService.addCategory(categoryToAdd).subscribe(catchCategoryAddedResponse, catchError);
      }
    };

    this.catService.categoryExists(categoryNameTrimmed).subscribe(categoryExistResponse, catchError);
  }

  resetComponentOnAddedCategory(category: Category) {
    console.log('Pomyślnie dodano kategorie o ID: ' + category.id + ' i nazwie: ' + category.name);
    this.lastAdded = category;
    this.categoryExists = false;
    this.categoryAdded = true;
    this.addCategoryForm.reset();
    this.treeService.refreshTree();
  }

  trimAndFirstLetterUpper(name: string): string {
    name = name.trim().toLowerCase();
    return name.charAt(0).toUpperCase() + name.slice(1);
  }

  createNewCategory(name: string): Category {
    const category = new Category();
    category.name = name;
    category.subCategories = [];
    return category;
  }
}
