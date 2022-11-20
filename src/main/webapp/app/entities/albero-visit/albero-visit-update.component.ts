import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IAlberoVisit } from 'app/shared/model/albero-visit.model';
import { AlberoVisitService } from './albero-visit.service';
import { IUser, UserService } from 'app/core';
import { IAlbero } from 'app/shared/model/albero.model';
import { AlberoService } from 'app/entities/albero';

@Component({
    selector: 'jhi-albero-visit-update',
    templateUrl: './albero-visit-update.component.html'
})
export class AlberoVisitUpdateComponent implements OnInit {
    private _alberoVisit: IAlberoVisit;
    isSaving: boolean;

    users: IUser[];

    alberos: IAlbero[];
    visitTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private alberoVisitService: AlberoVisitService,
        private userService: UserService,
        private alberoService: AlberoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ alberoVisit }) => {
            this.alberoVisit = alberoVisit;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.alberoService.query().subscribe(
            (res: HttpResponse<IAlbero[]>) => {
                this.alberos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.alberoVisit.visitTime = moment(this.visitTime, DATE_TIME_FORMAT);
        if (this.alberoVisit.id !== undefined) {
            this.subscribeToSaveResponse(this.alberoVisitService.update(this.alberoVisit));
        } else {
            this.subscribeToSaveResponse(this.alberoVisitService.create(this.alberoVisit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAlberoVisit>>) {
        result.subscribe((res: HttpResponse<IAlberoVisit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAlberoById(index: number, item: IAlbero) {
        return item.id;
    }
    get alberoVisit() {
        return this._alberoVisit;
    }

    set alberoVisit(alberoVisit: IAlberoVisit) {
        this._alberoVisit = alberoVisit;
        this.visitTime = moment(alberoVisit.visitTime).format(DATE_TIME_FORMAT);
    }
}
