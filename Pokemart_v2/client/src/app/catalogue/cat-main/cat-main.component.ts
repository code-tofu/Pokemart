import { HttpClient } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, firstValueFrom } from 'rxjs';
import { CatalogueItem } from 'src/app/model/catalogue.model';
import { CatalogueService } from 'src/app/services/catalogue.service';

const perPage: number = 10; //pagination vs offset done on server side

@Component({
  selector: 'app-cat-main',
  templateUrl: './cat-main.component.html',
  styleUrls: ['./cat-main.component.css'],
})
export class CatMainComponent implements OnInit {
  catSvc = inject(CatalogueService);
  actRoute = inject(ActivatedRoute);
  catalogue$!: Observable<CatalogueItem[]>;
  category!: string;
  searchQuery!: string;
  page: number = 0;
  maxpage!: number;

  ngOnInit(): void {
    console.info('>> [INFO] ', this.actRoute.snapshot.routeConfig);
    if (this.actRoute.snapshot.routeConfig!.path == 'shop/category/:category') {
      this.category = this.actRoute.snapshot.params['category'];
      console.info('>> [INFO] ', 'Load Shop by Category:', this.category);
      firstValueFrom(this.catSvc.getTotalCountByCategory(this.category))
        .then((resp) => {
          this.maxpage = Math.ceil(resp / 10);
          console.info('>> [INFO] Max Pages: ', this.maxpage);

          this.catalogue$ = this.catSvc.getCataloguebyCategory(this.category);
        })
        .catch((err) => {
          alert('Connection Issue: Please Try Again Later');
          console.error('>> [ERROR] Server Error:', err);
        });
    } else if (this.actRoute.snapshot.routeConfig!.path == 'shop/search') {
      this.searchQuery = this.actRoute.snapshot.queryParams['query'];
      console.info('>> [INFO] ', 'Load Shop by Search:', this.searchQuery);
      firstValueFrom(this.catSvc.getTotalCountBySearch(this.searchQuery))
        .then((resp) => {
          this.maxpage = Math.ceil(resp / 10);
          console.info('>> [INFO] Max Pages: ', this.maxpage);
          this.catalogue$ = this.catSvc.getCataloguebySearch(this.searchQuery);
        })
        .catch((err) => {
          alert('Connection Issue: Please Try Again Later');
          console.error('>> [ERROR] Server Error:', err);
        });
    } else {
      firstValueFrom(this.catSvc.getTotalCountAllProducts())
        .then((resp) => {
          this.maxpage = Math.ceil(resp / 10);
          console.info('>> [INFO] Max Pages: ', this.maxpage);
          this.catalogue$ = this.catSvc.getCatalogue(this.page);
          console.info('>> [INFO] ', 'Load Shop Main');
        })
        .catch((err) => {
          alert('Connection Issue: Please Try Again Later');
          console.error('>> [ERROR] Server Error:', err);
        });
    }
  }

  increasePage() {
    if (this.page < this.maxpage) this.page += 1;
    this.catalogue$ = this.catSvc.getCatalogue(this.page);
  }
  decreasePage() {
    if (this.page > 0) this.page -= 1;
    this.catalogue$ = this.catSvc.getCatalogue(this.page);
  }

  goToPage(e: any) {
    console.info('>> [INFO] ', e.srcElement.innerText);
    if (
      e.srcElement.innerText - 1 < this.maxpage &&
      e.srcElement.innerText - 1 > 0
    )
      this.page = +e.srcElement.innerText - 1;
    this.catalogue$ = this.catSvc.getCatalogue(this.page);
  }
}
