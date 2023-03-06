import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {CarFormComponent} from "../../shared/car-form/car-form.component";

@Component({
  selector: 'app-fleet',
  templateUrl: './fleet.component.html',
  styleUrls: ['./fleet.component.scss']
})
export class FleetComponent implements OnInit{
  // animal: string;
  // name: string;
  title: string = "Add new car";
  id: number = 0;

  constructor(private dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(CarFormComponent, {data:{title: this.title, tempId: this.id}});

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // this.title = result;

    });
  }
}
