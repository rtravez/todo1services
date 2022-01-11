import { Component, OnInit, ViewChild } from '@angular/core';
import { Empleado } from '../models/empleado';
import { EmpleadoService } from '../service/empleado.service';
import { ModalService } from '../service/modal.service';
import { Table } from 'primeng/table';
import { tap } from 'rxjs/operators';

import Swal from 'sweetalert2';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-empleado',
  templateUrl: './empleado.component.html',
  styleUrls: ['./empleado.component.css'],
})
export class EmpleadoComponent implements OnInit {
  @ViewChild('dt') table: Table;
  empleados: Empleado[];
  paginador: any;
  empleadoSelecionado: Empleado;
  modal: boolean;

  constructor(
    private empleadoService: EmpleadoService,
    private activatedRoute: ActivatedRoute,
    public modalService: ModalService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((params) => {
      let page: number = +params.get('page'); // + Convierte en number
      if (!page) {
        page = 0;
      }
      this.empleadoService
        .getEmpleados(page)
        .pipe(
          tap((response) => {
            (response.content as Empleado[]).forEach((empleado) => {
              console.log('EmpleadoComponent tap 3');
              console.log(empleado.nombre);
              console.log(empleado.apellido);
            });
          })
        )
        .subscribe((response) => {
          this.empleados = response.content as Empleado[];
          this.paginador = response;
        });
    });

    this.modalService.notificarUpload.subscribe((empleado) => {
      this.empleados = this.empleados.map((empleadoOriginal) => {
        if (empleado.id === empleadoOriginal.id) {
          empleadoOriginal.foto = empleado.foto;
        }
        return empleadoOriginal;
      });
    });
  }

  public delete(empleado: Empleado): void {
    Swal.fire({
      title: 'Cuidado:',
      text: `¿Seguro que desea eliminar a ${empleado.nombre} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
    }).then((result) => {
      if (result.value) {
        this.empleadoService.delete(empleado.id).subscribe((response) => {
          this.empleados = this.empleados.filter((cli) => cli !== empleado);
          Swal.fire(
            'Eliminado:',
            `Empleado ${empleado.nombre} eliminado con éxito.`,
            'success'
          );
        });
      }
    });
  }

  public abrirModal(empleado: Empleado): void {
    this.empleadoSelecionado = empleado;
    this.modalService.abrirModal();
  }
}
