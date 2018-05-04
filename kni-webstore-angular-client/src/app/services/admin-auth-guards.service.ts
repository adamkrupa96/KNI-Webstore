import { Injectable } from '@angular/core';
import { RouterStateSnapshot, ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthenticationService } from './authentication.service';

@Injectable()
export class AdminAuthGuardsService implements CanActivate {

  constructor(private authService: AuthenticationService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const url: string = state.url;

    return this.checkLogin(url);
  }

  checkLogin(url: string): boolean {

    if (this.authService.isLoggedIn()) {
      const rolesTab: any[] = this.authService.getRolesArray();

      if (rolesTab.some(e => e.authority === 'ROLE_ADMIN')) {
        return true;
      }
    }

    this.authService.setRedirectUrl(url);

    this.router.navigate(['/login']); // TODO - przerzuć na stronę informującą o braku dostępu
    return false;
  }
}
