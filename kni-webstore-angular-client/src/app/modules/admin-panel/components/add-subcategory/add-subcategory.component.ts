import { Component, OnInit, EventEmitter, Output, ViewChild, ElementRef } from '@angular/core';
import { Category } from '../../../../models/category';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CategoryService } from '../../../../services/category.service';
import { SubCategory } from '../../../../models/subcategory';
import { Util } from '../util';
import { TreeService } from '../../tree.service';
import { notOnlyDigitsValidator } from '../validators/not-only-digits-validator';
import { UploadFileService } from '../../../../services/upload-file.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';

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

  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };
  subcategory: SubCategory;
  restURL = 'http://localhost:8080/api/subcat-images/images';
  @ViewChild('fileInput')
  fileInput: ElementRef;

  constructor(private formBuilder: FormBuilder,
    private catService: CategoryService,
    private treeService: TreeService,
    private uploadService: UploadFileService) { }

  ngOnInit() {
    this.createForm();
    this.initList();
    this.onFormChange();
  }

  createForm() {
    this.addSubCategoryForm = this.formBuilder.group({
      chooseCategory: null,
      subCategoryName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(32), notOnlyDigitsValidator]]
    });
  }

  onFormChange() {
    const dropDownList = this.addSubCategoryForm.get('chooseCategory');
    const nameControl = this.addSubCategoryForm.get('subCategoryName');

    dropDownList.valueChanges.subscribe(value => {
      this.choosedCategory = this.categoryList[value];
      this.categoryNotSelected = false;
      this.subCategoryExists = false;
    });

    nameControl.valueChanges.subscribe(value => {
      this.subCategoryExists = false;
    });
  }

  addSubCategory() {
    if (this.addSubCategoryForm.get('chooseCategory').value == null) {
      this.categoryNotSelected = true;
      this.subCategoryAdded = false;
      return;
    }

    this.checkSubCategoryExists();
    if (this.subCategoryExists) {
      return;
    }
    let subCategoryToAddName = this.addSubCategoryForm.get('subCategoryName').value;
    subCategoryToAddName = Util.firstUpperLetter(subCategoryToAddName);

    const subCategoryToAdd = this.createNewSubCategory(subCategoryToAddName);
    const catchAddedSubCategory = (response: SubCategory) => {
      this.resetComponentOnAddedSubCategory(response);
    };

    // po wyslaniu na serwer pokategorii, jesli wystapi error, wypisujemy go na konsoli
    // w przeciwnym wypadku wysylamy plik na serwer, ustawiajac id dodanej podkategorii
    // mozliwe ze pozniej trzeba bedzie to inaczej rozwiazac
    this.catService.addSubCategoryOfCategory(this.choosedCategory.id, subCategoryToAdd)
      .subscribe(catchAddedSubCategory,
      error => {
        console.log(JSON.parse(error));
      },
      () => this.upload(this.lastAdded.id)
    );
  }

  checkSubCategoryExists() {
    this.choosedCategory.subCategories.forEach(eachSubCategory => {
      const eachSubCategoryName = eachSubCategory.name.toLowerCase().trim();
      const subCategoryToAddName = this.addSubCategoryForm.get('subCategoryName').value.toLowerCase().trim();

      if (eachSubCategoryName === subCategoryToAddName) {
        this.subCategoryExists = true;
        this.subCategoryAdded = false;
      }
    });
  }

  createNewSubCategory(name: string): SubCategory {
    const subCategory = new SubCategory();
    subCategory.name = name;
    subCategory.products = [];
    return subCategory;
  }

  resetComponentOnAddedSubCategory(subCategory: SubCategory) {
    this.lastAdded = subCategory;
    this.parentOfLastAdded = this.choosedCategory;
    this.subCategoryAdded = true;
    this.initList();
    this.addSubCategoryForm.reset();
    this.treeService.refreshTree();
  }

  initList() {
    this.catService.getAllCategories().subscribe(res => {
      this.categoryList = res;
    });
  }

  selectFile(event) {
    const file = event.target.files.item(0);

    // sprawdzamy czy dodawany plik jest obrazkiem
    if (file && file.type.match('image.*')) {
      this.selectedFiles = event.target.files;
    } else {
      alert('invalid format!');
    }
  }

  upload(entityID: number) {
    this.progress.percentage = 0;

    this.currentFileUpload = this.selectedFiles.item(0);
    this.uploadService.pushFileToStorage(this.restURL, this.currentFileUpload, entityID).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        console.log('File is completely uploaded!');
      }
    });

    this.selectedFiles = undefined;
    this.fileInput.nativeElement.value = '';
  }

}
