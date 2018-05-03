import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AdminPanelComponent,
  AddCategoryComponent,
  AddSubcategoryComponent,
  AddProductComponent,
  TreeViewComponent
} from './index';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TreeService } from './tree.service';


// Tzw featured module
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  declarations: [
    AdminPanelComponent,
    AddCategoryComponent,
    AddSubcategoryComponent,
    AddProductComponent,
    TreeViewComponent
  ],
  providers: [
    TreeService
  ]
})
export class AdminPanelModule { }
