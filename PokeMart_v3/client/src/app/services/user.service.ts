import { Injectable, inject } from '@angular/core';
import { AuthRequest, AuthResponse, RegisterRequest, UserProfile } from '../model/auth.model';
import { Observable, Subject, catchError, firstValueFrom, map, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { authURL, userURL } from '../endpoint.constants';
import { ErrorService } from './error.service';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(){
    this.loadSessionData();
  }
  http = inject(HttpClient);
  error = inject(ErrorService);
  tokenSvc = inject(TokenService);
  accessToken!: string |null;
  userID!:string |null; 
  user!: UserProfile |null;
  role!: string |null; //TODO: FIGURE THIS OUT
  username!:string|null; 
  authenticationStatus: Subject<boolean> = new Subject<boolean>();

  loadSessionData(){
    console.log(">> [INFO]: CHECKING SESSION STORAGE") 
    const storedToken = this.tokenSvc.getToken();
    console.log(">> [INFO]: LOAD FROM SESSION TOKEN: ",storedToken);
    if (storedToken != null) {
      this.accessToken = storedToken as string;
    }
    const storedUser = this.tokenSvc.getUser();
    console.log(">> [INFO]: LOAD FROM SESSION USER: ", storedUser);
    if (storedUser != null) {
      this.user = storedUser as UserProfile;
      this.userID = this.user.userID
    }
    const storedUsername = this.tokenSvc.getName();
    console.log(">> [INFO]: LOAD FROM SESSION NAME: ", storedUsername);
    if (storedUsername != null) {
      this.username = storedUsername as string;
    }
  }

  signout():void{
    this.tokenSvc.clearStorage();
    this.accessToken = null;
    this.user = null;
    this.role = null;
    this.username = null;
  }

  authenticate(auth:AuthRequest){
    console.info(">> [INFO] Authenticating:", auth.username);
    firstValueFrom(this.postAuthRequest(auth))
    .then( (response:AuthResponse) => {
      this.accessToken = response.accessToken
      this.tokenSvc.saveToken(this.accessToken);
      console.info(">> [INFO] authToken", this.accessToken);
      this.username = auth.username; 
      console.info(">> [INFO] Authenticated|Username:", this.username);
      this.tokenSvc.saveName(this.username);
      this.userID = response.userID;
      console.info(">> [INFO] Authenticated|UserID:", this.userID);
      this.role = response.role;
      console.info(">> [INFO] Authenticated|Role:", this.role);
      this.getUserDetails(auth.username).subscribe( (response) => {
        this.user = response;
        this.tokenSvc.saveUser(this.user);
      })
      this.authenticationStatus.next(true)
    })
    .catch((err) => {
      if(err.status == 401) console.error("UNAUTHORIZED: WRONG/USERNAME PASSWORD");
      if(err.status == 500) alert("Connection Issue: Please Try Again Later");
      this.authenticationStatus.next(false);
    });
  } 

  signup(signup: RegisterRequest): Observable<boolean>{
    console.info(">> [INFO] Registering:", signup);
    return this.postRegisterRequest(signup)
    .pipe(
      map((resp:string) => {
        console.info(">> [INFO] Created ID:", resp);
        return true;
    }),
      catchError((err) => {
        if(err.status == 500) alert("Connection Issue: Please Try Again Later");
        return of(false);
      }))
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
