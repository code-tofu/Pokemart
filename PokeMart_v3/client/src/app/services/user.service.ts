import { Injectable, inject } from '@angular/core';
import { AuthRequest, AuthResponse, RegisterRequest, UserProfile } from '../model/auth.model';
import { Observable, Subject, firstValueFrom, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { authURL, userURL } from '../endpoint.constants';
import { ErrorService } from './error.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(){ }
  http = inject(HttpClient);
  error = inject(ErrorService);
  accessToken!: string;
  username!:string;
  userID!:string;
  user!: UserProfile;
  role!: string

  authenticationStatus: Subject<boolean> = new Subject<boolean>();

  authenticate(auth:AuthRequest){
    console.info(">> [INFO] Authenticating:", auth.username);
    firstValueFrom(this.postAuthRequest(auth))
    .then( (response:AuthResponse) => {
      this.accessToken = response.accessToken
      console.info(">> [INFO] authToken", this.accessToken);
      this.username = auth.username;
      console.info(">> [INFO] Authenticated|Username:", this.username);
      this.userID = response.userID;
      console.info(">> [INFO] Authenticated|UserID:", this.userID);
      this.role = response.role;
      console.info(">> [INFO] Authenticated|Role:", this.role);
      this.getUserDetails(this.username).subscribe( (response) => {
        this.user = response;
      })
      this.authenticationStatus.next(true)
    })
    .catch((err) => {
      if(err.status == 401) console.error("UNAUTHORIZED: WRONG/USERNAME PASSWORD");
      if(err.status == 500) alert("Connection Issue: Please Try Again Later");
      this.authenticationStatus.next(false);
    });
  } 

  signup(signup: RegisterRequest){
    console.info(">> [INFO] Authenticating:", signup);
    firstValueFrom(this.postRegisterRequest(signup))
    .then( (resp:string) => {
      console.info(">> [INFO] Created ID:", resp);})
    .catch((err) => {
      this.error.httpErrorHandler(err)
    });
  }

  postAuthRequest(auth:AuthRequest):Observable<AuthResponse>{
    return this.http.post<any>( authURL + 'login', auth);
  }

  postRegisterRequest(signup: RegisterRequest):Observable<string>{
    return this.http.post<any>( authURL + 'registerCustomer', signup);
  }

  getUserDetails(username:string): Observable<UserProfile>{
    return this.http.get<UserProfile>(userURL + 'profile/' + username);
  }


}
