import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Category } from '../models/Category';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from './authentication.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ProductService {

  private restURL = 'http://localhost:8080/api';

  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + this.authService.getToken()
  });

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.restURL + '/categories', { headers: this.headers });
  }
}
