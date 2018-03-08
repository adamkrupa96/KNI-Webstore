import { Component, HostListener } from '@angular/core';
import { ProductService } from './services/product.service';
import { Category } from './models/Category';
import { AuthenticationService } from './services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'Sklep KNI. JEDZIEMY!';
  navIsFixed = false;

  constructor(public authService: AuthenticationService) {
  }

  logout() {
    this.authService.logout();
  }

  @HostListener('window:scroll', [])
  onWindowScroll() {
    console.log( window.pageYOffset );
    const number = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    if (number > 100) {
      this.navIsFixed = true;
    } else if (this.navIsFixed && number < 10) {
      this.navIsFixed = false;
    }
  }
}
