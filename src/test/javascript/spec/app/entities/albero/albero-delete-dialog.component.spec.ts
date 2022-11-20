/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NinoTestModule } from '../../../test.module';
import { AlberoDeleteDialogComponent } from 'app/entities/albero/albero-delete-dialog.component';
import { AlberoService } from 'app/entities/albero/albero.service';

describe('Component Tests', () => {
    describe('Albero Management Delete Component', () => {
        let comp: AlberoDeleteDialogComponent;
        let fixture: ComponentFixture<AlberoDeleteDialogComponent>;
        let service: AlberoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [AlberoDeleteDialogComponent]
            })
                .overrideTemplate(AlberoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AlberoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AlberoService);
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
