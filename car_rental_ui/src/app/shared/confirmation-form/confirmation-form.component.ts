import {Component} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FleetComponent} from "../../pages/fleet/fleet.component";

@Component({
  selector: 'app-confirmation-form',
  templateUrl: './confirmation-form.component.html',
  styleUrls: ['./confirmation-form.component.scss']
})
export class ConfirmationFormComponent {

  constructor(public dialogRef: MatDialogRef<FleetComponent>) {
  }

}
