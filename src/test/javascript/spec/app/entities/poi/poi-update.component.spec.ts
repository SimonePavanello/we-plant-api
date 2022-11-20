/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { PoiUpdateComponent } from 'app/entities/poi/poi-update.component';
import { PoiService } from 'app/entities/poi/poi.service';
import { Poi } from 'app/shared/model/poi.model';

describe('Component Tests', () => {
    describe('Poi Management Update Component', () => {
        let comp: PoiUpdateComponent;
        let fixture: ComponentFixture<PoiUpdateComponent>;
        let service: PoiService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [PoiUpdateComponent]
            })
                .overrideTemplate(PoiUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PoiUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoiService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Poi(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.poi = entity;
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
                    const entity = new Poi();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.poi = entity;
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
