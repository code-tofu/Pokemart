import { Component, OnInit, inject } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'client';

  theme: string = 'light';

  ngOnInit(): void {
    console.log(">> [INFO]","Height",window.innerHeight, "Width", window.innerWidth);
  }

  switchTheme() {
    this.theme === 'light' ? (this.theme = 'dark') : (this.theme = 'light');
  }
}
