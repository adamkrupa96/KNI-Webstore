import { Component, OnInit } from '@angular/core';
import { Category } from '../../models/Category';
import { CategoryService } from '../../services/category.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Product } from '../../models/Product';
import { ProductService } from '../../services/product.service';
import { SubCategory } from '../../models/Subcategory';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {

  subCategoriesList = [];
  categoriesList = [];

  addCategoryForm: FormGroup;

  // Narazie bez walidacji
  addSubCategoryForm: FormGroup;
  addProductForm: FormGroup;

  constructor(private catService: CategoryService,
    private prodService: ProductService) {
  }

  ngOnInit() {
    this.addCategoryForm = new FormGroup({
      categoryName: new FormControl(null, Validators.required)
    });

    this.addSubCategoryForm = new FormGroup({
      chooseCategory: new FormControl(null),
      subCategoryName: new FormControl(null)
    });

    this.addProductForm = new FormGroup({
      chooseCategory: new FormControl(null),
      chooseSubCategory: new FormControl(null),
      brand: new FormControl(null),
      model: new FormControl(null),
      price: new FormControl(null),
      inStock: new FormControl(null)
    });

    this.readCategoryList();
  }

  onSubmitCategory() {
    const toAdd: Category = new Category();
    toAdd.name = this.addCategoryForm.value.categoryName;
    this.catService.addCategory(toAdd).subscribe(() => this.readCategoryList());
    this.addCategoryForm.reset();
  }

  onSubmitSubCategory() {
    const subCat: SubCategory = new SubCategory();
    subCat.name = this.addSubCategoryForm.value.subCategoryName;
    const choosed: number = this.addSubCategoryForm.value.chooseCategory;
    this.catService.addSubCategoryOfCategory(choosed, subCat).subscribe(() => this.readCategoryList());

    this.addSubCategoryForm.reset();
  }

  onSubmitProduct() {
    this.catService.getSubCategoryById(this.addProductForm.value.chooseSubCategory)
      .subscribe(subCat => {
        const prod: Product = new Product();
        prod.brand = this.addProductForm.value.brand;
        prod.model = this.addProductForm.value.model;
        prod.price = this.addProductForm.value.price;
        prod.inStock = this.addProductForm.value.inStock;
        prod.features = [];
        this.prodService.addProduct(prod, subCat.id).subscribe(() => {
          this.readCategoryList();
          this.addProductForm.reset();
        });
      });
  }

  deleteCategory(category: Category): void {
    this.catService.deleteCategory(category).subscribe(() => {
      this.readCategoryList();
    });
  }

  deleteSubCategory(subCategory: SubCategory): void {
    this.catService.deleteSubCategory(subCategory).subscribe(() => {
      this.readCategoryList();
    });
  }

  deleteProduct(product: Product): void {
    this.prodService.deleteProduct(product.id).subscribe(() => {
      this.readCategoryList();
    });
  }

  readCategoryList() {
    this.catService.getAllCategories().subscribe(list => {
      this.categoriesList = list;
    });
  }

  readSubCatList(category_id: number) {
    this.catService.getSubCategoriesOfCategory(category_id)
      .subscribe(subCats => this.subCategoriesList = subCats);
  }
}
