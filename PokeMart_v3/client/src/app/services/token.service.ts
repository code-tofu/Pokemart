import { Injectable } from '@angular/core';
import { NAME_KEY, TOKEN_KEY, USER_KEY } from '../endpoint.constants';
import { UserProfile } from '../model/auth.model';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  constructor() { }

  clearStorage(): void {
    window.sessionStorage.clear();
  }

  saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  saveUser(user: UserProfile): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  getUser(): UserProfile | null {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user) as UserProfile;
    }
    return null;
  }

  saveName(name: string): void {
    window.sessionStorage.removeItem(NAME_KEY);
    window.sessionStorage.setItem(NAME_KEY, name);
  }

  getName():string | null {
    return window.sessionStorage.getItem(NAME_KEY);
  }
}
