export interface IPintor {
  id?: number;
  nombre?: string;
  apellidos?: string;
  sueldo?: number;
  experiencia?: number;
}

export class Pintor implements IPintor {
  constructor(public id?: number, public nombre?: string, public apellidos?: string, public sueldo?: number, public experiencia?: number) {}
}
