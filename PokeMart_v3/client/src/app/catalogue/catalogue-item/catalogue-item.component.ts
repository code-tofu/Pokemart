import { Component, Input, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CatalogueItem } from 'src/app/model/catalogue.model';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'app-catalogue-item',
  templateUrl: './catalogue-item.component.html',
  styleUrls: ['./catalogue-item.component.css'],
})
export class CatalogueItemComponent {
  @Input()
  item!: CatalogueItem;
  @Input()
  imgsrc!: String;

  router = inject(Router);

  ngOnInit(): void {
    this.imgsrc = Utils.generateImgURL(this.item.nameID);
  }

  detailPage() {
    this.router.navigate(['/detail', this.item.productID]);
  }


}
