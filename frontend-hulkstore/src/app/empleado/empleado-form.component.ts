import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Region } from '../models/region';
import { EmpleadoService } from '../service/empleado.service';
import { RegionService } from '../service/region.service';
import { Empleado } from '../models/empleado';

@Component({
  selector: 'app-empleado-form',
  templateUrl: './empleado-form.component.html',
  styleUrls: ['./empleado-form.component.css'],
})
export class EmpleadoFormComponent implements OnInit {
  empleado: Empleado;
  regiones: Region[] = [];
  titulo: string = 'Crear empleado';
  errores: string[];

  constructor(
    private empleadoService: EmpleadoService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private regionService: RegionService
  ) {}

  ngOnInit(): void {
    this.empleado = new Empleado();
    this.cargarEmpleado();
  }
  public cargarEmpleado(): void {
    this.activatedRoute.paramMap.subscribe((params) => {
      const id: number = +params.get('id');
      console.log(id);
      if (id) {
        this.empleadoService.getEmpleado(id).subscribe((c) => {
          this.empleado = c;
          console.log(c);
          console.log(c.region);
        });
      }
    });

    this.regionService
      .getRegiones()
      .subscribe((regiones) => (this.regiones = regiones));
  }

  public create(): void {
    console.log(this.empleado);
    console.log(this.empleado.region);
    this.empleadoService.create(this.empleado).subscribe(
      (empleado) => {
        Swal.fire(
          'Nuevo:',
          `Empleado ${empleado.nombre} creado con éxito`,
          'success'
        );

        this.router.navigate(['/empleado']);
      },
      (err) => {
        if (err.status === 400) {
          this.errores = err.error.errors as string[];
          console.error(err.status);
          console.error(err.error.errors);
        }
      }
    );
  }

  public update(): void {
    console.log(this.empleado);
    console.log(this.empleado.region);
    this.empleado.facturas = null;
    this.empleadoService.update(this.empleado).subscribe(
      (response) => {
        Swal.fire(
          'Actualizado:',
          `Empleado ${response.empleado.nombre} actualizado con éxito`,
          'success'
        );

        this.router.navigate(['/empleado']);
      },
      (err) => {
        if (err.status === 400) {
          this.errores = err.error.errors as string[];
          console.error(err.status);
          console.error(err.error.errors);
        }
      }
    );
  }
}
