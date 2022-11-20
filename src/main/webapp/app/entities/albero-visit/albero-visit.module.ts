import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NinoSharedModule } from 'app/shared';
import { NinoAdminModule } from 'app/admin/admin.module';
import {
    AlberoVisitComponent,
    AlberoVisitDetailComponent,
    AlberoVisitUpdateComponent,
    AlberoVisitDeletePopupComponent,
    AlberoVisitDeleteDialogComponent,
    alberoVisitRoute,
    alberoVisitPopupRoute
} from './';

const ENTITY_STATES = [...alberoVisitRoute, ...alberoVisitPopupRoute];

@NgModule({
    imports: [NinoSharedModule, NinoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AlberoVisitComponent,
        AlberoVisitDetailComponent,
        AlberoVisitUpdateComponent,
        AlberoVisitDeleteDialogComponent,
        AlberoVisitDeletePopupComponent
    ],
    entryComponents: [AlberoVisitComponent, AlberoVisitUpdateComponent, AlberoVisitDeleteDialogComponent, AlberoVisitDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NinoAlberoVisitModule {}
