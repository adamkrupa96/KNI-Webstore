import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { Category } from '../models/category';
import { SubCategory } from '../models/subcategory';
import { AuthenticationService } from './authentication.service';

@Injectable()
export class CategoryService {

  restURL = 'http://localhost:8080/api/categories';

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + this.authService.getToken()
  });

  // Zapytania http wykonywane są "lazy", po wywołaniu ktorejś z metod, koniecznie trzeba użyć subscribe() [za wyjatkiem DELETE]

  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.restURL, { headers: this.headers });
  }

  getCategoryById(id: number): Observable<Category> {
    return this.http.get<Category>(`${this.restURL}/${id}`, { headers: this.headers });
  }

  getCategoryByName(name: String): Observable<Category> {
    return this.http.get<Category>(`${this.restURL}/byname/${name}`, { headers: this.headers });
  }

  getSubCategoriesOfCategory(id: number): Observable<SubCategory[]> {
    return this.http.get<SubCategory[]>(`${this.restURL}/${id}/subcategories`, { headers: this.headers });
  }

  getSubCategoryById(id: number): Observable<SubCategory> {
    return this.http.get<SubCategory>(`${this.restURL}/subcategories/${id}`, { headers: this.headers });
  }

  categoryExists(name: string): Observable<Boolean> {
    return this.http.get<Boolean>(`${this.restURL}/exist/${name}`, { headers: this.headers });
  }

  getSubCategoryByName(name: string): Observable<SubCategory> {
    return this.http.get<SubCategory>(`${this.restURL}/subcategories/${name}`, { headers: this.headers });
  }

  addCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(this.restURL, category, { headers: this.headers });
  }

  addSubCategoryOfCategory(categoryId: number, subCategory: SubCategory): Observable<SubCategory> {
    return this.http.post<SubCategory>(`${this.restURL}/${categoryId}/subcategories`, subCategory, { headers: this.headers });
  }

  updateCategory(category: Category): Observable<Category> {
    return this.http.put<Category>(`${this.restURL}/${category.id}`, category, { headers: this.headers });
  }

  updateSubCategory(categoryOfSubCat: Category, subCategory: SubCategory): Observable<SubCategory> {
    return this.http.put<SubCategory>(`${this.restURL}/${categoryOfSubCat.id}/subcategories/${subCategory.id}`,
      subCategory, { headers: this.headers });
  }

  deleteCategory(categoryId: number): Observable<Category> {
    return this.http.delete<Category>(`${this.restURL}/${categoryId}`, { headers: this.headers });
  }

  deleteSubCategory(subCategoryId: number): Observable<SubCategory> {
    return this.http.delete<SubCategory>(`${this.restURL}/subcategories/${subCategoryId}`, { headers: this.headers });
  }
}
