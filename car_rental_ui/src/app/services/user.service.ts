import {Injectable} from '@angular/core';
import {NewUser} from "../domain/newUser";
import {HttpClient} from "@angular/common/http";
import {ApiConstraints} from "../config/apiConstraints";
import {Observable} from "rxjs";
import {UserDto} from "../domain/userDto";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private apiConstraints: ApiConstraints) {
  }

  createUser(newUser: NewUser): Observable<void> {
    return this.http.post<void>(this.apiConstraints.apiUrl + 'users/new', newUser);
    //.subscribe(data => data);
  }

  getCurrentUser(): Observable<UserDto>{
    return this.http.get<UserDto>(this.apiConstraints.apiUrl + 'users/current');
  }

  updateUserDetails(userDetails: UserDto): Observable<void> {
    // const username = sessionStorage.getItem('username');
    // userDetails.email = username == null ? '' : username;
    return this.http.post<any>(this.apiConstraints.apiUrl + 'users/current', userDetails).pipe();
  }

  deleteUser(body: any): Observable<any>{
    return this.http.put<void>(this.apiConstraints.apiUrl + "users/current", body).pipe();
  }
}
