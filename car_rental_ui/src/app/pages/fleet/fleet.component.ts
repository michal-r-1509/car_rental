import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {CarFormComponent} from "../../shared/car-form/car-form.component";
import {CarService} from "../../services/car.service";
import {AuthService} from "../../services/auth.service";
import {Car} from "../../domain/car";
import {ConfirmationFormComponent} from "../../shared/confirmation-form/confirmation-form.component";

@Component({
  selector: 'app-fleet',
  templateUrl: './fleet.component.html',
  styleUrls: ['./fleet.component.scss']
})
export class FleetComponent implements OnInit {
  title: string = "";
  //id: number = 0;
  cars: Car[] = [];

  constructor(private dialog: MatDialog, private carService: CarService, private authService: AuthService) {
  }

  ngOnInit(): void {
    if (this.authService.isUserLoggedIn()) {
      this.carService.getUserCars().pipe().subscribe({
        next: data => this.cars = data,
        error: err => console.log("something gone wrong")
      });
    }
  }

  openNewCarDialog(action: string): void {
    const dialogRef = this.dialog.open(CarFormComponent, {data: {title: action, tempId: 0, car: null}});
    dialogRef.afterClosed().subscribe({
      next: (data) => {
        console.log('The new car dialog was closed: -- carId: ' + 0);
        this.cars.push(data);
        //this.ngOnInit();
      }
      // this.title = result;

    });
  }

  openEditCarDialog(action: string, carId: number) {
    //let car: Car;
    this.carService.getCar(carId).pipe().subscribe({
      next: (data) => {
        //car = data;
        const dialogRef = this.dialog.open(CarFormComponent, {data: {title: action, tempId: carId, car: data}});
        dialogRef.afterClosed().subscribe( {
          next: () => this.ngOnInit()

          // this.title = result;
        });
      }
    });
  }

  deleteCar(carId: number) {
    const dialogRef = this.dialog.open(ConfirmationFormComponent);
    dialogRef.afterClosed().subscribe({
      next: (confirmation) => {
        if (confirmation) {
          this.carService.deleteCar(carId).subscribe({
            next: () => this.ngOnInit()
          });
        } else {
          return;
        }
      }
    })
  }
}
