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

import { UploadFileService } from '../../services/upload-file.service';

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
    TreeService,
    UploadFileService,
  ]
})
export class AdminPanelModule { }
