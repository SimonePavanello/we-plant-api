/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NinoTestModule } from '../../../test.module';
import { NinoUserComponent } from 'app/entities/nino-user/nino-user.component';
import { NinoUserService } from 'app/entities/nino-user/nino-user.service';
import { NinoUser } from 'app/shared/model/nino-user.model';

describe('Component Tests', () => {
    describe('NinoUser Management Component', () => {
        let comp: NinoUserComponent;
        let fixture: ComponentFixture<NinoUserComponent>;
        let service: NinoUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NinoTestModule],
                declarations: [NinoUserComponent],
                providers: []
            })
                .overrideTemplate(NinoUserComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NinoUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NinoUserService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new NinoUser(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.ninoUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
