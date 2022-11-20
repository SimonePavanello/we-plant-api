import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NinoSharedModule } from 'app/shared';
import { NinoAdminModule } from 'app/admin/admin.module';
import {
    VisitComponent,
    VisitDetailComponent,
    VisitUpdateComponent,
    VisitDeletePopupComponent,
    VisitDeleteDialogComponent,
    visitRoute,
    visitPopupRoute
} from './';

const ENTITY_STATES = [...visitRoute, ...visitPopupRoute];

@NgModule({
    imports: [NinoSharedModule, NinoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [VisitComponent, VisitDetailComponent, VisitUpdateComponent, VisitDeleteDialogComponent, VisitDeletePopupComponent],
    entryComponents: [VisitComponent, VisitUpdateComponent, VisitDeleteDialogComponent, VisitDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NinoVisitModule {}
