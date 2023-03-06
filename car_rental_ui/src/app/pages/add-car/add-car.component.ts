import {Component} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FleetComponent} from "../fleet/fleet.component";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-add-car',
  templateUrl: './add-car.component.html',
  styleUrls: ['./add-car.component.scss']
})
export class AddCarComponent {

  startDay = new Date(2023, 1, 1);
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


  title: string = "some title";


  constructor(public dialogRef: MatDialogRef<FleetComponent>/*, @Inject(MAT_DIALOG_DATA) public data: Car*/) {

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  submit() {

  }
}
