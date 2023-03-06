import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RegistrationComponent} from "./pages/registration/registration.component";
import {PageNotFoundComponent} from "./pages/page-not-found/page-not-found.component";
import {LoginComponent} from "./pages/login/login.component";
import {LogoutComponent} from "./pages/logout/logout.component";
import {AccountComponent} from "./pages/account/account.component";
import {HomeComponent} from "./pages/home/home.component";
import {FleetComponent} from "./pages/fleet/fleet.component";

const routes: Routes = [
  {path: 'registration', component: RegistrationComponent},
  {path: 'login', component: LoginComponent, pathMatch: 'full'},
  {path: 'logout', component: LogoutComponent},
  {path: 'account', component: AccountComponent},
  {path: 'fleet', component: FleetComponent},
  {path: '**', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
