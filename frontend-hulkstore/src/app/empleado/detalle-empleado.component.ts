import { HttpEventType } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Factura } from 'src/app/models/factura';
import { AuthService } from 'src/app/service/auth.service';
import { EmpleadoService } from 'src/app/service/empleado.service';
import { FacturaService } from 'src/app/service/factura.service';
import { ModalService } from 'src/app/service/modal.service';
import Swal from 'sweetalert2';
import { Empleado } from '../models/empleado';

@Component({
  selector: 'app-detalle-empleado',
  templateUrl: './detalle-empleado.component.html',
  styleUrls: ['./detalle-empleado.component.css'],
})
export class DetalleEmpleadoComponent implements OnInit {
  @Input() empleado: Empleado;
  facturas: Factura[];
  archivoSelecionado: File;
  progreso = 0;
  modal: boolean;

  constructor(
    private empleadoService: EmpleadoService,
    private activatedRoute: ActivatedRoute,
    public modalService: ModalService,
    public authService: AuthService,
    private facturaService: FacturaService
  ) {}

  ngOnInit(): void {
    /*this.activatedRoute.paramMap.subscribe((params) => {
      const id: number = +params.get('id');

      if (id) {
        this.empleadoService.getEmpleado(id).subscribe((empleado) => {
          this.empleado = empleado;
        });
      }
    });*/
  }

  public selecionarArchivo(event: any): any {
    this.progreso = 0;
    this.archivoSelecionado = event.target.files[0];
    console.log(this.archivoSelecionado);

    if (this.archivoSelecionado.type.indexOf('image') < 0) {
      Swal.fire('Error', `Tiene  que ser del tipo imagen`, 'error');
      this.archivoSelecionado = null;
    }
  }

  /*public subirArchivo(): any {
    if (!this.archivoSelecionado) {
      Swal.fire('Error', `Debe seleccionar una foto`, 'error');
    } else {
      this.empleadoService
        .subirArchivo(this.archivoSelecionado, this.empleado.id)
        .subscribe((empleado) => {
          this.empleado = empleado;
          Swal.fire(
            'Foto cargada',
            `La foto se ha subido con éxito ${this.empleado.foto}`,
            'success'
          );
        });
    }
  }*/

  public subirArchivo(): any {
    if (!this.archivoSelecionado) {
      Swal.fire('Error', `Debe seleccionar una foto`, 'error');
    } else {
      this.empleadoService
        .subirArchivo(this.archivoSelecionado, this.empleado.id)
        .subscribe((event) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progreso = Math.round((event.loaded / event.total) * 100);
          } else if (event.type === HttpEventType.Response) {
            const response: any = event.body;
            this.empleado = response.empleado as Empleado;

            this.modalService.notificarUpload.emit(this.empleado);
            Swal.fire('Foto cargada', response.mensaje, 'success');
          }
        });
    }
  }

  public cerrarModal(): void {
    this.modalService.cerrarModal();
    this.archivoSelecionado = null;
    this.progreso = 0;
    console.log(this.archivoSelecionado);
  }

  public delete(factura: Factura): void {
    Swal.fire({
      title: 'Cuidado:',
      text: `¿Seguro que desea eliminar la Factura ${factura.id} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
    }).then((result) => {
      if (result.value) {
        this.facturaService.delete(factura.id).subscribe((response) => {
          this.empleado.facturas = this.empleado.facturas.filter(
            (fac) => fac !== factura
          );
          Swal.fire(
            'Eliminado:',
            `Factura ${factura.descripcion} eliminado con éxito.`,
            'success'
          );
        });
      }
    });
  }
}
