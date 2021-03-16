import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmpresaSharedModule } from 'app/shared/shared.module';
import { PintorComponent } from './pintor.component';
import { PintorDetailComponent } from './pintor-detail.component';
import { PintorUpdateComponent } from './pintor-update.component';
import { PintorDeleteDialogComponent } from './pintor-delete-dialog.component';
import { pintorRoute } from './pintor.route';

@NgModule({
  imports: [EmpresaSharedModule, RouterModule.forChild(pintorRoute)],
  declarations: [PintorComponent, PintorDetailComponent, PintorUpdateComponent, PintorDeleteDialogComponent],
  entryComponents: [PintorDeleteDialogComponent],
})
export class EmpresaPintorModule {}
