import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Category } from '../../models/category';

@Injectable()
export class HttpCategoryService {
  readonly SERVER_URL = 'http://localhost:8080/api/categories';

  constructor(private http: HttpClient) { }

  getCategories(): Observable<Array<Category>> {
    return this.http.get<Array<Category>>(this.SERVER_URL);
  }

  addCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(this.SERVER_URL, category);
  }
}
