import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { BehaviorSubject, Observable, catchError, map, of } from 'rxjs';
import { API_KEY, GMAP_GEOCODE_URL, GMAP_JS_URL, locationURL, mapURL } from '../endpoint.constants';
import { MapGeocoder } from '@angular/google-maps';
import { DistanceDTO, StoreDTO } from '../model/map.model';
import { ErrorService } from './error.service';

@Injectable({
  providedIn: 'root'
})
export class MapService {

  APIStatus$: BehaviorSubject<Boolean> =  new BehaviorSubject(new Boolean(false))
  // APIStatus$: Observable<Boolean> = this.APIStatusSubject.asObservable();
  http = inject(HttpClient);
  geocoder = inject(MapGeocoder);
  error = inject(ErrorService);

  constructor() {
    this.http.jsonp(GMAP_JS_URL + API_KEY, 'callback')
    .pipe(
      map(() => {
      console.log('>> [INFO] GMAP JS API LOADED IN MAP SERVICE');
      return true}
      ),
      catchError((resp) => {
        console.log('>> [INFO] GMAP JS API ERROR IN MAP SERVICE');
        console.count(resp);
        return of(false);
      })
    ).subscribe( loaded => {
      this.APIStatus$.next(loaded);
    })
  }

  getAllStores(): Observable<StoreDTO[]> {
    console.log('>> [INFO] Getting All Pokemart Stores');
    return this.http.get<StoreDTO[]>(locationURL + 'stores')
    .pipe(catchError(err => this.error.httpArrErrorHandler(err)));
  }

  //TODO: HANDLE ERROR
  getDistance(dest: google.maps.LatLngLiteral): Observable<DistanceDTO>{
    console.log('>> [INFO] Getting Distance Cost to Destination',dest);
    return this.http.post<DistanceDTO>(mapURL + 'distance',dest);
  }

  geocodeAddress(address: string): Observable<any>{ //google.maps.LatLngLiteral
    console.log('>> [INFO] Geocoding:',address);
    return  this.http.get<any>(GMAP_GEOCODE_URL + API_KEY + '&address=' + address)
    .pipe(map((resp) => resp.results[0].geometry.location)) //TODO: PRINT OUT ERRORS/HANDLE ERRORS
  }
  
}
