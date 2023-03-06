import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstraints} from "../config/apiConstraints";
import {Observable} from "rxjs";
import {Car} from "../domain/car";

@Injectable({
  providedIn: 'root'
})
export class CarService {

  constructor(private http: HttpClient, private apiConstraints: ApiConstraints) {
  }

  createCar(car: Car): Observable<void> {
    return this.http.post<void>(this.apiConstraints.apiUrl + "cars/new", car);
  }

  updateCar(car: Car, id: number): Observable<void>{
    return this.http.put<void>(this.apiConstraints.apiUrl + "cars/" + id, car);
  }
}
