import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmpleadoFormComponent } from './empleado/empleado-form.component';
import { EmpleadoComponent } from './empleado/empleado.component';
import { DetalleFacturaComponent } from './facturas/detalle-factura.component';
import { FacturasComponent } from './facturas/facturas.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './usuarios/guards/auth.guard';
import { RoleGuard } from './usuarios/guards/role.guard';

const routes: Routes = [
  { path: 'empleado', component: EmpleadoComponent },
  { path: 'empleado/page/:page', component: EmpleadoComponent }, //paginator
  {
    path: 'empleado/form',
    component: EmpleadoFormComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_ADMIN' },
  },
  {
    path: 'empleado/form/:id',
    component: EmpleadoFormComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_ADMIN' },
  },
  { path: 'login', component: LoginComponent },
  {
    //Es la ruta como se muestra la pagina
    path: 'facturas/form/:id',
    component: DetalleFacturaComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_USER' },
  },
  {
    path: 'facturas/:empleadoId',
    component: FacturasComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_ADMIN' },
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
