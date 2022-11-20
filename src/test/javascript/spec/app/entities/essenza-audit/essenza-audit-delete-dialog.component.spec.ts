/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NinoTestModule } from '../../../test.module';
import { EssenzaAuditDeleteDialogComponent } from 'app/entities/essenza-audit/essenza-audit-delete-dialog.component';
import { EssenzaAuditService } from 'app/entities/essenza-audit/essenza-audit.service';

describe('Component Tests', () => {
    describe('EssenzaAudit Management Delete Component', () => {
        let comp: EssenzaAuditDeleteDialogComponent;
        let fixture: ComponentFixture<EssenzaAuditDeleteDialogComponent>;
        let service: EssenzaAuditService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [EssenzaAuditDeleteDialogComponent]
            })
                .overrideTemplate(EssenzaAuditDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EssenzaAuditDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EssenzaAuditService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
