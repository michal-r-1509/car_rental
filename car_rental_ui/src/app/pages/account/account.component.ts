import {Component} from '@angular/core';
import {AuthService} from "../../user/service/auth.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UpdateUserRequest} from "../../domain/updateUserRequest";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent {

  constructor(private loginService: AuthService, private toastr: ToastrService) {
  }

  nameControl = new FormControl('', [Validators.maxLength(50)]);
  surnameControl = new FormControl('', [Validators.maxLength(50)]);
  addressControl = new FormControl('', [Validators.maxLength(50)]);
  phoneControl = new FormControl('', [Validators.pattern('[0-9]{9}')]);
  taxPayerIdentNoControl = new FormControl('', [Validators.pattern('[0-9]{10}')]);
  descriptionControl = new FormControl('', [Validators.maxLength(200)]);

  updateGroup: FormGroup = new FormGroup({
    name: this.nameControl,
    surname: this.surnameControl,
    address: this.addressControl,
    phoneNumber: this.phoneControl,
    taxPayerIdentNo: this.taxPayerIdentNoControl,
    description: this.descriptionControl
  })

  updateAccount() {
    if (this.updateGroup.invalid || !this.loginService.isUserLoggedIn()) {
      return;
    }
    const userDetails = new UpdateUserRequest();
    userDetails.name = this.nameControl.value === null ? '' : this.nameControl.value;
    userDetails.surname = this.surnameControl.value === null ? '' : this.surnameControl.value;
    userDetails.address = this.addressControl.value === null ? '' : this.addressControl.value;
    userDetails.phoneNumber = this.phoneControl.value === null ? '' : this.phoneControl.value;
    userDetails.taxPayerIdentNo = this.taxPayerIdentNoControl.value === null ? '' : this.taxPayerIdentNoControl.value;
    userDetails.description = this.descriptionControl.value === null ? '' : this.descriptionControl.value;
    this.loginService.updateUserDetails(userDetails).subscribe({
      next: () => this.toastr.success("Details updated successfully", '', {positionClass: "toast"}),
      error: () => this.toastr.error("Update failed", '', {positionClass: "toast"})
    })
  }
}
