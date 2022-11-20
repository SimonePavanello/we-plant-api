import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { NinoVisitModule } from './visit/visit.module';
import { NinoStopModule } from './stop/stop.module';
import { NinoAlberoModule } from './albero/albero.module';
import { NinoEssenzaModule } from './essenza/essenza.module';
import { NinoPrivacyModule } from './privacy/privacy.module';
import { NinoEssenzaAuditModule } from './essenza-audit/essenza-audit.module';
import { NinoImageModule } from './image/image.module';
import { NinoLocationModule } from './location/location.module';
import { NinoNinoUserModule } from './nino-user/nino-user.module';
import { NinoPoiModule } from './poi/poi.module';
import { NinoEventAndLocationModule } from './event-and-location/event-and-location.module';
import { NinoAlberoVisitModule } from './albero-visit/albero-visit.module';
import { NinoEmailModule } from './email/email.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        NinoVisitModule,
        NinoStopModule,
        NinoAlberoModule,
        NinoEssenzaModule,
        NinoPrivacyModule,
        NinoEssenzaAuditModule,
        NinoImageModule,
        NinoLocationModule,
        NinoNinoUserModule,
        NinoPoiModule,
        NinoEventAndLocationModule,
        NinoAlberoVisitModule,
        NinoEmailModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NinoEntityModule {}
