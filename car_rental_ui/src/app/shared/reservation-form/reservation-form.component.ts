import {Component, Inject, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FleetComponent} from "../../pages/fleet/fleet.component";
import {ModalDialogData} from "../modalDialogData";
import {ReservationDialogData} from "../reservationDialogData";
import {ReservationService} from "../../services/reservation.service";
import {AuthService} from "../../services/auth.service";
import {ReservationDto} from "../../domain/reservationDto";

@Component({
  selector: 'app-reservation-form',
  templateUrl: './reservation-form.component.html',
  styleUrls: ['./reservation-form.component.scss']
})
export class ReservationFormComponent {

  total: number = 0;
  startDayControl = new FormControl(new Date());
  endDayControl = new FormControl(new Date());

  reservationForm: FormGroup = new FormGroup({
    startDay: this.startDayControl,
    endDay: this.endDayControl,
  });

  constructor(private dialogRef: MatDialogRef<FleetComponent>,
              @Inject(MAT_DIALOG_DATA) public data: ReservationDialogData,
              private authService: AuthService, private reservationService: ReservationService) {
  }

  /*  ngOnInit(): void {
      console.log("on init data: " + this.data.user.name);
    }*/

  submit(): void {
    if (!this.authService.isUserLoggedIn() || !this.authService.getRole().includes("CLIENT") ||
      this.reservationForm.invalid) {
      return;
    }

    let reservation: ReservationDto = {
      startDay: this.startDayControl.value ?? new Date(),
      endDay: this.endDayControl.value ?? new Date(),
      carId: this.data.car.id,
    };
    this.reservationService.makeReservation(reservation).subscribe({
      next: () => {
        this.dialogRef.close();
        this.reservationForm.reset();
      },
      error: () => "something gone wrong"
    })
    console.log("reservation posted");
  }

  totalPaymentCalculator() {
    let start = this.startDayControl.value;
    let end = this.endDayControl.value;
    console.log("start: " + start + " end: " + end)
    if (start == null || end == null) {
      this.total = 0;
    } else {
      let days = Math.ceil(((Date.UTC(end.getFullYear(), end.getMonth(), end.getDate()) -
        Date.UTC(start.getFullYear(), start.getMonth(), start.getDate())) / (1000 * 60 * 60 * 24)) + 1);
      this.total = days * this.data.car.cost.perDay + this.data.car.cost.insurance;
    }
  }
}
