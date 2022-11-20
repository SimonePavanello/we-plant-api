/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { PoiDetailComponent } from 'app/entities/poi/poi-detail.component';
import { Poi } from 'app/shared/model/poi.model';

describe('Component Tests', () => {
    describe('Poi Management Detail Component', () => {
        let comp: PoiDetailComponent;
        let fixture: ComponentFixture<PoiDetailComponent>;
        const route = ({ data: of({ poi: new Poi(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [PoiDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PoiDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PoiDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.poi).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
