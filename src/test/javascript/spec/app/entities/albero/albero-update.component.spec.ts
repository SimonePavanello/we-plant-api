/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { AlberoUpdateComponent } from 'app/entities/albero/albero-update.component';
import { AlberoService } from 'app/entities/albero/albero.service';
import { Albero } from 'app/shared/model/albero.model';

describe('Component Tests', () => {
    describe('Albero Management Update Component', () => {
        let comp: AlberoUpdateComponent;
        let fixture: ComponentFixture<AlberoUpdateComponent>;
        let service: AlberoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [AlberoUpdateComponent]
            })
                .overrideTemplate(AlberoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AlberoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AlberoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Albero(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.albero = entity;
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
                    const entity = new Albero();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.albero = entity;
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
