import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { Observable, catchError, map, of } from 'rxjs';
import { API_KEY, GMAP_JS_URL, center_ISS } from 'src/app/endpoint.constants';
import { GoogleMap } from '@angular/google-maps';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css'],
})
export class LocationComponent {
  http = inject(HttpClient);
  apiLoaded$!: Observable<boolean>;

  HQmarker = {
    position: center_ISS,
    label: {
      color: 'white',
      text: 'PokeMart HQ',
    },
  };




  HQmarkerOptions!: google.maps.MarkerOptions
  mapOptions: google.maps.MapOptions = {
    mapId: '4e4dc093ebd5865d',
    center: center_ISS,
    zoom: 16,
  };
  ngOnInit(): void {
    this.apiLoaded$ = this.http
      .jsonp(
      GMAP_JS_URL + API_KEY,
        'callback'
      )
      .pipe(
        map(() => {
          console.log('>> [INFO] GMAP JS API LOADED');
          this.HQmarkerOptions = {
            icon: {
              path:'M3.75 0a1 1 0 0 0-.8.4L.1 4.2a.5.5 0 0 0-.1.3V15a1 1 0 0 0 1 1h14a1 1 0 0 0 1-1V4.5a.5.5 0 0 0-.1-.3L13.05.4a1 1 0 0 0-.8-.4h-8.5ZM8.5 4h6l.5.667V5H1v-.333L1.5 4h6V1h1v3ZM8 7.993c1.664-1.711 5.825 1.283 0 5.132-5.825-3.85-1.664-6.843 0-5.132Z',
              fillColor: 'green',
              fillOpacity: 1,
              strokeWeight: 2,
              strokeColor:"white",
              rotation: 0,
              scale: 2,
              anchor: new google.maps.Point(0,0),
            },
          };
          return true;
        }),
        catchError(() => {
          console.log('>> [INFO] GMAP JS API ERROR');
          return of(false);
        })
      );
  }
}


// M2.97 1.35A1 1 0 0 1 3.73 1h8.54a1 1 0 0 1 .76.35l2.609 3.044A1.5 1.5 0 0 1 16 5.37v.255a2.375 2.375 0 0 1-4.25 1.458A2.371 2.371 0 0 1 9.875 8 2.37 2.37 0 0 1 8 7.083 2.37 2.37 0 0 1 6.125 8a2.37 2.37 0 0 1-1.875-.917A2.375 2.375 0 0 1 0 5.625V5.37a1.5 1.5 0 0 1 .361-.976l2.61-3.045zm1.78 4.275a1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0 1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0 1.375 1.375 0 1 0 2.75 0V5.37a.5.5 0 0 0-.12-.325L12.27 2H3.73L1.12 5.045A.5.5 0 0 0 1 5.37v.255a1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0zM1.5 8.5A.5.5 0 0 1 2 9v6h12V9a.5.5 0 0 1 1 0v6h.5a.5.5 0 0 1 0 1H.5a.5.5 0 0 1 0-1H1V9a.5.5 0 0 1 .5-.5zm2 .5a.5.5 0 0 1 .5.5V13h8V9.5a.5.5 0 0 1 1 0V13a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V9.5a.5.5 0 0 1 .5-.5z