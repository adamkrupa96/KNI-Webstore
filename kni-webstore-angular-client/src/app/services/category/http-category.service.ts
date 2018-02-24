import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Category } from '../../models/category';
import { SubCategory } from '../../models/subcategory';

@Injectable()
export class HttpCategoryService {
  private SERVER_URL = 'http://localhost:8080/api/categories';

  constructor(private http: HttpClient) { }

  // Nie dokończone 

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.SERVER_URL);
  }

  getCategoryById(id: number): Observable<Category> {
    return this.http.get<Category>(this.SERVER_URL + '/' + id);
  }

  getSubCategoriesOfCategory(id: number): Observable<SubCategory[]> {
    return this.http.get<SubCategory[]>(this.SERVER_URL + '/' + id + '/subcategories');
  }

  getSubCategoryById(id: number): Observable<SubCategory> {
    return this.http.get<SubCategory>(this.SERVER_URL + '/subcategories/' + id);
  }

  addCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(this.SERVER_URL, category);
  }

  addSubCategory(category_id: number, subCat: SubCategory): Observable<SubCategory> {
    return this.http.post<SubCategory>(this.SERVER_URL + '/' + category_id + '/subcategories', subCat);
  }

  updateCategory(category_id: number, category: Category): Observable<Category> {
    return this.http.put<Category>(this.SERVER_URL + '/' + category_id, category);
  }

  updateSubCategory(category_id: number, subCat_id: number, subCat: SubCategory): Observable<SubCategory> {
    return this.http.put<SubCategory>(this.SERVER_URL + '/' + category_id + '/subcategories/' + subCat_id, subCat);
  }

  deleteCategory(category_id: number): Observable<Category> {
    return this.http.delete<Category>(this.SERVER_URL + '/categories/' + category_id);
  }

  deleteSubCategory(subCat_id: number): Observable<SubCategory> {
    return this.http.delete<SubCategory>(this.SERVER_URL + '/subcategories/' + subCat_id);
  }
}
