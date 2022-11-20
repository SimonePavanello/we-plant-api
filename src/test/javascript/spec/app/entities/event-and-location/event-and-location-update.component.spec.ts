/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { EventAndLocationUpdateComponent } from 'app/entities/event-and-location/event-and-location-update.component';
import { EventAndLocationService } from 'app/entities/event-and-location/event-and-location.service';
import { EventAndLocation } from 'app/shared/model/event-and-location.model';

describe('Component Tests', () => {
    describe('EventAndLocation Management Update Component', () => {
        let comp: EventAndLocationUpdateComponent;
        let fixture: ComponentFixture<EventAndLocationUpdateComponent>;
        let service: EventAndLocationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [EventAndLocationUpdateComponent]
            })
                .overrideTemplate(EventAndLocationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EventAndLocationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EventAndLocationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EventAndLocation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.eventAndLocation = entity;
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
                    const entity = new EventAndLocation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.eventAndLocation = entity;
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
