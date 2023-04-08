import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  email = new FormControl('', [Validators.required/*, Validators.email*/]);
  password = new FormControl('', [Validators.required]);

  constructor(private loginService: AuthService, private toastr: ToastrService, private router: Router) {
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
        console.log(this.getEmail().value + ' login success');
        if (this.loginService.getRole().includes("CLIENT")){
          this.router.navigate(['home']);
        }else if (this.loginService.getRole().includes("LENDER")){
          this.router.navigate(['reservations']);
        }
      },
      error: () => {console.log('Login failed');
        this.toastr.error("Login failed");
        },
      complete: () => this.loginGroup.reset()
    });

  }

  private getEmail(): FormControl {
    return this.email;
  }

  private getPassword(): FormControl {
    return this.password;
  }

}
