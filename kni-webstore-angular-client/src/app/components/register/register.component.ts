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
  user = new User();

  constructor(private authService: AuthenticationService) { }

  ngOnInit() {
    this.createRegisterForm();
  }

  createRegisterForm() {
    this.registerForm = new FormGroup({
      username: new FormControl(null, Validators.required),
      firstName: new FormControl(null, Validators.required),
      lastName: new FormControl(null, Validators.required),
      email: new FormControl(null, [Validators.required, Validators.email]),
      password: new FormControl(null, Validators.required)
    });
  }

  register() {
    console.log(this.registerForm);
    this.user.username = this.registerForm.value['username'];
    this.user.firstName = this.registerForm.value['firstName'];
    this.user.lastName = this.registerForm.value['lastName'];
    this.user.email = this.registerForm.value['email'];
    this.user.password = this.registerForm.value['password'];

    console.log(this.user);
    this.authService.register(this.user)
      .subscribe(result => {
        console.log(result);
      });

    this.user = new User();
    this.createRegisterForm();
  }
}
