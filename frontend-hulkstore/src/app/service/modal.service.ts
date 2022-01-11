import { EventEmitter, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ModalService {
  modal = false;

  private notificarUploads = new EventEmitter<any>();

  constructor() {}

  get notificarUpload(): EventEmitter<any> {
    return this.notificarUploads;
  }

  public abrirModal(): void {
    this.modal = true;
  }

  public cerrarModal(): void {
    this.modal = false;
  }
}
