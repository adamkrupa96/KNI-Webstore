import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { HttpCategoryService } from './http-category.service';
import { Category } from '../../models/category';

@Injectable()
export class CategoryService {

  private categoryListObservable = new BehaviorSubject<Array<Category>>([]);

  constructor(private httpService: HttpCategoryService) {
    this.httpService.getCategories().subscribe(categories => {
      this.categoryListObservable.next(categories);
    });
  }

  getCategoryListObservable(): Observable<Category[]> {
    return this.categoryListObservable.asObservable();
  }

  addCategory(category: Category) {
    this.httpService.addCategory(category).subscribe(catNew => {
      const list = this.categoryListObservable.value;
      list.push(catNew);
      this.categoryListObservable.next(list);
    });
  }
}
