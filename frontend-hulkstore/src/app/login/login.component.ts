import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Usuario } from '../models/usuario';
import { AuthService } from './../service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  titulo: string;
  usuario: Usuario;

  constructor(private authService: AuthService, private router: Router) {
    this.usuario = new Usuario();
  }

  ngOnInit(): void {
    this.titulo = 'Iniciar';
    if (this.authService.isAuthenticated()) {
      Swal.fire(
        'Login',
        `Hola ${this.authService.getUsuario.username} ya estás autenticado`,
        'info'
      );
      this.router.navigate(['/empleado']);
    }
  }

  public login(): void {
    if (this.usuario.username === null || this.usuario.password === null) {
      Swal.fire('Error Login', 'Username o password incorrectos', 'error');
      return;
    } else {
      this.authService.login(this.usuario).subscribe(
        (response) => {
          // const payload = JSON.parse(atob(response.access_token.split('.')[1]));

          this.authService.guardarUsuario(response.access_token);
          this.authService.guardarToken(response.access_token);

          const usuario = this.authService.getUsuario;
          const token = this.authService.getToken;

          this.router.navigate(['/empleado']);
          Swal.fire('Login', `Bienvenido ${usuario.username}`, 'success');
        },
        (err: any) => {
          if (err.status === 400) {
            Swal.fire('Error Login', `Usuario o clave incorrecta`, 'error');
          }
        }
      );
    }
  }
}
