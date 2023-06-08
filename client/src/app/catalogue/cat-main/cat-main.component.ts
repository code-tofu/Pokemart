import { HttpClient } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { CatalogueItem } from 'src/app/model/catalogue.model';
import { CatalogueService } from 'src/app/services/catalogue.service';

const perPage: number = 10;

@Component({
  selector: 'app-cat-main',
  templateUrl: './cat-main.component.html',
  styleUrls: ['./cat-main.component.css'],
})
export class CatMainComponent implements OnInit {
  catalogue$!: Observable<CatalogueItem[]>;
  category!: string;

  catSvc = inject(CatalogueService);
  actRoute = inject(ActivatedRoute);
  page: number = 0;
  maxpage: number = 5; //TODO TO CHANGE

  ngOnInit(): void {
    console.info('>> [INFO] ', this.actRoute.snapshot.routeConfig);
    if (this.actRoute.snapshot.routeConfig!.path == 'shop/category/:category') {
      this.catalogue$ = this.catSvc.getCataloguebyCategory(
        this.actRoute.snapshot.params['category']
      );
      this.category = this.actRoute.snapshot.params['category'];
      console.info('>> [INFO] ', 'Load Shop by Category:', this.category);
    } else if (this.actRoute.snapshot.routeConfig!.path == 'shop/search') {
      this.catalogue$ = this.catSvc.getCataloguebySearch(
        this.actRoute.snapshot.queryParams['query']
      );
      console.info(
        '>> [INFO] ',
        'Load Shop by Search:',
        this.actRoute.snapshot.queryParams['query']
      );
    } else {
      this.catalogue$ = this.catSvc.getCatalogue(this.page * perPage);
      console.info('>> [INFO] ', 'Load Shop Main');
    }
  }

  increasePage() {
    if (this.page < this.maxpage) this.page += 1;
  }
  decreasePage() {
    if (this.page > 0) this.page -= 1;
  }
}
