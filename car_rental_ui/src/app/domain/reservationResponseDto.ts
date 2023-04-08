export interface ReservationResponseDto{
  id: number;
  brand: string;
  model: string;
  name: string;
  address: string;
  phoneNumber: string;
  startDay: Date;
  endDay: Date;
  daysAmount: number;
  totalCost: number;
}
