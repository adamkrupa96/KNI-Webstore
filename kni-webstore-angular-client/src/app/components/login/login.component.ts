import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from '../../models/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loading = false;
  error = '';

  loginForm: FormGroup;

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.createLoginForm();
  }

  createLoginForm() {
    this.loginForm = new FormGroup({
      username: new FormControl(null, [Validators.required, Validators.minLength(4), Validators.maxLength(50)]),
      password: new FormControl(null, [Validators.required, Validators.minLength(4), Validators.maxLength(50)])
    });
  }

  login() {
    this.loading = true;
    this.authService.login(this.loginForm.value.username, this.loginForm.value.password)
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
        }
      }, error => {
        this.loading = false;
        this.error = 'Nazwa użytkownika lub hasło są niepoprawne';
      });
  }
}
