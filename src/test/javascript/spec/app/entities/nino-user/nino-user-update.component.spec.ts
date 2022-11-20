/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NinoTestModule } from '../../../test.module';
import { NinoUserUpdateComponent } from 'app/entities/nino-user/nino-user-update.component';
import { NinoUserService } from 'app/entities/nino-user/nino-user.service';
import { NinoUser } from 'app/shared/model/nino-user.model';

describe('Component Tests', () => {
    describe('NinoUser Management Update Component', () => {
        let comp: NinoUserUpdateComponent;
        let fixture: ComponentFixture<NinoUserUpdateComponent>;
        let service: NinoUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [NinoUserUpdateComponent]
            })
                .overrideTemplate(NinoUserUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NinoUserUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NinoUserService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new NinoUser(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ninoUser = entity;
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
                    const entity = new NinoUser();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ninoUser = entity;
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
