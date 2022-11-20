/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { EventAndLocationDetailComponent } from 'app/entities/event-and-location/event-and-location-detail.component';
import { EventAndLocation } from 'app/shared/model/event-and-location.model';

describe('Component Tests', () => {
    describe('EventAndLocation Management Detail Component', () => {
        let comp: EventAndLocationDetailComponent;
        let fixture: ComponentFixture<EventAndLocationDetailComponent>;
        const route = ({ data: of({ eventAndLocation: new EventAndLocation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [EventAndLocationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EventAndLocationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EventAndLocationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.eventAndLocation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
