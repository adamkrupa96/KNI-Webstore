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
  user: User;

  loginForm: FormGroup;

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.createLoginForm();
  }

  createLoginForm() {
    this.user = new User();
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]),
      password: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(50)])
    });
  }

  login() {
    this.loading = true;
    this.authService.login(this.user.username, this.user.password)
      .subscribe(result => {
        if (result === true) {
          this.authService.redirectToPreviousPage();
          this.createLoginForm();
        }
      }, error => {
        this.loading = false;
        this.error = 'Nazwa użytkownika lub hasło są niepoprawne';
      });
  }

}
