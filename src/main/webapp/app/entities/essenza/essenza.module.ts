import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NinoSharedModule } from 'app/shared';
import {
    EssenzaComponent,
    EssenzaDetailComponent,
    EssenzaUpdateComponent,
    EssenzaDeletePopupComponent,
    EssenzaDeleteDialogComponent,
    essenzaRoute,
    essenzaPopupRoute
} from './';

const ENTITY_STATES = [...essenzaRoute, ...essenzaPopupRoute];

@NgModule({
    imports: [NinoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EssenzaComponent,
        EssenzaDetailComponent,
        EssenzaUpdateComponent,
        EssenzaDeleteDialogComponent,
        EssenzaDeletePopupComponent
    ],
    entryComponents: [EssenzaComponent, EssenzaUpdateComponent, EssenzaDeleteDialogComponent, EssenzaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NinoEssenzaModule {}
