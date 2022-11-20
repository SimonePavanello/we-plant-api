/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { EssenzaAuditUpdateComponent } from 'app/entities/essenza-audit/essenza-audit-update.component';
import { EssenzaAuditService } from 'app/entities/essenza-audit/essenza-audit.service';
import { EssenzaAudit } from 'app/shared/model/essenza-audit.model';

describe('Component Tests', () => {
    describe('EssenzaAudit Management Update Component', () => {
        let comp: EssenzaAuditUpdateComponent;
        let fixture: ComponentFixture<EssenzaAuditUpdateComponent>;
        let service: EssenzaAuditService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [EssenzaAuditUpdateComponent]
            })
                .overrideTemplate(EssenzaAuditUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EssenzaAuditUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EssenzaAuditService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EssenzaAudit(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.essenzaAudit = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EssenzaAudit();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.essenzaAudit = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
