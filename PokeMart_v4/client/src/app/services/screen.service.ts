import { Injectable } from '@angular/core';
import { HostListener } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class ScreenService {

  constructor() {
    this.getScreenSize();
  }

  screenHeight!: number;
  screenWidth!: number;

  @HostListener('window:resize', ['$event'])
  getScreenSize(event?:any) {
        this.screenHeight = window.innerHeight;
        this.screenWidth = window.innerWidth;
        console.log()
  }
}
