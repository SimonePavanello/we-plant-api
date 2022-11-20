/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { AlberoVisitDetailComponent } from 'app/entities/albero-visit/albero-visit-detail.component';
import { AlberoVisit } from 'app/shared/model/albero-visit.model';

describe('Component Tests', () => {
    describe('AlberoVisit Management Detail Component', () => {
        let comp: AlberoVisitDetailComponent;
        let fixture: ComponentFixture<AlberoVisitDetailComponent>;
        const route = ({ data: of({ alberoVisit: new AlberoVisit(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [AlberoVisitDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AlberoVisitDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AlberoVisitDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.alberoVisit).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
