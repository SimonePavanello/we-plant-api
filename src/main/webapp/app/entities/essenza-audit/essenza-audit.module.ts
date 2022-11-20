import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NinoSharedModule } from 'app/shared';
import { NinoAdminModule } from 'app/admin/admin.module';
import {
    EssenzaAuditComponent,
    EssenzaAuditDetailComponent,
    EssenzaAuditUpdateComponent,
    EssenzaAuditDeletePopupComponent,
    EssenzaAuditDeleteDialogComponent,
    essenzaAuditRoute,
    essenzaAuditPopupRoute
} from './';

const ENTITY_STATES = [...essenzaAuditRoute, ...essenzaAuditPopupRoute];

@NgModule({
    imports: [NinoSharedModule, NinoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EssenzaAuditComponent,
        EssenzaAuditDetailComponent,
        EssenzaAuditUpdateComponent,
        EssenzaAuditDeleteDialogComponent,
        EssenzaAuditDeletePopupComponent
    ],
    entryComponents: [
        EssenzaAuditComponent,
        EssenzaAuditUpdateComponent,
        EssenzaAuditDeleteDialogComponent,
        EssenzaAuditDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NinoEssenzaAuditModule {}
