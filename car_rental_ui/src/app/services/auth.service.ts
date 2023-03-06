import {Injectable} from '@angular/core';
import {map, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {ApiConstraints} from "../config/apiConstraints";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private apiConstraints: ApiConstraints) {
  }

  authenticate(email: string, password: string): Observable<void> {
    const formData = new FormData();
    formData.append('email', email);
    formData.append('password', password);
    console.log(email + " :: " + password);
    return this.http.post<any>(this.apiConstraints.authUrl + "login", formData).pipe(
      map(data => {
        sessionStorage.setItem('username', email);
        sessionStorage.setItem('role', data.role);
        return data;
      }));
  }

  getUsername(){
    const username = sessionStorage.getItem('username');
    return username == null ? '' : username;
  }

  getRole(){
    const role = sessionStorage.getItem('role');
    return role == null ? '' : role.toUpperCase();
  }

  isUserLoggedIn(): boolean{
    const username = sessionStorage.getItem('username');
    return username !== null;
  }

  userLogout(): Observable<void>{
    sessionStorage.clear();
    return this.http.post<any>(this.apiConstraints.authUrl + 'logout', null).pipe(
      map(() => console.log("logged out")));
  }

  clear() {
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('role');
  }

/*  updateUserDetails(userDetails: UpdateUserRequest): Observable<void> {
    const username = sessionStorage.getItem('username');
    userDetails.email = username == null ? '' : username;
    return this.http.post<any>(this.apiConstraints.apiUrl + 'users/current', userDetails).pipe();
  }*/
}
