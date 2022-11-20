/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { StopUpdateComponent } from 'app/entities/stop/stop-update.component';
import { StopService } from 'app/entities/stop/stop.service';
import { Stop } from 'app/shared/model/stop.model';

describe('Component Tests', () => {
    describe('Stop Management Update Component', () => {
        let comp: StopUpdateComponent;
        let fixture: ComponentFixture<StopUpdateComponent>;
        let service: StopService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [StopUpdateComponent]
            })
                .overrideTemplate(StopUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StopUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StopService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Stop(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stop = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Stop();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stop = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
