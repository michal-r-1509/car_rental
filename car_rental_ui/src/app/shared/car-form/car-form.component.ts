import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
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
export class CarFormComponent implements OnInit{

  today = new Date();
  //tempId = 0;

  // regNoControl = new FormControl("");
  brandControl = new FormControl("");
  modelControl = new FormControl("");
  availableControl = new FormControl(true);

  // insuranceEndDateControl = new FormControl("", []);
  // registerDateControl = new FormControl("", []);
  // nextTechReviewDateControl = new FormControl("", []);

  // enginePowerControl = new FormControl("");
  gearboxControl = new FormControl("");
  fuelControl = new FormControl("");
  // fuelUsageControl = new FormControl("");

  seatsControl = new FormControl("");
  // trunkCapControl = new FormControl("");

  perDayControl = new FormControl("");
  // perWeekControl = new FormControl("");
  insuranceControl = new FormControl("");

  carForm: FormGroup = new FormGroup({
    // regNo: this.regNoControl,
    brand: this.brandControl,
    model: this.modelControl,
    available: this.availableControl,
    // insuranceEndDate: this.insuranceEndDateControl,
    // registerDate: this.registerDateControl,
    // nextTechReviewDate: this.nextTechReviewDateControl,
    // enginePower: this.enginePowerControl,
    gearboxType: this.gearboxControl,
    fuelType: this.fuelControl,
    // fuelUsage: this.fuelUsageControl,
    seats: this.seatsControl,
    // trunkCap: this.trunkCapControl,
    perDay: this.perDayControl,
    // perWeek: this.perWeekControl,
    insurance: this.insuranceControl
  });

  gearboxTypes: string[] = ["manual", "automatic"];
  fuelTypes: string[] = ["gasoline", "lpg", "diesel", "electric", "hybrid", "cng"];

  constructor(public dialogRef: MatDialogRef<FleetComponent>,
              @Inject(MAT_DIALOG_DATA) public data: ModalDialogData,
              private authService: AuthService, private carService: CarService) {
  }

  onNoClick(): void {
    // this.dialogRef.close();
    this.carForm.reset();
  }

  ngOnInit(): void {
    if (this.data.tempId != 0 && this.data.car != null){
      this.brandControl.setValue(this.data.car.brand);
      this.modelControl.setValue(this.data.car.model);
      this.availableControl.setValue(this.data.car.available);
      this.gearboxControl.setValue(this.data.car.carDetails.gearboxType.toLowerCase());
      this.fuelControl.setValue(this.data.car.carDetails.fuelType.toLowerCase());
      this.seatsControl.setValue(this.data.car.carDetails.seats.toString());
      this.perDayControl.setValue(this.data.car.cost.perDay.toString());
      this.insuranceControl.setValue(this.data.car.cost.insurance.toString());
    }
  }

  submit(): void {
    console.log("car id: " + this.data.tempId)
    if (!this.authService.isUserLoggedIn() || !this.authService.getRole().includes("LENDER") || this.carForm.invalid) {
      console.log("car data NOT submitted");
      // let temp: string = this.regNoControl.value as string;
      // let temp2: string = this.regNoControl.value ?? '';
      // console.log("string to:" + temp + ":ot co")
      // console.log("string to:" + temp2 + ":ot co")
      return;
    }
    /*
        const carDetails: CarDetails = new CarDetails();
        carDetails.gearbox = this.gearboxControl.value ?? this.gearboxTypes[0];
        carDetails.fuel = this.fuelControl.value ?? this.fuelTypes[0];
        carDetails.seats = Number(this.seatsControl.value ?? '0');

        const cost: Cost =  new Cost();
        cost.perDay = Number(this.perDayControl.value ?? '0');
        cost.insurance = Number(this.insuranceControl.value ?? '0');*/

    let car: Car = {
      id: 0,
      brand: this.brandControl.value ?? '',
      model: this.modelControl.value ?? '',
      available: this.availableControl.value ?? false,
      carDetails: {
        seats: Number(this.seatsControl.value ?? '0'),
        fuelType: this.fuelControl.value ?? this.fuelTypes[0],
        gearboxType: this.gearboxControl.value ?? this.gearboxTypes[0]
      },
      cost: {
        insurance: Number(this.insuranceControl.value ?? '0'),
        perDay: Number(this.perDayControl.value ?? '0'),
      }
    }

    if (this.data.tempId != 0) {
      car.id = this.data.tempId;
      this.carService.updateCar(car, this.data.tempId).pipe().subscribe({
        next: (data: Car) => {
          console.log("poszlo, car id: " + this.data.tempId);
          this.dialogRef.close(data);
          this.carForm.reset();
        },
        error: () => {console.log("nie poszlo")},
        complete: () => {

        }
      });
      console.log("car updated");
    } else {
      this.carService.createCar(car).pipe().subscribe({
        next: (data: Car) => {
          console.log("poszlo");
          this.dialogRef.close(data);
          this.carForm.reset();
        },
        error: () => {console.log("nie poszlo create")},

      });
      console.log(car);
      console.log("new car created");
    }
  }

}
