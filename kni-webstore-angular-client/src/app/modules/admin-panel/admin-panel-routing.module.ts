import { NgModule } from '@angular/core';
import { AdminPanelComponent, AddCategoryComponent, AddSubcategoryComponent, AddProductComponent } from './index';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuardsService } from '../../services/auth-guards.service';
import { AdminAuthGuardsService } from '../../services/admin-auth-guards.service';

const routes: Routes = [
    {
        path: 'adminpanel',
        component: AdminPanelComponent,
        canActivate: [AdminAuthGuardsService],
        children: [
            { path: '', component: AddCategoryComponent },
            { path: 'addcategory', component: AddCategoryComponent },
            { path: 'addsubcategory', component: AddSubcategoryComponent },
            { path: 'addproduct', component: AddProductComponent }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminPanelRoutingModule { }
