/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { NinoUserDetailComponent } from 'app/entities/nino-user/nino-user-detail.component';
import { NinoUser } from 'app/shared/model/nino-user.model';

describe('Component Tests', () => {
    describe('NinoUser Management Detail Component', () => {
        let comp: NinoUserDetailComponent;
        let fixture: ComponentFixture<NinoUserDetailComponent>;
        const route = ({ data: of({ ninoUser: new NinoUser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [NinoUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NinoUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NinoUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ninoUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
