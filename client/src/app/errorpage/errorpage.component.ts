import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-errorpage',
  templateUrl: './errorpage.component.html',
  styleUrls: ['./errorpage.component.css'],
})
export class ErrorpageComponent implements OnInit {
  actRoute = inject(ActivatedRoute);

  titleMain: String = '404:NOT FOUND';
  subtitleFirst: String = 'PROFESSOR OAK:';
  subtitleSecond: String = "This isn't the time to use that!";

  ngOnInit(): void {
    console.info('>> [INFO] Path:', this.actRoute.snapshot.url[0].path);
    if (this.actRoute.snapshot.url[0].path == 'uc') {
      this.titleMain = "We're Sorry";
      this.subtitleFirst = 'This page is still under construction.';
      this.subtitleSecond = 'Please come again soon!';
    }
  }
}
