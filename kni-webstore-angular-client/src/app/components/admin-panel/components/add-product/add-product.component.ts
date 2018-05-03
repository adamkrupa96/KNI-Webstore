import { Component, OnInit, ViewChild, ElementRef, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Category } from '../../../../models/category';
import { CategoryService } from '../../../../services/category.service';
import { SubCategory } from '../../../../models/subcategory';
import { Feature } from '../../../../models/feature';
import { Util } from '../util';
import { ProductService } from '../../../../services/product.service';
import { Product } from '../../../../models/product';
import { TreeService } from '../../tree.service';
import { notOnlyDigitsValidator } from '../validators/not-only-digits-validator';

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
      brand: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(16), notOnlyDigitsValidator]],
      model: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(32), notOnlyDigitsValidator]],
      price: ['', Validators.required],
      inStock: ['', Validators.required],
      shortDesc: [null, Validators.maxLength(96)],
      longDesc: [null, Validators.maxLength(256)]
    });

    this.addFeatureForm = this.formBuilder.group({
      key: ['', [Validators.required, Validators.maxLength(16)]],
      value: ['', [Validators.required, Validators.maxLength(16)]]
    });
  }

  addProduct() {
    const chooseCategoryDropdown = this.addProductForm.get('chooseCategory').value;
    const chooseSubCategoryDropdown = this.addProductForm.get('chooseSubCategory').value;

    if (chooseCategoryDropdown == null) {
      this.categoryNotSelected = true;
      return;
    }
    if (chooseSubCategoryDropdown == null) {
      this.subCategoryNotSelected = true;
      return;
    }

    this.categoryNotSelected = false;
    this.subCategoryNotSelected = false;

    const productToAdd = this.createProductFromFormValues();
    this.prodService.addProduct(productToAdd, this.choosedSubCategory.id).subscribe((response: Product) => {
      this.onCatchAddedProduct(response);
    });
  }

  createProductFromFormValues(): Product {
    const brand = this.productFormValues('brand').trim().toUpperCase();
    const model = this.productFormValues('model').trim();
    const price = this.roundToSecoundPlace(+this.productFormValues('price'));
    const inStock = Math.floor(+this.productFormValues('inStock'));
    const shortDesc = this.productFormValues('shortDesc');
    const longDesc = this.productFormValues('longDesc');

    const productToAdd = new Product();
    productToAdd.name = `${brand} ${model}`;
    productToAdd.model = model;
    productToAdd.price = price;
    productToAdd.inStock = inStock;
    productToAdd.shortDescription = shortDesc;
    productToAdd.longDescription = longDesc;
    productToAdd.features = this.features;

    return productToAdd;
  }

  productFormValues(controlName: string): string {
    return this.addProductForm.get(controlName).value;
  }

  roundToSecoundPlace(number: number): number {
    return Number(number.toFixed(2));
  }

  onCatchAddedProduct(product: Product) {
    this.lastAdded = product;
    this.lastAddedCategory = this.choosedCategory;
    this.lastAddedSubCategory = this.choosedSubCategory;

    this.addProductForm.reset();
    this.features = [];
    this.productAdded = true;
    this.treeService.refreshTree();
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
