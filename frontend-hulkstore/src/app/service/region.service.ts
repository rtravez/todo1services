import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Region } from '../models/region';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class RegionService {
  private url = 'http://localhost:8080/api/regiones';
  private httpHeaders: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {}

  getRegiones(): Observable<Region[]> {
    return this.http.get<Region[]>(this.url);
  }
}
