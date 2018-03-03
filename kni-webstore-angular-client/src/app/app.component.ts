import { Component } from '@angular/core';
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

  constructor(public authService: AuthenticationService) {
  }

  logout() {
    this.authService.logout();
  }
}
