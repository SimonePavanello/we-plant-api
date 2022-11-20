/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NinoTestModule } from '../../../test.module';
import { AlberoVisitComponent } from 'app/entities/albero-visit/albero-visit.component';
import { AlberoVisitService } from 'app/entities/albero-visit/albero-visit.service';
import { AlberoVisit } from 'app/shared/model/albero-visit.model';

describe('Component Tests', () => {
    describe('AlberoVisit Management Component', () => {
        let comp: AlberoVisitComponent;
        let fixture: ComponentFixture<AlberoVisitComponent>;
        let service: AlberoVisitService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [AlberoVisitComponent],
                providers: []
            })
                .overrideTemplate(AlberoVisitComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AlberoVisitComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AlberoVisitService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AlberoVisit(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.alberoVisits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
