/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NinoTestModule } from '../../../test.module';
import { AlberoComponent } from 'app/entities/albero/albero.component';
import { AlberoService } from 'app/entities/albero/albero.service';
import { Albero } from 'app/shared/model/albero.model';

describe('Component Tests', () => {
    describe('Albero Management Component', () => {
        let comp: AlberoComponent;
        let fixture: ComponentFixture<AlberoComponent>;
        let service: AlberoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [AlberoComponent],
                providers: []
            })
                .overrideTemplate(AlberoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AlberoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AlberoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Albero(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.alberos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
