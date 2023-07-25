import { Component, OnInit, SimpleChanges, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { CatalogueItem } from 'src/app/model/catalogue.model';
import { CatalogueService } from 'src/app/services/catalogue.service';
import { itemsPerPage } from 'src/app/endpoint.constants';

@Component({
  selector: 'app-catalogue',
  templateUrl: './catalogue.component.html',
  styleUrls: ['./catalogue.component.css'],
})
export class CatalogueComponent implements OnInit {
  catalogueSvc = inject(CatalogueService);
  actRoute = inject(ActivatedRoute);
  catalogue$!: Observable<CatalogueItem[]>;
  catalogueType: number = 0;
  userParam!: string;
  currentPage: number = 1;
  maxPage!: number;
  itemsPerPage = itemsPerPage;
  qpSub!: Subscription;

  ngOnInit(): void {
    console.info('>> [INFO] Route:', this.actRoute.snapshot.routeConfig);
    switch (this.actRoute.snapshot.routeConfig!.path) {
      case 'shop/category/:category': {
        this.catalogueType = 1;
        this.userParam = this.actRoute.snapshot.params['category'];
        this.catalogueSvc
          .getTotalCountByCategory(this.userParam)
          .subscribe((resp) => {
            this.maxPage = resp;
            console.info('>> [INFO] Max Page ', this.maxPage);
          });

        this.catalogue$ = this.loadCataloguePage(this.catalogueType);
        console.info('>> [INFO] ', 'Load Shop by Category:', this.userParam);
        break;
      }
      case 'shop/search': {
        this.catalogueType = 2;
        this.qpSub = this.actRoute.queryParams.subscribe((params) => {
          console.info('>> [INFO] QueryParams:', params['query']);
          this.userParam = params['query'];
          this.catalogueSvc
            .getTotalCountBySearch(this.userParam)
            .subscribe((resp) => {
              this.maxPage = resp;
              console.info('>> [INFO] Max Page ', this.maxPage);
            });
          this.catalogue$ = this.loadCataloguePage(this.catalogueType);
          console.info('>> [INFO] ', 'Load Shop by Search:', this.userParam);
        });
        break;
      }
      default: {
        this.catalogueSvc.getTotalCountFullCatalogue().subscribe((resp) => {
          this.maxPage = resp;
          console.info('>> [INFO] Max Page ', this.maxPage);
        });
        this.catalogue$ = this.loadCataloguePage(this.catalogueType);
        console.info('>> [INFO] Load Shop Catalogue');
        break;
      }
    }
    console.info('>> [INFO] Current Page:', this.currentPage);
  }

  loadCataloguePage(catalogueType: number): Observable<CatalogueItem[]> {
    switch (catalogueType) {
      case 1: {
        return this.catalogueSvc.getCataloguebyCategory(
          this.userParam,
          this.currentPage - 1
        );
      }
      case 2: {
        return this.catalogueSvc.getCataloguebySearch(
          this.userParam,
          this.currentPage - 1
        );
      }
      default: {
        return this.catalogueSvc.getCatalogue(this.currentPage - 1);
      }
    }
  }

  goToPage(e: any) {
    console.info('>> [INFO] User Selected Page:', e);
    this.currentPage = e;
    this.catalogue$ = this.loadCataloguePage(this.catalogueType);
  }

  ngOnDestroy(): void {
    if (this.qpSub) {
      this.qpSub.unsubscribe();
      console.info('>> [INFO] Unsubscribed QueryParams');
    }
  }
}
