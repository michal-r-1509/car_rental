import {Injectable} from '@angular/core';
import {User} from "../../domain/user";
import {HttpClient} from "@angular/common/http";
import {ApiConstraints} from "../../config/apiConstraints";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private apiConstraints: ApiConstraints) {
  }

  createUser(user: User): Observable<void> {
    return this.http.post<void>(this.apiConstraints.apiUrl + "users/new", user);
    //.subscribe(data => data);
  }
}
