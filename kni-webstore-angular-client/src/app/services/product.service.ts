import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Category } from '../models/Category';
import { Observable } from 'rxjs/Observable';
import { Product } from '../models/product';
import { SubCategory } from '../models/subcategory';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ProductService {

  restURL = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) { }

  // Zapytania http wykonywane są "lazy", po wywołaniu ktorejś z metod, koniecznie trzeba użyć subscribe() [za wyjatkiem DELETE]

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.restURL);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.restURL}/${id}`);
  }

  getProductsOfCategory(categoryId: number): Observable<Product[]> {
    const params = new HttpParams().set('cat', `${categoryId}`);
    return this.http.get<Product[]>(`${this.restURL}OfCategory`, {headers: httpOptions.headers, params});
  }

  getProductsOfSubCategory(subCategoryId: number): Observable<Product[]> {
    const params = new HttpParams().set('cat', `${subCategoryId}`);
    return this.http.get<Product[]>(`${this.restURL}OfSubCategory`, {headers: httpOptions.headers, params});
  }

  getUnallocatedProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.restURL}Unallocated`);
  }

  addProduct(product: Product, subCategoryId?: number): Observable<Product> {
    if (subCategoryId) {
      const params = new HttpParams()
        .set('sub', `${subCategoryId}`);
      return this.http.post<Product>(this.restURL, product, { headers: httpOptions.headers, params});
    } else {
      const params = new HttpParams()
        .set('sub', 'null');
      return this.http.post<Product>(this.restURL, product, { headers: httpOptions.headers, params});
    }
  }

  updateProduct(id: number, product: Product, subCategoryId?: number): Observable<Product> {
    if (subCategoryId) {
      const params = new HttpParams().set('sub', `${subCategoryId}`);
      return this.http.put<Product>(`${this.restURL}/${id}`, product, {headers: httpOptions.headers, params});
    } else {
      const params = new HttpParams().set('sub', 'null');
      return this.http.put<Product>(`${this.restURL}/${id}`, product, {headers: httpOptions.headers, params});
    }
  }

  deleteProduct(id: number): void {
    this.http.delete(`${this.restURL}/${id}`).subscribe();
  }

  deleteAll(): void {
    this.http.delete(`${this.restURL}`).subscribe();
  }
}
