/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NinoTestModule } from '../../../test.module';
import { EventAndLocationComponent } from 'app/entities/event-and-location/event-and-location.component';
import { EventAndLocationService } from 'app/entities/event-and-location/event-and-location.service';
import { EventAndLocation } from 'app/shared/model/event-and-location.model';

describe('Component Tests', () => {
    describe('EventAndLocation Management Component', () => {
        let comp: EventAndLocationComponent;
        let fixture: ComponentFixture<EventAndLocationComponent>;
        let service: EventAndLocationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [EventAndLocationComponent],
                providers: []
            })
                .overrideTemplate(EventAndLocationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EventAndLocationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EventAndLocationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EventAndLocation(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.eventAndLocations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
