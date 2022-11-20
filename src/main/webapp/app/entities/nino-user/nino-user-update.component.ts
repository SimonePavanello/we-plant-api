import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { INinoUser } from 'app/shared/model/nino-user.model';
import { NinoUserService } from './nino-user.service';
import { IUser, UserService } from 'app/core';
import { IEventAndLocation } from 'app/shared/model/event-and-location.model';
import { EventAndLocationService } from 'app/entities/event-and-location';

@Component({
    selector: 'jhi-nino-user-update',
    templateUrl: './nino-user-update.component.html'
})
export class NinoUserUpdateComponent implements OnInit {
    private _ninoUser: INinoUser;
    isSaving: boolean;

    users: IUser[];

    eventandlocations: IEventAndLocation[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private ninoUserService: NinoUserService,
        private userService: UserService,
        private eventAndLocationService: EventAndLocationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ninoUser }) => {
            this.ninoUser = ninoUser;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.eventAndLocationService.query().subscribe(
            (res: HttpResponse<IEventAndLocation[]>) => {
                this.eventandlocations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ninoUser.id !== undefined) {
            this.subscribeToSaveResponse(this.ninoUserService.update(this.ninoUser));
        } else {
            this.subscribeToSaveResponse(this.ninoUserService.create(this.ninoUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<INinoUser>>) {
        result.subscribe((res: HttpResponse<INinoUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackEventAndLocationById(index: number, item: IEventAndLocation) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get ninoUser() {
        return this._ninoUser;
    }

    set ninoUser(ninoUser: INinoUser) {
        this._ninoUser = ninoUser;
    }
}
