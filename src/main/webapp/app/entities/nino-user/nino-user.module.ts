import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NinoSharedModule } from 'app/shared';
import { NinoAdminModule } from 'app/admin/admin.module';
import {
    NinoUserComponent,
    NinoUserDetailComponent,
    NinoUserUpdateComponent,
    NinoUserDeletePopupComponent,
    NinoUserDeleteDialogComponent,
    ninoUserRoute,
    ninoUserPopupRoute
} from './';

const ENTITY_STATES = [...ninoUserRoute, ...ninoUserPopupRoute];

@NgModule({
    imports: [NinoSharedModule, NinoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NinoUserComponent,
        NinoUserDetailComponent,
        NinoUserUpdateComponent,
        NinoUserDeleteDialogComponent,
        NinoUserDeletePopupComponent
    ],
    entryComponents: [NinoUserComponent, NinoUserUpdateComponent, NinoUserDeleteDialogComponent, NinoUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NinoNinoUserModule {}
