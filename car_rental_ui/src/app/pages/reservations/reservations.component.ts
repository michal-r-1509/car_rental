import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {ReservationResponseDto} from "../../domain/reservationResponseDto";
import {ReservationService} from "../../services/reservation.service";
import {AuthService} from "../../services/auth.service";
import {ConfirmationFormComponent} from "../../shared/confirmation-form/confirmation-form.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.scss']
})
export class ReservationsComponent implements OnInit, AfterViewInit{
  displayedColumns: string[] = ['id', 'brand', 'model', 'name', 'address', 'phone_number', 'start_day',
    'end_day', 'days_amount', 'total_cost', 'action'];
  dataSource: MatTableDataSource<ReservationResponseDto> = new MatTableDataSource();

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  // data: ReservationResponseDto[] = [];

  constructor(private dialog: MatDialog, private reservationService: ReservationService,
              private authService: AuthService) {
    // Create 100 users
    //const users = Array.from({length: 100}, (_, k) => createNewUser(k + 1));

    // Assign the data to the data source for the table to render
    //this.dataSource = new MatTableDataSource(users);
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  ngOnInit(): void {
    this.reservationService.getReservations().subscribe({
      next: (data) => {
        this.dataSource = new MatTableDataSource(data);
      }
    })
  }

  deleteReservation(id: number) {
    const dialogRef = this.dialog.open(ConfirmationFormComponent);
    dialogRef.afterClosed().subscribe({
      next: (confirmation) => {
        if (confirmation) {
          this.reservationService.deleteReservation(id).subscribe({
            next: () => this.ngOnInit()
          });
        } else {
          return;
        }
      }
    })
  }
}

/** Builds and returns a new User. */
/*function createNewUser(id: number): ReservationResponseDto {
  const name =
    NAMES[Math.round(Math.random() * (NAMES.length - 1))] +
    ' ' +
    NAMES[Math.round(Math.random() * (NAMES.length - 1))].charAt(0) +
    '.';

  return {
    id: id.toString(),
    name: name,
    progress: Math.round(Math.random() * 100).toString(),
    fruit: FRUITS[Math.round(Math.random() * (FRUITS.length - 1))],
  };

}*/
