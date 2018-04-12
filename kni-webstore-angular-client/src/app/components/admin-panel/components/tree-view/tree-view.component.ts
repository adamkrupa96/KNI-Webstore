import { Component, OnInit, Input } from '@angular/core';
import { Category } from '../../../../models/Category';
import { CategoryService } from '../../../../services/category.service';
import { Subject } from 'rxjs/Subject';
import { ProductService } from '../../../../services/product.service';
import { TreeService } from '../../tree.service';

@Component({
  selector: 'app-tree-view',
  templateUrl: './tree-view.component.html',
  styleUrls: ['./tree-view.component.css']
})
export class TreeViewComponent implements OnInit {

  // Przechwycenie obiektu Subject z komponentu nadrzędnego
  // @Input() refreshTreeView: Subject<any>;
  categories: Category[];

  // Każdy index w tej liscie odpowiada obiektowi z listy categories, true -> lista rozwinieta, false -> lista schowana
  categoriesDrop: boolean[];

  subCategoriesDrop: boolean[];

  constructor(private catService: CategoryService,
    private prodService: ProductService,
    private refreshService: TreeService) { }

  ngOnInit() {
    this.refresh();
    this.refreshService.refreshSubject.subscribe(event => {
      this.refresh();
    });
    // Zasubskrybowanie go (Czyli przy każdej zmianie obiektu subject w AdminPanelComponent, odswiezaj liste)
    // i dziala jakoś
    // this.refreshTreeView.subscribe(event => {
    //   this.refresh();
    // });
  }

  deleteCategory(id: number) {
    this.catService.deleteCategory(id).subscribe(() => {
      this.refresh();
    });
  }

  deleteSubCategory(id: number) {
    this.catService.deleteSubCategory(id).subscribe(() => {
      this.refresh();
    });
  }

  deleteProduct(id: number) {
    this.prodService.deleteProduct(id).subscribe(() => {
      this.refresh();
    });
  }

  toggleDropCategory(index: number) {
    this.categoriesDrop[index] = !this.categoriesDrop[index];
  }

  refresh() {
    this.catService.getAllCategories().subscribe(res => {
      this.categories = res;
      this.refreshDrop();
    });
  }

  refreshDrop() {
    this.categoriesDrop = [];
    this.subCategoriesDrop = [];
    for (let i = 0; i < this.categories.length; i++) {
      this.categoriesDrop.push(false);
    }
  }

}
