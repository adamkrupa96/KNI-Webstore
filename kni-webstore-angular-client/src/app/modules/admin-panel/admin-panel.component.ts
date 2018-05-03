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

  constructor() { }

  ngOnInit() {
    this.showCategory = true;
    this.showSubCategory = false;
    this.showProduct = false;
  }
}
