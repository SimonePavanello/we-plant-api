import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NinoSharedModule } from 'app/shared';
import { NinoAdminModule } from 'app/admin/admin.module';
import {
    ImageComponent,
    ImageDetailComponent,
    ImageUpdateComponent,
    ImageDeletePopupComponent,
    ImageDeleteDialogComponent,
    imageRoute,
    imagePopupRoute
} from './';

const ENTITY_STATES = [...imageRoute, ...imagePopupRoute];

@NgModule({
    imports: [NinoSharedModule, NinoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ImageComponent, ImageDetailComponent, ImageUpdateComponent, ImageDeleteDialogComponent, ImageDeletePopupComponent],
    entryComponents: [ImageComponent, ImageUpdateComponent, ImageDeleteDialogComponent, ImageDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NinoImageModule {}
