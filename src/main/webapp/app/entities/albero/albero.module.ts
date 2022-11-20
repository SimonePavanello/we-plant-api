import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NinoSharedModule } from 'app/shared';
import { NinoAdminModule } from 'app/admin/admin.module';
import {
    AlberoComponent,
    AlberoDetailComponent,
    AlberoUpdateComponent,
    AlberoDeletePopupComponent,
    AlberoDeleteDialogComponent,
    alberoRoute,
    alberoPopupRoute
} from './';

const ENTITY_STATES = [...alberoRoute, ...alberoPopupRoute];

@NgModule({
    imports: [NinoSharedModule, NinoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AlberoComponent, AlberoDetailComponent, AlberoUpdateComponent, AlberoDeleteDialogComponent, AlberoDeletePopupComponent],
    entryComponents: [AlberoComponent, AlberoUpdateComponent, AlberoDeleteDialogComponent, AlberoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NinoAlberoModule {}
