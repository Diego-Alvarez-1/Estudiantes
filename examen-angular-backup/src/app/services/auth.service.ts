import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('currentUser') || 'null'));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  login(usuario: string, password: string): Observable<any> {
    return this.http.get<any[]>('/assets/data/credenciales.json')
      .pipe(map(credenciales => {
        const user = credenciales.find(u => u.usuario === usuario && u.password === password);
        if (user) {
          const userSession = { usuario: user.usuario, loggedIn: true };
          localStorage.setItem('currentUser', JSON.stringify(userSession));
          this.currentUserSubject.next(userSession);
          return userSession;
        }
        return null;
      }));
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('currentUser');
  }
}