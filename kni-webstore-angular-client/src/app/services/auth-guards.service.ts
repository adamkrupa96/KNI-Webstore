import { Injectable } from '@angular/core';
import { RouterStateSnapshot, ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthenticationService } from './authentication.service';


/**
 * serwis odpowiedzialny za ochronę routingu
 * w metodzie canActivate definiujemy w jaki sposob taka ochrona ma zachodzic
 * pozniej dodajemy taki serwis do tablicy routingu (w module routingu) dla poszczegolnej sciezki
 *
 * obiekt typu RouterStateSnapshot pozwala nam na wyciagniecie adresu url strony na ktora chcielismy wejsc
 * ale nie zostalismy dopuszczeni, bo nie mielismy oprawnien/nie bylismy zalogowani
 */

@Injectable()
export class AuthGuardsService implements CanActivate {

  constructor(private authService: AuthenticationService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const url: string = state.url;

    return this.checkLogin(url);
  }

  checkLogin(url: string): boolean {
    console.log(this.authService.getDecodedAccessToken());

    if (this.authService.isLoggedIn()) {
      const rolesTab: any[] = this.authService.getRolesArray();

      if (rolesTab.some(e => e.authority === 'ROLE_USER')) {
        return true;
      }
    }

    // Store the attempted URL for redirecting
    this.authService.setRedirectUrl(url);

    // Navigate to the login page with extras
    this.router.navigate(['/login']);
    return false;
  }
}

