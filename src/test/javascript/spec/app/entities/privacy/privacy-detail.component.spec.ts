/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { PrivacyDetailComponent } from 'app/entities/privacy/privacy-detail.component';
import { Privacy } from 'app/shared/model/privacy.model';

describe('Component Tests', () => {
    describe('Privacy Management Detail Component', () => {
        let comp: PrivacyDetailComponent;
        let fixture: ComponentFixture<PrivacyDetailComponent>;
        const route = ({ data: of({ privacy: new Privacy(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [PrivacyDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PrivacyDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PrivacyDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.privacy).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
