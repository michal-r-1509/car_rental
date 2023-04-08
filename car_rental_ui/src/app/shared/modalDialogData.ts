import {Car} from "../domain/car";
import {UserDto} from "../domain/userDto";

export interface ModalDialogData{
  title: string;
  tempId: number;
  car: Car;
}
