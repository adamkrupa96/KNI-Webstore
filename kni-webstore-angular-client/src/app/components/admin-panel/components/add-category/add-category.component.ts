import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { CategoryService } from '../../../../services/category.service';
import { Category } from '../../../../models/Category';
import { TreeService } from '../../tree.service';

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
      categoryName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(32)]]
    });
  }


  onSubmit() {
    this.catService.categoryExists(this.addCategoryForm.get('categoryName').value.trim()).subscribe(res => {
      if (res) {
        this.categoryExists = true;
        this.categoryAdded = false;
      } else {
        let categoryName = this.addCategoryForm.get('categoryName').value.trim().toLowerCase();
        categoryName = categoryName.charAt(0).toUpperCase() + categoryName.slice(1);

        this.catService.addCategory(new Category(categoryName)).subscribe(catRes => {
          console.log('Pomyślnie dodano kategorie o ID: ' + catRes.id + ' i nazwie: ' + catRes.name);
          this.lastAdded = catRes;
          this.categoryExists = false;
          this.categoryAdded = true;
          this.addCategoryForm.reset();
          this.treeService.refreshTree();
        });
      }
    });
  }
}
