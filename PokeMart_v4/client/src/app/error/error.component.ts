import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent {
  actRoute = inject(ActivatedRoute);

  titleMain: String = '404:NOT FOUND';
  subtitleFirst: String = 'Professor Oak:';
  subtitleSecond: String = "This isn't the time to use that!";

  ngOnInit(): void {
    console.info('>> [INFO] Error Page Path:', this.actRoute.snapshot.url[0].path);
    if (this.actRoute.snapshot.url[0].path == 'construction') {
      this.titleMain = "We're Sorry!";
      this.subtitleFirst = 'This page is still under construction.';
      this.subtitleSecond = 'Please come again soon!';
    }
  }
}