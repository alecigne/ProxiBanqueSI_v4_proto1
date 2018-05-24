import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { CompteSelectionComponent } from './compte-selection/compte-selection.component';
import { TransfertComponent } from './transfert/transfert.component';
import { VirementComponent } from './virement/virement.component';
import { OperationsService } from './operations.service';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { FieldComponent } from '../shared/field/field.component';
import { SharedModule } from '../shared/shared.module';
import { MenuComponent } from './menu/menu.component';
import { AuditComponent } from './audit/audit.component';

const routes: Routes = [
  { path: 'virement', component: VirementComponent },
  { path: 'operations/audit', component: AuditComponent},
  { path: 'operations', component: MenuComponent }
]

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule,
    SharedModule
  ],
  declarations: [
    VirementComponent,
    CompteSelectionComponent,
    TransfertComponent, 
    CompteSelectionComponent, MenuComponent,
    MenuComponent,
    AuditComponent
   ],
  providers:[
    OperationsService
  ]
})
export class OperationsModule { }
