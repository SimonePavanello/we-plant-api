import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NinoSharedModule } from 'app/shared';
import {
    EventAndLocationComponent,
    EventAndLocationDetailComponent,
    EventAndLocationUpdateComponent,
    EventAndLocationDeletePopupComponent,
    EventAndLocationDeleteDialogComponent,
    eventAndLocationRoute,
    eventAndLocationPopupRoute
} from './';

const ENTITY_STATES = [...eventAndLocationRoute, ...eventAndLocationPopupRoute];

@NgModule({
    imports: [NinoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EventAndLocationComponent,
        EventAndLocationDetailComponent,
        EventAndLocationUpdateComponent,
        EventAndLocationDeleteDialogComponent,
        EventAndLocationDeletePopupComponent
    ],
    entryComponents: [
        EventAndLocationComponent,
        EventAndLocationUpdateComponent,
        EventAndLocationDeleteDialogComponent,
        EventAndLocationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NinoEventAndLocationModule {}
