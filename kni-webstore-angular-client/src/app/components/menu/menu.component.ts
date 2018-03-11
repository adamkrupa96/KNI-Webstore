import { Component, OnInit, HostListener } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  navIsFixed = false;

  constructor(public authService: AuthenticationService) {
  }

  ngOnInit(): void {
  }

  logout() {
    this.authService.logout();
  }

  @HostListener('window:scroll', [])
  onWindowScroll() {
    console.log( window.pageYOffset );
    const number = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    if (number > 40) {
      this.navIsFixed = true;
    } else if (this.navIsFixed && number < 10) {
      this.navIsFixed = false;
    }
  }
}
