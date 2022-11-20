/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NinoTestModule } from '../../../test.module';
import { PoiComponent } from 'app/entities/poi/poi.component';
import { PoiService } from 'app/entities/poi/poi.service';
import { Poi } from 'app/shared/model/poi.model';

describe('Component Tests', () => {
    describe('Poi Management Component', () => {
        let comp: PoiComponent;
        let fixture: ComponentFixture<PoiComponent>;
        let service: PoiService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [PoiComponent],
                providers: []
            })
                .overrideTemplate(PoiComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PoiComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoiService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Poi(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.pois[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
