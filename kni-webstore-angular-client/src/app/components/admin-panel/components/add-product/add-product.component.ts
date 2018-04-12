import { Component, OnInit, ViewChild, ElementRef, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Category } from '../../../../models/Category';
import { CategoryService } from '../../../../services/category.service';
import { SubCategory } from '../../../../models/subcategory';
import { Feature } from '../../../../models/feature';
import { Util } from '../util';
import { ProductService } from '../../../../services/product.service';
import { Product } from '../../../../models/product';
import { TreeService } from '../../tree.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  // Pobranie inputu oznaczonego #keyInput
  @ViewChild('keyInput') keyInput: ElementRef;

  addProductForm: FormGroup;
  addFeatureForm: FormGroup;

  categoryList: Category[] = [];
  choosedCategory: Category;

  subCategoriesOfCategory: SubCategory[] = [];
  choosedSubCategory: SubCategory;

  features: Feature[] = [];

  lastAdded: Product;
  lastAddedSubCategory: SubCategory;
  lastAddedCategory: Category;

  categoryNotSelected = false;
  subCategoryNotSelected = false;
  productAdded = false;


  constructor(private formBuilder: FormBuilder,
    private catService: CategoryService,
    private prodService: ProductService,
    private treeService: TreeService) { }

  ngOnInit() {
    this.createForm();
    this.initList();
    this.onFormChange();
  }

  createForm() {
    this.addProductForm = this.formBuilder.group({
      chooseCategory: null,
      chooseSubCategory: null,
      brand: ['', [Validators.required, Validators.maxLength(32)]],
      model: ['', [Validators.required, Validators.maxLength(32)]],
      price: ['', Validators.required],
      inStock: ['', Validators.required],
      shortDesc: [null, Validators.maxLength(63)],
      longDesc: [null, Validators.maxLength(254)]
    });

    this.addFeatureForm = this.formBuilder.group({
      key: ['', [Validators.required, Validators.maxLength(30)]],
      value: ['', [Validators.required, Validators.maxLength(30)]]
    });
  }

  addProduct() {
    if (this.addProductForm.get('chooseCategory').value == null) {
      this.categoryNotSelected = true;
      return;
    }

    if (this.addProductForm.get('chooseSubCategory').value == null) {
      this.subCategoryNotSelected = true;
      return;
    }


    this.categoryNotSelected = false;
    this.subCategoryNotSelected = false;

    const brand = this.addProductForm.get('brand').value.trim().toUpperCase();
    const model = this.addProductForm.get('model').value;
    const price = Number((+this.addProductForm.get('price').value).toFixed(2)); // Zaokrąglenie do drugiego miejsca po przecinku
    const inStock = Math.floor(+this.addProductForm.get('inStock').value); // Pominięcie części ułamkowej
    const shortDesc = this.addProductForm.get('shortDesc').value;
    const longDesc = this.addProductForm.get('longDesc').value;

    this.prodService.addProduct(new Product(`${brand} ${model}`, shortDesc, longDesc , brand, model, price, inStock, this.features),
    this.choosedSubCategory.id).subscribe(res => {
      this.lastAdded = res;
      this.lastAddedCategory = this.choosedCategory;
      this.lastAddedSubCategory = this.choosedSubCategory;
      this.addProductForm.reset();
      this.features = [];
      this.productAdded = true;
      this.treeService.refreshTree();
    });
  }

  addFeature() {
    this.features.push(new Feature(
      Util.firstUpperLetter(this.addFeatureForm.get('key').value),
      Util.firstUpperLetter(this.addFeatureForm.get('value').value)
    ));
    this.addFeatureForm.reset();

    // Ustawienie na niego focus, po dodaniu cechy (Dodajemy ceche enterem i z automatu możemy dodawać następną, koozak :D)
    this.keyInput.nativeElement.focus();
  }

  delFeature(index: number) {
    for (let i = index; i < this.features.length; i++) {
      this.features[i] = this.features[i + 1];
    }
    this.features.pop();
  }

  onFormChange() {
    this.addProductForm.get('chooseCategory').valueChanges.subscribe(value => {
      this.choosedCategory = this.categoryList[value];

      if (this.choosedCategory == null) {
        return;
      }

      this.subCategoriesOfCategory = this.choosedCategory.subCategories;
    });

    this.addProductForm.get('chooseSubCategory').valueChanges.subscribe(value => {
      this.choosedSubCategory = this.subCategoriesOfCategory[value];
    });

    this.addProductForm.get('price').valueChanges.subscribe(value => {
      if (isNaN(+value)) {
        this.addProductForm.get('price').reset();
      }
    });

    this.addProductForm.get('inStock').valueChanges.subscribe(value => {
      if (isNaN(+value)) {
        this.addProductForm.get('inStock').reset();
      }
    });

  }

  initList() {
    this.catService.getAllCategories().subscribe(res => {
      this.categoryList = res;
    });
  }
}
