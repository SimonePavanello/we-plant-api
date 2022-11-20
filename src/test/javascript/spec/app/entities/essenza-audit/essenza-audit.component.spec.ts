/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NinoTestModule } from '../../../test.module';
import { EssenzaAuditComponent } from 'app/entities/essenza-audit/essenza-audit.component';
import { EssenzaAuditService } from 'app/entities/essenza-audit/essenza-audit.service';
import { EssenzaAudit } from 'app/shared/model/essenza-audit.model';

describe('Component Tests', () => {
    describe('EssenzaAudit Management Component', () => {
        let comp: EssenzaAuditComponent;
        let fixture: ComponentFixture<EssenzaAuditComponent>;
        let service: EssenzaAuditService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [EssenzaAuditComponent],
                providers: []
            })
                .overrideTemplate(EssenzaAuditComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EssenzaAuditComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EssenzaAuditService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EssenzaAudit(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.essenzaAudits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
