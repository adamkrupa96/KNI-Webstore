import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
// import { Response } from '@angular/http'; potrzebne gdy chcemy uzywac metody handleError
import { map, tap } from 'rxjs/operators';
import { Router, NavigationEnd } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import * as jwt_decode from 'jwt-decode';
import { User } from '../models/user';
import { ActivatedRoute } from '@angular/router';


/**
 * @class AuthenticationService
 * serwis odpowiedzialny za logowanie/rejestracje/autoryzacje uzytkownika
 */
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

  /**
   * metoda logowania, w ktorej otrzymujemy z serwera token
   * jesli token zostaje zwrocony, zapisujemy go w pamieci (localStorage) i zwracamy true
   * w innym przypadku zwracamy false i wylapujemy blad
   */
  login(username: string, password: string): Observable<boolean> {
    return this.http.post<any>(this.authUrl, { username: username, password: password }, this.httpOptions)
      .map(tokenResp => {
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
      }).map((response: any) => response);
  }

  // metoda do wylapywania bugow
  // private handleError (error: Response | any) {
  //   let errMsg: string;
  //   if (error instanceof Response) {
  //     const body = error.json() || '';
  //     const err = body.error || JSON.stringify(body);
  //     errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
  //   } else {
  //     errMsg = error.message ? error.message : error.toString();
  //   }
  //   console.error(errMsg);
  //   return Observable.throw(errMsg);
  // }

  /**
   * metoda do rejestracji usera
   * na razie zwraca po pomyslnej rejestracji boolean
   * po poprawkach w razie bledu walidacji na serwerze, bedzie zwracac tablice/mape bledow
   * */
  register(user: User): Observable<boolean> {
    return this.http.post<any>(this.registerUrl, JSON.stringify(user), this.httpOptions)
      .pipe(
        tap(boolResp => {
          return boolResp;
        })
      ).map((response: any) => response);
  }

  /**
   * pobiera token z localStorage
   */
  getToken(): string {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    const token = currentUser && currentUser.token;
    return token ? token : '';
  }

  /**
   * dekoduje token, jesli ten istnieje
   */
  getDecodedAccessToken(): Observable<any> {
    try {
      return jwt_decode(this.getToken());
    } catch (Error) {
      return null;
    }
  }

  /**
   * zwraca tablice rol uzytkownika, pobrana z czesci "payload" (w ktorej znajduja sie informacje o userze) z tokenu
   */
  getRolesArray(): any[] {
    return this.getDecodedAccessToken()['roles'];
  }

  /**
   * usuwamy token z pamieci i odswiezamy strone
   * bez odswiezenia po zalogowaniu np. z konta admina, na konto usera, mozemy nadal wykonywac wszystkie
   * operacje, ktore moze wykonac admin
   * mozliwe ze trzeba bedzie na to znalezc lepszy sposob niz odswiezenie strony
   */
  logout(): void {
    localStorage.removeItem('currentUser');
    this.refreshPage();
  }

  refreshPage(): void {
    window.location.reload();
  }

  redirectToPreviousPage() {
    this.getRedirectUrl();
    if (this.redirectUrl) {
      this.router.navigate([this.redirectUrl]);
      this.setRedirectUrl('');
    } else if (this.redirectUrl === undefined || this.redirectUrl === null) {
      this.router.navigate(['/home']);
    }
  }

  redirectToLoginPage() {
    this.router.navigate(['/login']);
  }

  /**
   * sprawdzamy czy uzytkownik jest zalogowany (czy w pamieci jest token)
   */
  isLoggedIn(): boolean {
    const token: string = this.getToken();
    return token && token.length > 0;
  }

  setRedirectUrl(redirectUrl: string) {
    this.redirectUrl = redirectUrl;
  }

  getRedirectUrl() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.setRedirectUrl(event.url);
      }
    }, error => {
      return null;
    });
  }
}
