import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {UserDto} from "../../domain/userDto";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit{

  constructor(private authService: AuthService, private toastr: ToastrService, private userService: UserService,
              private router: Router) {
  }

  nameControl = new FormControl('', [Validators.maxLength(50)]);
  surnameControl = new FormControl('', [Validators.maxLength(50)]);
  addressControl = new FormControl('', [Validators.maxLength(50)]);
  phoneControl = new FormControl('', [Validators.pattern('[0-9]{9}')]);
  taxPayerIdentNoControl = new FormControl('', [Validators.pattern('[0-9]{10}')]);
  descriptionControl = new FormControl('', [Validators.maxLength(200)]);

  confirmationControl = new FormControl('', [Validators.required]);
  passwordControl = new FormControl('', [Validators.required]);

  updateGroup: FormGroup = new FormGroup({
    name: this.nameControl,
    surname: this.surnameControl,
    address: this.addressControl,
    phoneNumber: this.phoneControl,
    taxPayerIdentNo: this.taxPayerIdentNoControl,
    description: this.descriptionControl
  });

  deleteGroup: FormGroup = new FormGroup({
    password: this.passwordControl
  });

  formShow: boolean = false;

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe({
      next: user => {
        this.nameControl.setValue(user.name);
        this.surnameControl.setValue(user.surname);
        this.addressControl.setValue(user.address);
        this.phoneControl.setValue(user.phoneNumber);
        this.taxPayerIdentNoControl.setValue(user.taxPayerIdentNo);
        this.descriptionControl.setValue(user.description);
      },
      error: () => this.toastr.error("Cannot find user information")
    });
  }

  updateAccount() {
    if (this.updateGroup.invalid || !this.authService.isUserLoggedIn()) {
      return;
    }
    const userDetails = new UserDto();
    userDetails.name = this.nameControl.value === null ? '' : this.nameControl.value;
    userDetails.surname = this.surnameControl.value === null ? '' : this.surnameControl.value;
    userDetails.address = this.addressControl.value === null ? '' : this.addressControl.value;
    userDetails.phoneNumber = this.phoneControl.value === null ? '' : this.phoneControl.value;
    userDetails.taxPayerIdentNo = this.taxPayerIdentNoControl.value === null ? '' : this.taxPayerIdentNoControl.value;
    userDetails.description = this.descriptionControl.value === null ? '' : this.descriptionControl.value;
    this.userService.updateUserDetails(userDetails).subscribe({
      next: () => this.toastr.success("Details updated successfully", ''),
      error: () => this.toastr.error("Update failed")
    });
  }

  getRole() {
    return this.authService.getRole();
  }

  deleteCheck(completed: boolean) {
    this.formShow = completed;
  }

  deleteAccount() {
    if (this.deleteGroup.invalid || !this.authService.isUserLoggedIn()) {
      return;
    }
    const data = {
      "confirm": this.formShow,
      "password": this.passwordControl.value
    };
    this.userService.deleteUser(data).pipe().subscribe({
      next: () => {
        this.authService.clear();
        this.authService.userLogout().subscribe();
        this.router.navigate(['home']);
      },
      error: () => this.toastr.error("Cannot delete")
    })
  }
}
