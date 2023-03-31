import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {CarService} from "../../services/car.service";
import {Car} from "../../domain/car";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit{

  cars: Car[] = [];

  constructor(private authService: AuthService, private carService: CarService) {
  }

  ngOnInit(): void {
    this.carService.getAllCars().subscribe({
      next: data => this.cars = data,
      error: () => console.log("something gone wrong")
    });
  }

  isUserLoggedIn(): boolean{
    return this.authService.isUserLoggedIn();
  }

  reserveCarDialog(id: number) {
    console.log("reservation dialog open");
  }

  permitToReserve() {
    return this.authService.getRole().includes('CLIENT') && this.authService.isUserLoggedIn();
  }
}
