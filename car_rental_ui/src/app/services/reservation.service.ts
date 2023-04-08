import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstraints} from "../config/apiConstraints";
import {ReservationDto} from "../domain/reservationDto";
import {Observable} from "rxjs";
import * as http from "http";
import {ReservationResponseDto} from "../domain/reservationResponseDto";

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  constructor(private http: HttpClient, private apiConstraints: ApiConstraints) { }

  makeReservation(reservation: ReservationDto): Observable<ReservationResponseDto>{
    return this.http.post<ReservationResponseDto>(this.apiConstraints.apiUrl + 'reservations/new', reservation);
  }

  getReservations(): Observable<ReservationResponseDto[]>{
    return this.http.get<ReservationResponseDto[]>(this.apiConstraints.apiUrl + 'reservations');
  }

  deleteReservation(id: number): Observable<void>{
    return this.http.delete<void>(this.apiConstraints.apiUrl + 'reservations/' + id);
  }

}
