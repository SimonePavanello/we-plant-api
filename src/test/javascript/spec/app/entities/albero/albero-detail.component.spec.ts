/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { AlberoDetailComponent } from 'app/entities/albero/albero-detail.component';
import { Albero } from 'app/shared/model/albero.model';

describe('Component Tests', () => {
    describe('Albero Management Detail Component', () => {
        let comp: AlberoDetailComponent;
        let fixture: ComponentFixture<AlberoDetailComponent>;
        const route = ({ data: of({ albero: new Albero(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [AlberoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AlberoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AlberoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.albero).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
