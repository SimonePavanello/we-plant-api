/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NinoTestModule } from '../../../test.module';
import { EssenzaComponent } from 'app/entities/essenza/essenza.component';
import { EssenzaService } from 'app/entities/essenza/essenza.service';
import { Essenza } from 'app/shared/model/essenza.model';

describe('Component Tests', () => {
    describe('Essenza Management Component', () => {
        let comp: EssenzaComponent;
        let fixture: ComponentFixture<EssenzaComponent>;
        let service: EssenzaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [EssenzaComponent],
                providers: []
            })
                .overrideTemplate(EssenzaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EssenzaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EssenzaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Essenza(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.essenzas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
