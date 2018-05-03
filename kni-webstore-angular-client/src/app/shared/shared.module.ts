import { NgModule } from '@angular/core';

import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { PathNavigationComponent } from './path-navigation/path-navigation.component';

@NgModule({
  declarations: [
    PathNavigationComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    PathNavigationComponent,
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class SharedModule {}
