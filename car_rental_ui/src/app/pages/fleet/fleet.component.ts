import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {CarFormComponent} from "../../shared/car-form/car-form.component";
import {CarService} from "../../services/car.service";
import {AuthService} from "../../services/auth.service";
import {Car} from "../../domain/car";

@Component({
  selector: 'app-fleet',
  templateUrl: './fleet.component.html',
  styleUrls: ['./fleet.component.scss']
})
export class FleetComponent implements OnInit{
  title: string = "";
  id: number = 0;
  cars: Car[] = [];

  constructor(private dialog: MatDialog, private carService: CarService, private authService: AuthService) {
  }

  ngOnInit(): void {
    if (this.authService.isUserLoggedIn()){
      this.carService.getUserCars().pipe().subscribe({
        next: data => this.cars = data,
        error: err => console.log("something gone wrong")
      });
    }
  }

  openNewCarDialog(action: string): void {
    const dialogRef = this.dialog.open(CarFormComponent, {data:{title: action, tempId: this.id}});
    dialogRef.afterClosed().subscribe(() => {
      console.log('The new car dialog was closed');
      // this.title = result;

    });
  }

  openEditCarDialog(action: string) {
    const dialogRef = this.dialog.open(CarFormComponent, {data:{title: action, tempId: this.id}});
    dialogRef.afterClosed().subscribe(() => {
      console.log('The edit car dialog was closed');
      // this.title = result;

    });
  }

}
