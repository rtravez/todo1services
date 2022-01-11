import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styles: [],
})
export class HeaderComponent implements OnInit {
  title = 'Spring - Angular';

  constructor(public authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  public logout(): void {
    const usuario = this.authService.getUsuario;
    this.authService.logout();
    Swal.fire(
      'Logout',
      `Adiós ${usuario.username} has cerrado sesión con éxito`,
      'success'
    );
    this.router.navigate(['/login']);
  }
}
