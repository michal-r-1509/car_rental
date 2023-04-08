import {Car} from "../domain/car";
import {UserDto} from "../domain/userDto";

export interface ReservationDialogData{
  tempId: number;
  car: Car;
  user: UserDto;
}
