/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { StopDetailComponent } from 'app/entities/stop/stop-detail.component';
import { Stop } from 'app/shared/model/stop.model';

describe('Component Tests', () => {
    describe('Stop Management Detail Component', () => {
        let comp: StopDetailComponent;
        let fixture: ComponentFixture<StopDetailComponent>;
        const route = ({ data: of({ stop: new Stop(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [StopDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StopDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StopDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stop).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
