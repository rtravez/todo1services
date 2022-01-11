import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Empleado } from '../models/empleado';

@Injectable({
  providedIn: 'root',
})
export class EmpleadoService {
  private url = 'http://localhost:8080/api/empleados';

  constructor(private http: HttpClient, private router: Router) {}

  getEmpleados(page: number): Observable<any> {
    return this.http.get(this.url + '/page/' + page).pipe(
      tap((response: any) => {
        (response.content as Empleado[]).forEach((empleado) => {
          console.log('tap 1');
          console.log(empleado.nombre);
          console.log(empleado.apellido);
        });
      }),
      map((response: any) => {
        (response.content as Empleado[]).map((empleado) => {
          empleado.nombre = empleado.nombre.toUpperCase();
          empleado.apellido = empleado.apellido.toUpperCase();
          empleado.apellido = empleado.apellido.toUpperCase();
          return empleado;
        });
        return response;
      }),
      tap((response) => {
        (response.content as Empleado[]).forEach((empleado) => {
          console.log('tap 2');
          console.log(empleado.nombre);
          console.log(empleado.apellido);
        });
      })
    );
  }

  public create(empleado: Empleado): Observable<Empleado> {
    return this.http.post(this.url, empleado).pipe(
      map((response: any) => response.empleado as Empleado),
      catchError((e) => {
        if (e.status === 400) {
          return throwError(e);
        }
        console.log(e.error.mensaje);
        return throwError(e);
      })
    );
  }

  getEmpleado(id: number): Observable<Empleado> {
    return this.http.get<Empleado>(`${this.url}/${id}`).pipe(
      catchError((e) => {
        if (e.status != 401 && e.error.mensaje) {
          this.router.navigate(['/empleado']);
          console.error(e.error.mensaje);
        }

        return throwError(e);
      })
    );
  }

  public update(empleado: Empleado): Observable<any> {
    return this.http.put<any>(`${this.url}/${empleado.id}`, empleado).pipe(
      catchError((e) => {
        if (e.status === 400) {
          return throwError(e);
        }

        console.log(e.error.mensaje);
        return throwError(e);
      })
    );
  }

  public delete(id: number): Observable<Empleado> {
    return this.http.delete<Empleado>(`${this.url}/${id}`).pipe(
      catchError((e) => {
        console.log(e.error.mensaje);
        return throwError(e);
      })
    );
  }

  public subirArchivo(archivo: File, id: number): Observable<HttpEvent<{}>> {
    const formData = new FormData();
    formData.append('archivo', archivo);
    formData.append('id', id.toString());

    const req = new HttpRequest('POST', `${this.url}/upload`, formData, {
      reportProgress: true,
    });

    return this.http.request(req);
  }
}
