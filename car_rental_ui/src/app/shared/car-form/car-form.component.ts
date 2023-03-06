import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FleetComponent} from "../../pages/fleet/fleet.component";
import {ModalDialogData} from "../modalDialogData";
import {AuthService} from "../../services/auth.service";
import {CarService} from "../../services/car.service";
import {Car} from "../../domain/car";

@Component({
  selector: 'app-car-form',
  templateUrl: './car-form.component.html',
  styleUrls: ['./car-form.component.scss']
})
export class CarFormComponent {

  today = new Date();

  regNoControl = new FormControl("");
  brandControl = new FormControl("");
  modelControl = new FormControl("");
  availableControl = new FormControl(true);

  insuranceEndDateControl = new FormControl("", []);
  registerDateControl = new FormControl("", []);
  nextTechReviewDateControl = new FormControl("", []);

  enginePowerControl = new FormControl("");
  gearboxControl = new FormControl("");
  fuelControl = new FormControl("");
  fuelUsageControl = new FormControl("");

  seatsControl = new FormControl("");
  trunkCapControl = new FormControl("");

  perDayControl = new FormControl("");
  perWeekControl = new FormControl("");
  insuranceControl = new FormControl("");

  carForm: FormGroup = new FormGroup({
    regNo: this.regNoControl,
    brand: this.brandControl,
    model: this.modelControl,
    available: this.availableControl,
    insuranceEndDate: this.insuranceEndDateControl,
    registerDate: this.registerDateControl,
    nextTechReviewDate: this.nextTechReviewDateControl,
    enginePower: this.enginePowerControl,
    gearbox: this.gearboxControl,
    fuel: this.fuelControl,
    fuelUsage: this.fuelUsageControl,
    seats: this.seatsControl,
    trunkCap: this.trunkCapControl,
    perDay: this.perDayControl,
    perWeek: this.perWeekControl,
    insurance: this.insuranceControl
  });

  gearboxTypes: string[] = ["manual", "automatic"];
  fuelTypes: string[] = ["gasoline", "LPG", "diesel", "electric", "hybrid", "CNG"];


  constructor(public dialogRef: MatDialogRef<FleetComponent>,
              @Inject(MAT_DIALOG_DATA) public data: ModalDialogData,
              private authService: AuthService, private carService: CarService) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  submit(): void {
    if (!this.authService.isUserLoggedIn() || !this.authService.getRole().includes("LENDER") || this.carForm.invalid){
      // console.log("car data NOT submitted");
      let temp: string = this.regNoControl.value as string;
      let temp2: string = this.regNoControl.value ?? '';
      console.log("string to:" + temp + ":ot co")
      console.log("string to:" + temp2 + ":ot co")
      return;
    }

    const car: Car = new Car();
    car.regNo = this.regNoControl.value ?? '';


    if (this.data.tempId != 0){
      this.carService.updateCar(new Car(), this.data.tempId);
    }else{
      this.carService.createCar(new Car());
    }

    console.log("car data submitted");
  }

}
