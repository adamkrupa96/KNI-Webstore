import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { Category } from '../../../../models/Category';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CategoryService } from '../../../../services/category.service';
import { SubCategory } from '../../../../models/subcategory';
import { Util } from '../util';

@Component({
  selector: 'app-add-subcategory',
  templateUrl: './add-subcategory.component.html',
  styleUrls: ['./add-subcategory.component.css']
})
export class AddSubcategoryComponent implements OnInit {

  addSubCategoryForm: FormGroup;

  parentOfLastAdded: Category;
  lastAdded: SubCategory;

  subCategoryAdded = false;
  categoryNotSelected = false;
  subCategoryExists = false;
  categoryList: Category[] = [];
  choosedCategory: Category;

  // Opisane na przypadku kategorii
  @Output() subCategoryAddedEvent = new EventEmitter();

  constructor(private formBuilder: FormBuilder,
    private catService: CategoryService) { }

  ngOnInit() {
    this.createForm();
    this.initList();
    this.onFormChange();
  }

  createForm() {
    this.addSubCategoryForm = this.formBuilder.group({
      chooseCategory: null,
      subCategoryName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(32)]]
    });
  }

  onFormChange() {
    this.addSubCategoryForm.get('chooseCategory').valueChanges.subscribe(value => {
      this.choosedCategory = this.categoryList[value];
      this.categoryNotSelected = false;
      this.subCategoryExists = false;
    });

    this.addSubCategoryForm.get('subCategoryName').valueChanges.subscribe(value => {
      this.subCategoryExists = false;
    });
  }

  onSubmit() {
    if (this.addSubCategoryForm.get('chooseCategory').value == null) {
      this.categoryNotSelected = true;
      this.subCategoryAdded = false;
      return;
    }

    this.choosedCategory.subCategories.forEach(subCat => {
      if (subCat.name.toLowerCase().trim() === this.addSubCategoryForm.get('subCategoryName').value.toLowerCase().trim()) {
        this.subCategoryExists = true;
        this.subCategoryAdded = false;
        return;
      }
    });

    if (this.subCategoryExists) {
      return;
    }

    this.catService.addSubCategoryOfCategory(this.choosedCategory.id, new SubCategory(Util.firstUpperLetter(
      this.addSubCategoryForm.get('subCategoryName').value))).subscribe(res => {
        this.lastAdded = res;
        this.parentOfLastAdded = this.choosedCategory;
        this.subCategoryAdded = true;
        this.initList();
        this.addSubCategoryForm.reset();
        this.subCategoryAddedEvent.emit();
      });


  }

  initList() {
    this.catService.getAllCategories().subscribe(res => {
      this.categoryList = res;
    });
  }


}
