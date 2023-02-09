import { Component } from '@angular/core';
import {LoginService} from "../user/service/login.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  constructor(public loginService: LoginService) {
  }
}
