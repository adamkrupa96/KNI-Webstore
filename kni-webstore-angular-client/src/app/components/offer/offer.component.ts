import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../models/category';

@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.css']
})
export class OfferComponent implements OnInit {

  categoriesList: Category[] = [];

  constructor(private catService: CategoryService) { }

  ngOnInit() {
    this.getAllCategories();
  }

  getAllCategories() {
    this.catService.getAllCategories().subscribe(catList => {
      this.categoriesList = catList;
      console.log(this.categoriesList);
    });
  }
}
