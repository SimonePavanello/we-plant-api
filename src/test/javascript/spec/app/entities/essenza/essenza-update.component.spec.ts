/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { EssenzaUpdateComponent } from 'app/entities/essenza/essenza-update.component';
import { EssenzaService } from 'app/entities/essenza/essenza.service';
import { Essenza } from 'app/shared/model/essenza.model';

describe('Component Tests', () => {
    describe('Essenza Management Update Component', () => {
        let comp: EssenzaUpdateComponent;
        let fixture: ComponentFixture<EssenzaUpdateComponent>;
        let service: EssenzaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [EssenzaUpdateComponent]
            })
                .overrideTemplate(EssenzaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EssenzaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EssenzaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Essenza(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.essenza = entity;
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
                    const entity = new Essenza();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.essenza = entity;
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
