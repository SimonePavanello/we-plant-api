import { NgModule } from '@angular/core';

import { NinoSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [NinoSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [NinoSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class NinoSharedCommonModule {}
