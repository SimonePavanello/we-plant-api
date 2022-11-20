/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NinoTestModule } from '../../../test.module';
import { PoiDeleteDialogComponent } from 'app/entities/poi/poi-delete-dialog.component';
import { PoiService } from 'app/entities/poi/poi.service';

describe('Component Tests', () => {
    describe('Poi Management Delete Component', () => {
        let comp: PoiDeleteDialogComponent;
        let fixture: ComponentFixture<PoiDeleteDialogComponent>;
        let service: PoiService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [PoiDeleteDialogComponent]
            })
                .overrideTemplate(PoiDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PoiDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoiService);
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
