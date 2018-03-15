import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs/Subject';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  showCategory: boolean;
  showSubCategory: boolean;
  showProduct: boolean;

  // Obiekt zdarzenia, ktory zostanie przeslany do TreeViewComponent
  refreshTreeView: Subject<any> = new Subject();

  constructor() { }

  ngOnInit() {
    this.showCategory = true;
    this.showSubCategory = false;
    this.showProduct = false;
  }

  // Metoda "zmiany" obiektu, przeslanego do TreeView
  callTreeToRefresh() {
    this.refreshTreeView.next();
  }

  // Pseudo routing
  // Niech narazie zostanie dopoki nie wydzielimy oddzielnego modulu dla panelu admina

  showForm(form: number) {
    if (form === 0) {
      this.showCategory = true;
      this.showSubCategory = false;
      this.showProduct = false;
    } else if (form === 1) {
      this.showCategory = false;
      this.showSubCategory = true;
      this.showProduct = false;
    } else {
      this.showCategory = false;
      this.showSubCategory = false;
      this.showProduct = true;
    }
  }
}
