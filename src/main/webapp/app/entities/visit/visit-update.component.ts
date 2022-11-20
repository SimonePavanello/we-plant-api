import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IVisit } from 'app/shared/model/visit.model';
import { VisitService } from './visit.service';
import { IStop } from 'app/shared/model/stop.model';
import { StopService } from 'app/entities/stop';
import { IUser, UserService } from 'app/core';
import { IEventAndLocation } from 'app/shared/model/event-and-location.model';
import { EventAndLocationService } from 'app/entities/event-and-location';

@Component({
    selector: 'jhi-visit-update',
    templateUrl: './visit-update.component.html'
})
export class VisitUpdateComponent implements OnInit {
    private _visit: IVisit;
    isSaving: boolean;

    startpoints: IStop[];

    endpoints: IStop[];

    users: IUser[];

    eventandlocations: IEventAndLocation[];
    startTime: string;
    createdDate: string;
    modifiedDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private visitService: VisitService,
        private stopService: StopService,
        private userService: UserService,
        private eventAndLocationService: EventAndLocationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ visit }) => {
            this.visit = visit;
        });
        this.stopService.query({ filter: 'visit-is-null' }).subscribe(
            (res: HttpResponse<IStop[]>) => {
                if (!this.visit.startPointId) {
                    this.startpoints = res.body;
                } else {
                    this.stopService.find(this.visit.startPointId).subscribe(
                        (subRes: HttpResponse<IStop>) => {
                            this.startpoints = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.stopService.query({ filter: 'visit-is-null' }).subscribe(
            (res: HttpResponse<IStop[]>) => {
                if (!this.visit.endPointId) {
                    this.endpoints = res.body;
                } else {
                    this.stopService.find(this.visit.endPointId).subscribe(
                        (subRes: HttpResponse<IStop>) => {
                            this.endpoints = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.visit.startTime = moment(this.startTime, DATE_TIME_FORMAT);
        this.visit.createdDate = moment(this.createdDate, DATE_TIME_FORMAT);
        this.visit.modifiedDate = moment(this.modifiedDate, DATE_TIME_FORMAT);
        if (this.visit.id !== undefined) {
            this.subscribeToSaveResponse(this.visitService.update(this.visit));
        } else {
            this.subscribeToSaveResponse(this.visitService.create(this.visit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVisit>>) {
        result.subscribe((res: HttpResponse<IVisit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStopById(index: number, item: IStop) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackEventAndLocationById(index: number, item: IEventAndLocation) {
        return item.id;
    }
    get visit() {
        return this._visit;
    }

    set visit(visit: IVisit) {
        this._visit = visit;
        this.startTime = moment(visit.startTime).format(DATE_TIME_FORMAT);
        this.createdDate = moment(visit.createdDate).format(DATE_TIME_FORMAT);
        this.modifiedDate = moment(visit.modifiedDate).format(DATE_TIME_FORMAT);
    }
}
