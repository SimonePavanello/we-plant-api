/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NinoTestModule } from '../../../test.module';
import { StopComponent } from 'app/entities/stop/stop.component';
import { StopService } from 'app/entities/stop/stop.service';
import { Stop } from 'app/shared/model/stop.model';

describe('Component Tests', () => {
    describe('Stop Management Component', () => {
        let comp: StopComponent;
        let fixture: ComponentFixture<StopComponent>;
        let service: StopService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [StopComponent],
                providers: []
            })
                .overrideTemplate(StopComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StopComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StopService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Stop(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.stops[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
