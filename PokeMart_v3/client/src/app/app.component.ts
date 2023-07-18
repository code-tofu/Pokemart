import { Component, OnInit, inject } from '@angular/core';
import { THEME_KEY } from './endpoint.constants';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'Pokemart';
  
  theme: string = 'light';

  ngOnInit(): void {
    console.log(">> [INFO]","Height",window.innerHeight, "Width", window.innerWidth);
    this.loadtheme();
  }

  switchTheme() {
    this.theme === 'light' ? (this.theme = 'dark') : (this.theme = 'light');
    this.savetheme();
  }

  savetheme(): void {
    window.sessionStorage.removeItem(THEME_KEY);
    window.sessionStorage.setItem(THEME_KEY, this.theme);
  }

  loadtheme(): void {
    const savedTheme = window.sessionStorage.getItem(THEME_KEY);
    if (savedTheme)this.theme = savedTheme;
  }
}
