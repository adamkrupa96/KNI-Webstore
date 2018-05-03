import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from '../../models/user';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  user: User;
  error = '';

  constructor(private authService: AuthenticationService) { }

  ngOnInit() {
    this.createRegisterForm();
  }

  /**
   * dodanie walidatorow do pol, kazde pole musi zostac wypelnione,
   * ma miec sprawdzana minimalna dlugosc i maksymalna, a pole email ma byc emailem
   */
  createRegisterForm() {
    this.user = new User();
    this.registerForm = new FormGroup({
      username: new FormControl(null, [Validators.required, Validators.minLength(4), Validators.maxLength(50)]),
      firstName: new FormControl(null, [Validators.required, Validators.minLength(4), Validators.maxLength(50)]),
      lastName: new FormControl(null, [Validators.required, Validators.minLength(4), Validators.maxLength(50)]),
      email: new FormControl(null, [Validators.required, Validators.email, Validators.minLength(4), Validators.maxLength(50)]),
      password: new FormControl(null, [Validators.required, Validators.minLength(4), Validators.maxLength(100)])
    });
  }

  register() {
    this.authService.register(this.user)
      .subscribe(result => {
        console.log(result);
        if (result === true) {
          this.authService.redirectToLoginPage();
          this.createRegisterForm();
        }
      }, error => {
        this.error = error.error.message;
      });
  }
}
