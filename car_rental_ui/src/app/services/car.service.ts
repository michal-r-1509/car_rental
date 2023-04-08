import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstraints} from "../config/apiConstraints";
import {map, Observable} from "rxjs";
import {Car} from "../domain/car";
import {UserInfoDto} from "../domain/UserInfoDto";

@Injectable({
  providedIn: 'root'
})
export class CarService {

  constructor(private http: HttpClient, private apiConstraints: ApiConstraints) {
  }

  createCar(car: Car): Observable<Car> {
    return this.http.post<Car>(this.apiConstraints.apiUrl + "cars/new", car);
  }

  updateCar(car: Car, id: number): Observable<Car>{
    return this.http.put<Car>(this.apiConstraints.apiUrl + "cars/" + id, car);
  }

  getUserCars(): Observable<Car[]> {
    return this.http.get<Car[]>(this.apiConstraints.apiUrl + "cars/currentUser");
  }

  getAllCars(): Observable<Car[]> {
    return this.http.get<Car[]>(this.apiConstraints.apiUrl + "cars");
  }

  getCar(id: number): Observable<Car> {
    return this.http.get<Car>(this.apiConstraints.apiUrl + "cars/" + id);
  }

  getUserInfo(carId: number): Promise<UserInfoDto | undefined>{
    return this.http.get<UserInfoDto>(this.apiConstraints.apiUrl + "cars/user/" + carId).toPromise();
  }

  deleteCar(id: number): Observable<void> {
    return this.http.delete<void>(this.apiConstraints.apiUrl + "cars/" + id);
  }
}
