import { Component, inject } from '@angular/core';
import { Observable, firstValueFrom } from 'rxjs';
import { catPerPage } from 'src/app/endpoint.constants';
import { CategoryCount } from 'src/app/model/count.model';
import { CatalogueService } from 'src/app/services/catalogue.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent {
  categories$!: Observable<CategoryCount[]>;
  catalogueSvc = inject(CatalogueService);
  currentPage = 1;
  maxPage=0;
  catPerPage=catPerPage;
  
  ngOnInit(): void {
    this.categories$ = this.catalogueSvc.getCategories(this.currentPage-1);
    firstValueFrom(this.catalogueSvc.getCategoryCount())
      .then((resp) => {
        console.info(">> [INFO] Max Page ", resp);
        this.maxPage = resp;
      })
      .catch((err) => {
        this.catalogueSvc.error.httpNumErrorHandler(err);
      });
  }

  goToPage(e: any) {
    console.info('>> [INFO] User Selected Page:', e);
    this.currentPage = e;
    this.categories$ = this.catalogueSvc.getCategories(this.currentPage-1);
  }

}

