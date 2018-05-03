import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Product } from '../models/product';
import { SubCategory } from '../models/subcategory';
import { AuthenticationService } from './authentication.service';
import { Feature } from '../models/feature';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ProductService {

  restURL = 'http://localhost:8080/api/products';

  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + this.authService.getToken()
  });

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  // Zapytania http wykonywane są "lazy", po wywołaniu ktorejś z metod, koniecznie trzeba użyć subscribe() [za wyjatkiem DELETE]

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.restURL, { headers: this.headers });
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.restURL}/${id}`, { headers: this.headers });
  }

  getProductsOfCategory(categoryId: number): Observable<Product[]> {
    const params = new HttpParams().set('cat', `${categoryId}`);
    return this.http.get<Product[]>(`${this.restURL}OfCategory`, {headers: this.headers, params});
  }

  getProductsOfSubCategory(subCategoryId: number): Observable<Product[]> {
    const params = new HttpParams().set('cat', `${subCategoryId}`);
    return this.http.get<Product[]>(`${this.restURL}OfSubCategory`, {headers: this.headers, params});
  }

  getUnallocatedProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.restURL}Unallocated`, { headers: this.headers });
  }

  addProduct(product: Product, subCategoryId?: number): Observable<Product> {
    if (subCategoryId) {
      const params = new HttpParams()
        .set('sub', `${subCategoryId}`);
      return this.http.post<Product>(this.restURL, product, { headers: this.headers, params});
    } else {
      const params = new HttpParams()
        .set('sub', 'null');
      return this.http.post<Product>(this.restURL, product, { headers: this.headers, params});
    }
  }

  updateProduct(id: number, product: Product, subCategoryId?: number): Observable<Product> {
    if (subCategoryId) {
      const params = new HttpParams().set('sub', `${subCategoryId}`);
      return this.http.put<Product>(`${this.restURL}/${id}`, product, {headers: this.headers, params});
    } else {
      const params = new HttpParams().set('sub', 'null');
      return this.http.put<Product>(`${this.restURL}/${id}`, product, {headers: this.headers, params});
    }
  }

  deleteProduct(id: number): Observable<Object> {
    return this.http.delete(`${this.restURL}/${id}`, { headers: this.headers });
  }

  deleteAll(): void {
    this.http.delete(`${this.restURL}`, { headers: this.headers }).subscribe();
  }
}
