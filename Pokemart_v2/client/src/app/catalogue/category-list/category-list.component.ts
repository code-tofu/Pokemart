import { Component, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { CategoryCount } from 'src/app/model/catalogue.model';
import { CatalogueService } from 'src/app/services/catalogue.service';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css'],
})
export class CategoryListComponent {
  categories$!: Observable<CategoryCount[]>;

  catSvc = inject(CatalogueService);

  ngOnInit(): void {
    this.categories$ = this.catSvc.getCategories();
  }
}
