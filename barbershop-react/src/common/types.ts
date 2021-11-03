export type Customer = {
  id: number;
  login: string;
  password: string;
  firstname: string | null;
  lastname: string | null;
  phoneNumber: string | null;
  city: string | null;
  street: string | null;
  postalCode: string | null;
  role: string | null;
};

export type Service = {
  id: number;
  name: string;
  durationMinutes: number;
  price: number;
};

export type Employee = {
  id: number;
  login: string;
  password: string;
  firstname: string | null;
  lastname: string | null;
  phoneNumber: string | null;
  city: string | null;
  street: string | null;
  postalCode: string | null;
  role: string | null;
  salary: number | null;
  serviceDTOs: Service[] | null;
};

export type Reservation = {
  id: number;
  fromTime: string;
  toTime: string;
  customerDTO: Customer;
  employeeDTO: Employee;
  serviceDTOs: Service[] | null;
  done: boolean | null;
  feedbackText: string | null;
};
