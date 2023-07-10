import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, ViewChild, inject } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { HQ_ISS } from 'src/app/endpoint.constants';
import { MapInfoWindow } from '@angular/google-maps';
import { MapService } from 'src/app/services/map.service';
import { StoreDTO } from 'src/app/model/map.model';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css'],
})
export class LocationComponent implements OnDestroy {
  http = inject(HttpClient);
  mapSvc = inject(MapService);

  apiLoaded!: boolean;
  APIStatusSub!:Subscription;
  stores$!: Observable<StoreDTO[]>;

  ngOnInit(): void {
    this.APIStatusSub = this.mapSvc.APIStatus$.subscribe((status) => {
      console.log("[INFO] GMAP JSAPI LOADED:",status.valueOf());
      this.apiLoaded = status.valueOf();
    });
    this.stores$ = this.mapSvc.getAllStores();
  }

  @ViewChild(MapInfoWindow, { static: false }) infoWindow!: MapInfoWindow
  infoOptions!:google.maps.InfoWindowOptions;
  openInfo(marker: any, store: StoreDTO) {
    console.log(marker);
    this.infoOptions = {
      maxWidth:400,
      content:this.generateStoreContent(store)
    }
    this.infoWindow.open(marker)
  }
  generateStoreContent(store:StoreDTO):string {
    return '<div>' +
    '<h6>PokeMart @ ' + store.storeName + '</h6>' +
    '<p><strong>Address: </strong>' + store.storeAddress + '</p>' +
    '<strong>Phone: </strong>' + store.storePhone + '</p>'
    + '</div>'
  }

  ngOnDestroy(): void {
    this.APIStatusSub.unsubscribe
  }
  mapOptions: google.maps.MapOptions = {
    mapId: '4e4dc093ebd5865d',
    center: HQ_ISS,
    zoom: 15,
    streetViewControl: false,
    mapTypeControl: false,
    clickableIcons:false,
  };
  StoreMarkerOptions: google.maps.MarkerOptions ={clickable:true, draggable:false,icon: {
    path:'M8 1a2.5 2.5 0 0 1 2.5 2.5V4h-5v-.5A2.5 2.5 0 0 1 8 1zm3.5 3v-.5a3.5 3.5 0 1 0-7 0V4H1v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V4h-3.5z',
    fillColor: '#cee2fe',
    fillOpacity: 1,
    strokeWeight: 1,
    strokeColor:"black",
    rotation: 0,
    scale: 1.5, 
  }}
  HQMarkerOptions: google.maps.MarkerOptions ={
    clickable:true, draggable:false,
    // label: {
    //   text:"Pokemart HQ",
    //   color: '#dc3545',
    //   fontSize: '16px',
    //   fontWeight: '800',
    // },
    icon: {
    path:'M3.75 0a1 1 0 0 0-.8.4L.1 4.2a.5.5 0 0 0-.1.3V15a1 1 0 0 0 1 1h14a1 1 0 0 0 1-1V4.5a.5.5 0 0 0-.1-.3L13.05.4a1 1 0 0 0-.8-.4h-8.5ZM8.5 4h6l.5.667V5H1v-.333L1.5 4h6V1h1v3ZM8 7.993c1.664-1.711 5.825 1.283 0 5.132-5.825-3.85-1.664-6.843 0-5.132Z',
    fillColor: '#f1aeb4',
    fillOpacity: 1,
    strokeWeight: 1,
    strokeColor:"black",
    rotation: 0,
    scale: 1.5,
  }}
  HQ:StoreDTO = {
    storeId: 0,
    storeAddress: "Silph Co. Head Office, Saffron City, Kanto",
    storeName: "PokeMart HQ",
    storePhone: "+81120049725",
    storeLat: HQ_ISS.lat,
    storeLng: HQ_ISS.lng,
}
}
