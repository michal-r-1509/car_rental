export class Car{
  // id: number;
  regNo: string;
  brand: string;
  model: string;
  available: boolean;
  techDetails:{
    enginePower: number;
    gearbox: string;
    fuel: string;
    fuelUsage: number;
  };
  usageDetails: {
    seats: number;
    trunkCap: number;
  };
  legalStatus:{
    insuranceEndDate: Date;
    registerDate: Date;
    nextTechReviewDate: Date;
  };
  cost: {
    perDay: number;
    perWeek: number;
    insurance: number;
  };
}
