import { Empleado } from './empleado';
import { ItemFactura } from './item-factura';

export class Factura {
  id: number;
  descripcion: string;
  observacion: string;
  createAt: Date;
  total: number;
  empleado: Empleado = new Empleado();
  items: ItemFactura[] = [];

  constructor() {}

  calcularGranTotal(): number {
    this.total = 0;
    this.items.forEach((item: ItemFactura) => {
      this.total += item.calcularImporte();
    });
    return this.total;
  }
}
