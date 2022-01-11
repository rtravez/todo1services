import { Producto } from './producto';

export class ItemFactura {
  public id: number;
  public cantidad: number = 1;
  public importe: number;
  public producto: Producto;

  constructor() {}

  public calcularImporte(): number {
    return this.cantidad * this.producto.precio;
  }
}
