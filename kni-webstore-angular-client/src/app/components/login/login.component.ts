import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  model: any = {};
  loading = false;
  error = '';
  info: string;

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      if (params['name']) {
        this.info = 'Zaloguj sie do ' + params['name'];
      } else {
        this.info = null;
      }
    });
    this.authService.logout();
  }

  login() {
    this.loading = true;
    this.authService.login(this.model.username, this.model.password)
      .subscribe(result => {
        if (result === true) {
          // login successful
          // przejscie na strone przed ktora bylismy zanim sie zalogowalismy
          const redirectUrl = this.authService.getRedirectUrl();
          console.log(redirectUrl);
          if (redirectUrl) {
            this.router.navigate([redirectUrl]);
            this.authService.setRedirectUrl(null);
          } else if (redirectUrl === undefined || redirectUrl === null) {
            this.router.navigate(['/home']);
          }
        } else {
          // login failed
          this.error = 'Username or password is incorrect';
          this.loading = false;
        }
      }, error => {
        this.loading = false;
        this.error = error;
      });
  }
}
