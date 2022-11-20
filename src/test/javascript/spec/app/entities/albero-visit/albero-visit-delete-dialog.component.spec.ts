/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NinoTestModule } from '../../../test.module';
import { AlberoVisitDeleteDialogComponent } from 'app/entities/albero-visit/albero-visit-delete-dialog.component';
import { AlberoVisitService } from 'app/entities/albero-visit/albero-visit.service';

describe('Component Tests', () => {
    describe('AlberoVisit Management Delete Component', () => {
        let comp: AlberoVisitDeleteDialogComponent;
        let fixture: ComponentFixture<AlberoVisitDeleteDialogComponent>;
        let service: AlberoVisitService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [AlberoVisitDeleteDialogComponent]
            })
                .overrideTemplate(AlberoVisitDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AlberoVisitDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AlberoVisitService);
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
