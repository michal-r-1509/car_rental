import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../../user/service/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  email = new FormControl('', [Validators.required/*, Validators.email*/]);
  password = new FormControl('', [Validators.required]);

  constructor(private loginService: LoginService, private router: Router) {
  }

  loginGroup = new FormGroup({
    email: this.email,
    password: this.password,
  });

  login() {
    if (this.loginGroup.invalid) {
      return;
    }
    this.loginService.authenticate(this.getEmail().value, this.getPassword().value).subscribe({
      next: (data) => {
        console.log('User login success');
        if (this.loginService.getRole().includes("CLIENT")){
          this.router.navigate(['page-not-found']);
        }else if (this.loginService.getRole().includes("LENDER")){
          this.router.navigate(['page-not-found']);
        }
      },
      error: () => console.log('Login failed'),
      complete: () => console.log('completed, now redirection expected')
    });
    this.loginGroup.reset();
  }

  private getEmail(): FormControl {
    return this.email;
  }

  private getPassword(): FormControl {
    return this.password;
  }

}
