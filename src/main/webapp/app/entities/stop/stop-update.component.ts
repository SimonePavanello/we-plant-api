import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IStop } from 'app/shared/model/stop.model';
import { StopService } from './stop.service';
import { IVisit } from 'app/shared/model/visit.model';
import { VisitService } from 'app/entities/visit';

@Component({
    selector: 'jhi-stop-update',
    templateUrl: './stop-update.component.html'
})
export class StopUpdateComponent implements OnInit {
    private _stop: IStop;
    isSaving: boolean;

    visits: IVisit[];
    startTime: string;
    endTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private stopService: StopService,
        private visitService: VisitService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stop }) => {
            this.stop = stop;
        });
        this.visitService.query().subscribe(
            (res: HttpResponse<IVisit[]>) => {
                this.visits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.stop.startTime = moment(this.startTime, DATE_TIME_FORMAT);
        this.stop.endTime = moment(this.endTime, DATE_TIME_FORMAT);
        if (this.stop.id !== undefined) {
            this.subscribeToSaveResponse(this.stopService.update(this.stop));
        } else {
            this.subscribeToSaveResponse(this.stopService.create(this.stop));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStop>>) {
        result.subscribe((res: HttpResponse<IStop>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVisitById(index: number, item: IVisit) {
        return item.id;
    }
    get stop() {
        return this._stop;
    }

    set stop(stop: IStop) {
        this._stop = stop;
        this.startTime = moment(stop.startTime).format(DATE_TIME_FORMAT);
        this.endTime = moment(stop.endTime).format(DATE_TIME_FORMAT);
    }
}
