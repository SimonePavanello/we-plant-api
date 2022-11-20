import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NinoSharedModule } from 'app/shared';
import {
    StopComponent,
    StopDetailComponent,
    StopUpdateComponent,
    StopDeletePopupComponent,
    StopDeleteDialogComponent,
    stopRoute,
    stopPopupRoute
} from './';

const ENTITY_STATES = [...stopRoute, ...stopPopupRoute];

@NgModule({
    imports: [NinoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [StopComponent, StopDetailComponent, StopUpdateComponent, StopDeleteDialogComponent, StopDeletePopupComponent],
    entryComponents: [StopComponent, StopUpdateComponent, StopDeleteDialogComponent, StopDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NinoStopModule {}
