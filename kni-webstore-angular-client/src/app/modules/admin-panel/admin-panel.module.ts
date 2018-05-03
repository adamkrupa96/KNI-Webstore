import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AdminPanelComponent,
  AddCategoryComponent,
  AddSubcategoryComponent,
  AddProductComponent,
  TreeViewComponent
} from './index';
import { AdminPanelRoutingModule } from './admin-panel-routing.module';
import { RouterModule } from '@angular/router';
import { TreeService } from './tree.service';
import { SharedModule } from '../../shared/shared.module';


// Tzw featured module
@NgModule({
  imports: [
    AdminPanelRoutingModule,
    RouterModule,
    SharedModule
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
