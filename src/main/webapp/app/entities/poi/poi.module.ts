import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NinoSharedModule } from 'app/shared';
import {
    PoiComponent,
    PoiDetailComponent,
    PoiUpdateComponent,
    PoiDeletePopupComponent,
    PoiDeleteDialogComponent,
    poiRoute,
    poiPopupRoute
} from './';

const ENTITY_STATES = [...poiRoute, ...poiPopupRoute];

@NgModule({
    imports: [NinoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PoiComponent, PoiDetailComponent, PoiUpdateComponent, PoiDeleteDialogComponent, PoiDeletePopupComponent],
    entryComponents: [PoiComponent, PoiUpdateComponent, PoiDeleteDialogComponent, PoiDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NinoPoiModule {}
