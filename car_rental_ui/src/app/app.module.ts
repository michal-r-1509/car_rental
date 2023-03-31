import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RegistrationComponent} from './pages/registration/registration.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {ApiConstraints} from "./config/apiConstraints";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatRadioModule} from "@angular/material/radio";
import {PageNotFoundComponent} from './pages/page-not-found/page-not-found.component';
import {HeaderComponent} from './pages/header/header.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {ToastrModule} from "ngx-toastr";
import {LoginComponent} from './pages/login/login.component';
import {LogoutComponent} from './pages/logout/logout.component';
import {AccountComponent} from './pages/account/account.component';
import {AuthService} from "./services/auth.service";
import {HttpRequestInterceptor} from "./services/http-request-interceptor";
import {HomeComponent} from './pages/home/home.component';
import {MatCheckboxModule} from "@angular/material/checkbox";
import {FleetComponent} from './pages/fleet/fleet.component';
import {MatCardModule} from "@angular/material/card";
import {AddCarComponent} from './pages/add-car/add-car.component';
import {MatDialogModule} from "@angular/material/dialog";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatIconModule} from "@angular/material/icon";
import {MAT_DATE_FORMATS, MatDateFormats, MatNativeDateModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {CarFormComponent} from './shared/car-form/car-form.component';

const CUSTOM_DATE_FORMATS: MatDateFormats = {
  parse: {
    dateInput: 'l, LTS'
  },
  display: {
    dateInput: 'DD-MM-YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  }
}

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    PageNotFoundComponent,
    HeaderComponent,
    LoginComponent,
    LogoutComponent,
    AccountComponent,
    HomeComponent,
    FleetComponent,
    AddCarComponent,
    CarFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatRadioModule,
    ReactiveFormsModule,
    MatToolbarModule,
    ToastrModule.forRoot({
      maxOpened: 2,
      preventDuplicates: true,
      timeOut: 2000,
      closeButton: true,
      autoDismiss: true,
      newestOnTop: true,
      positionClass: "toast-center-center"
    }),
    MatCheckboxModule,
    MatCardModule,
    MatDialogModule,
    MatDatepickerModule,
    MatIconModule,
    MatNativeDateModule,
    MatSelectModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [ApiConstraints, AuthService, [
    {provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true}],
    {provide: MAT_DATE_FORMATS, useValue: CUSTOM_DATE_FORMATS}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
