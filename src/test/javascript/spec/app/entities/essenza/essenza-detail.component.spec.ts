/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { EssenzaDetailComponent } from 'app/entities/essenza/essenza-detail.component';
import { Essenza } from 'app/shared/model/essenza.model';

describe('Component Tests', () => {
    describe('Essenza Management Detail Component', () => {
        let comp: EssenzaDetailComponent;
        let fixture: ComponentFixture<EssenzaDetailComponent>;
        const route = ({ data: of({ essenza: new Essenza(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [EssenzaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EssenzaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EssenzaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.essenza).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
