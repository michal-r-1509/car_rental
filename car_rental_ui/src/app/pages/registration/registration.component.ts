import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../../domain/user";
import {UserService} from "../../user/service/user.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})

export class RegistrationComponent implements OnInit {

  ngOnInit(): void {
  }

  email = new FormControl('', [Validators.required/*, Validators.email*/]);
  password = new FormControl('', [Validators.required]);
  role = new FormControl('', [Validators.required]);
  clientDescription: string = "Account for private customers";
  lenderDescription: string = "Account for companies";
  description: string = "";



  registrationGroup = new FormGroup({
    email: this.email,
    password: this.password,
    role: this.role
  });

  constructor(private userService: UserService, private router: Router, private toastr: ToastrService) {
  }

  createUser(): void {
    const user = new User();

    if (this.registrationGroup.invalid) {
      return;
    }

    user.email = this.email.value == null ? '' : this.email.value;
    user.password = this.password.value == null ? '' : this.password.value;
    user.role = this.role.value == null ? '' : this.role.value;

    //console.log('account type ' + this.roleControl.value + ' reszta ' + this.emailControl.value + ' ' + this.passwordControl.value);
    /*this.userService.createUser(user).pipe().subscribe(
      data => {
        console.log('User registration success')
      },
      error => {
        console.log('User registration failed')
      }
    );*/

    this.userService.createUser(user).pipe().subscribe({
      next: () => {console.log('User registration success');
        // this.router.navigate(['page-not-found']);
        },
      error: () => {console.log('User registration failed');
        this.toastr.error("Registration failed", "", {positionClass: "toast"});
        },
      complete: () => console.log('completed, now redirection expected')
    });
    this.registrationGroup.reset();
  }

  onChangeDescription() {
    if (this.role.value == "client"){
      this.description = this.clientDescription;
    }else if (this.role.value == "lender"){
      this.description = this.lenderDescription;
    }
  }
}
