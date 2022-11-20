/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { EssenzaAuditDetailComponent } from 'app/entities/essenza-audit/essenza-audit-detail.component';
import { EssenzaAudit } from 'app/shared/model/essenza-audit.model';

describe('Component Tests', () => {
    describe('EssenzaAudit Management Detail Component', () => {
        let comp: EssenzaAuditDetailComponent;
        let fixture: ComponentFixture<EssenzaAuditDetailComponent>;
        const route = ({ data: of({ essenzaAudit: new EssenzaAudit(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [EssenzaAuditDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EssenzaAuditDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EssenzaAuditDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.essenzaAudit).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
