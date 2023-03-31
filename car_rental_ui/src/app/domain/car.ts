export interface Car {
  id: number;
  brand: string;
  model: string;
  available: boolean;
  carDetails: {
    seats: number;
    gearboxType: string;
    fuelType: string;
  };
  cost: {
    perDay: number;
    insurance: number;
  };
}
