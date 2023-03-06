import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {NewUser} from "../../domain/newUser";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})

export class RegistrationComponent implements OnInit {

  constructor(private userService: UserService, private router: Router, private toastr: ToastrService) {
  }

  ngOnInit(): void {
  }

  email = new FormControl('', [Validators.required/*, Validators.email*/]);
  password = new FormControl('', [Validators.required, Validators.minLength(2)]);
  role = new FormControl('', [Validators.required]);
  clientDescription: string = "Account for private customers";
  lenderDescription: string = "Account for companies";
  description: string = "";

  registrationGroup = new FormGroup({
    email: this.email,
    password: this.password,
    role: this.role
  });

  createUser(): void {
    const user = new NewUser();

    if (this.registrationGroup.invalid) {
      return;
    }

    user.email = this.email.value == null ? '' : this.email.value;
    user.password = this.password.value == null ? '' : this.password.value;
    user.role = this.role.value == null ? '' : this.role.value;

    this.userService.createUser(user).pipe().subscribe({
      next: () => {console.log('NewUser registration success');
        this.toastr.success("Registered successfully");
        this.router.navigate(['home']);
        this.registrationGroup.reset();
        },
      error: () => {console.log('NewUser registration failed');
        this.toastr.error("Registration failed");
        },
      complete: () => {
        console.log('completed, now redirection expected');
      }
    });
  }

  onChangeDescription() {
    if (this.role.value == "client"){
      this.description = this.clientDescription;
    }else if (this.role.value == "lender"){
      this.description = this.lenderDescription;
    }
  }
}
