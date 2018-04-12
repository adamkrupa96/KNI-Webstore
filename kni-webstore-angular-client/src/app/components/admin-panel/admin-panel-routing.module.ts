import { NgModule } from '@angular/core';
import { AdminPanelComponent, AddCategoryComponent, AddSubcategoryComponent, AddProductComponent } from './index';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuardsService } from '../../services/auth-guards.service';
import { AdminPanelModule } from './admin-panel.module';

const routes: Routes = [
    {
        path: 'adminpanel',
        component: AdminPanelComponent,
        canActivate: [AuthGuardsService],
        children: [
            { path: '', component: AddCategoryComponent },
            { path: 'addcategory', component: AddCategoryComponent },
            { path: 'addsubcategory', component: AddSubcategoryComponent },
            { path: 'addproduct', component: AddProductComponent }
        ]
    }
];

@NgModule({
    imports: [AdminPanelModule,
        RouterModule.forChild(routes)
    ],
    exports: [RouterModule]
})
export class AdminPanelRoutingModule { }