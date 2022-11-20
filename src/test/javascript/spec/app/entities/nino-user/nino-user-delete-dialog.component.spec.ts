/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NinoTestModule } from '../../../test.module';
import { NinoUserDeleteDialogComponent } from 'app/entities/nino-user/nino-user-delete-dialog.component';
import { NinoUserService } from 'app/entities/nino-user/nino-user.service';

describe('Component Tests', () => {
    describe('NinoUser Management Delete Component', () => {
        let comp: NinoUserDeleteDialogComponent;
        let fixture: ComponentFixture<NinoUserDeleteDialogComponent>;
        let service: NinoUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [NinoUserDeleteDialogComponent]
            })
                .overrideTemplate(NinoUserDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NinoUserDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NinoUserService);
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
