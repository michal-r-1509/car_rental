import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {CarService} from "../../services/car.service";
import {Car} from "../../domain/car";
import {MatDialog} from "@angular/material/dialog";
import {UserInfoDto} from "../../domain/UserInfoDto";
import {ReservationFormComponent} from "../../shared/reservation-form/reservation-form.component";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  cars: Car[] = [];

  // userInfo: UserInfoDto;

  constructor(private dialog: MatDialog, private authService: AuthService, private carService: CarService) {
  }

  ngOnInit(): void {
    this.carService.getAllCars().subscribe({
      next: data => this.cars = data,
      error: () => console.log("something gone wrong")
    });
  }

  async reserveCarDialog(carId: number) {
    let userInfo: UserInfoDto | undefined = await this.carService.getUserInfo(carId)
    if (userInfo == undefined || !this.permitToReserve()) {
      return;
    }

    this.carService.getCar(carId).pipe().subscribe({
      next: (data) => {
        const dialogRef = this.dialog.open(ReservationFormComponent, {data: {tempId: carId, car: data, user: userInfo}});
        dialogRef.afterClosed().subscribe({
          //next: () => this.ngOnInit()
        });
      }
    });
  }

  isUserLoggedIn(): boolean {
    return this.authService.isUserLoggedIn();
  }

  permitToReserve() {
    return this.authService.getRole().includes('CLIENT') && this.authService.isUserLoggedIn();
  }

}
