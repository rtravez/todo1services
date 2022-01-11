import { Factura } from './factura';
import { Region } from './region';

export class Empleado {
  id: number;
  cedula: number;
  nombre: string;
  apellido: string;
  createAt: Date;
  email: string;
  foto: string;
  region: Region;
  facturas: Factura[];

  constructor() {}
}
