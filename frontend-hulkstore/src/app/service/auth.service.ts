import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usuario } from './../models/usuario';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private url = 'http://localhost:8080/oauth/token';
  private usuario: Usuario;
  private token: string;
  private credenciales = btoa(
    'frontend-hulkstore' + ':' + '12345'
  );
  private params = new URLSearchParams();
  private httpHeaders: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded',
    Authorization: 'Basic ' + this.credenciales,
  });

  constructor(private http: HttpClient) {}

  public login(usuario: Usuario): Observable<any> {
    this.params.set('grant_type', 'password');
    this.params.set('username', usuario.username);
    this.params.set('password', usuario.password);
    //  console.log(this.params.toString());
    return this.http.post(this.url, this.params.toString(), {
      headers: this.httpHeaders,
    });
  }

  public guardarUsuario(accessToken: string): void {
    const payload = this.obtenerToken(accessToken);
    this.usuario = new Usuario();
    this.usuario.nombre = payload.nombre;
    this.usuario.apellido = payload.apellido;
    this.usuario.email = payload.email;
    this.usuario.username = payload.user_name;
    this.usuario.roles = payload.authorities;
    sessionStorage.setItem('usuario', JSON.stringify(this.usuario));
  }
  public guardarToken(accessToken: string): void {
    this.token = accessToken;
    sessionStorage.setItem('token', this.token);
  }

  public obtenerToken(accessToken: string): any {
    if (accessToken != null) {
      return JSON.parse(atob(accessToken.split('.')[1]));
    }
    return null;
  }

  public get getUsuario(): Usuario {
    if (this.usuario != null) {
      return this.usuario;
    } else if (
      this.usuario == null &&
      sessionStorage.getItem('usuario') != null
    ) {
      this.usuario = JSON.parse(sessionStorage.getItem('usuario')) as Usuario;
      return this.usuario;
    }
    return new Usuario();
  }

  public get getToken(): string {
    if (this.token != null) {
      return this.token;
    } else if (this.token == null && sessionStorage.getItem('token') != null) {
      this.token = sessionStorage.getItem('token');
      return this.token;
    }
    return null;
  }

  public isAuthenticated(): boolean {
    const payload = this.obtenerToken(this.getToken);
    if (payload != null && payload.user_name && payload.user_name.length > 0) {
      return true;
    }
    return false;
  }

  public logout(): void {
    this.usuario = null;
    this.token = null;
    sessionStorage.clear();

    /*sessionStorage.removeItem('token');
    sessionStorage.removeItem('usuario');*/
  }

  public hasRole(role: string): boolean {
    if (this.getUsuario.roles.includes(role)) {
      return true;
    }
    return false;
  }
}
