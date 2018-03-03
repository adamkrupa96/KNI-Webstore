import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { map, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import jwt_decode from 'jwt-decode';
import { User } from '../models/user';


@Injectable()
export class AuthenticationService {
  readonly authUrl = 'http://localhost:8080/auth';
  readonly registerUrl = 'http://localhost:8080/register';
  private redirectUrl: string;
  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient, private router: Router) {
  }

  login(username: string, password: string): Observable<boolean> {
    return this.http.post<any>(this.authUrl, { username: username, password: password }, this.httpOptions)
      .map(tokenResp => {
        console.log(tokenResp);
        // login successful if there's a jwt token in the response
        if (tokenResp && tokenResp.token) {
          // store username and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify({ username: username, token: tokenResp.token }));

          // return true to indicate successful login
          return true;
        } else {
          // return false to indicate failed login
          return false;
        }
      }).catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  register(user: User): Observable<boolean> {
    return this.http.post<any>(this.registerUrl, JSON.stringify(user), this.httpOptions)
      .pipe(
        tap(boolResp => {
          console.log(boolResp);
          return boolResp;
        })
      ).catch((error: any) => Observable.throw(error || 'Server error'));
  }

  getToken(): string {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    const token = currentUser && currentUser.token;
    return token ? token : '';
  }

  getDecodedAccessToken(): Observable<any> {
    try {
      return jwt_decode(this.getToken());
    } catch (Error) {
      return Error;
    }
  }

  getRolesArray(): any[] {
    return this.getDecodedAccessToken()['roles'];
  }

  logout(): void {
    // clear token remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }

  isLoggedIn(): boolean {
    const token: string = this.getToken();
    return token && token.length > 0;
  }

  setRedirectUrl(url: string) {
    this.redirectUrl = url;
  }

  getRedirectUrl(): string {
    return this.redirectUrl;
  }
}
