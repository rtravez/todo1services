import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Producto } from '../models/producto';
import { Factura } from './../models/factura';

@Injectable({
  providedIn: 'root',
})
export class FacturaService {
  private url = 'http://localhost:8080/api/facturas';

  constructor(private http: HttpClient, private router: Router) {}

  public getFactura(id: number): Observable<Factura> {
    return this.http.get<Factura>(`${this.url}/${id}`).pipe(
      catchError((e) => {
        if (e.status != 401 && e.error.mensaje) {
          this.router.navigate(['/facturas']);
          console.error(e.error.mensaje);
        }

        return throwError(e);
      })
    );
  }

  public delete(id: number): Observable<Factura> {
    return this.http.delete<Factura>(`${this.url}/${id}`).pipe(
      catchError((e) => {
        console.log(e.error.mensaje);
        return throwError(e);
      })
    );
  }

  filtrarProductos(term: string): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.url}/filtrar-productos/${term}`);
  }

  public create(factura: Factura): Observable<Factura> {
    return this.http.post(this.url, factura).pipe(
      map((response: any) => response.factura as Factura),
      catchError((e) => {
        if (e.status === 400) {
          return throwError(e);
        }
        console.log(e.error.mensaje);
        return throwError(e);
      })
    );
  }
}
