import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPrivacy } from 'app/shared/model/privacy.model';
import { PrivacyService } from './privacy.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-privacy-update',
    templateUrl: './privacy-update.component.html'
})
export class PrivacyUpdateComponent implements OnInit {
    private _privacy: IPrivacy;
    isSaving: boolean;

    users: IUser[];
    time: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private privacyService: PrivacyService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ privacy }) => {
            this.privacy = privacy;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.privacy.time = moment(this.time, DATE_TIME_FORMAT);
        if (this.privacy.id !== undefined) {
            this.subscribeToSaveResponse(this.privacyService.update(this.privacy));
        } else {
            this.subscribeToSaveResponse(this.privacyService.create(this.privacy));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPrivacy>>) {
        result.subscribe((res: HttpResponse<IPrivacy>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get privacy() {
        return this._privacy;
    }

    set privacy(privacy: IPrivacy) {
        this._privacy = privacy;
        this.time = moment(privacy.time).format(DATE_TIME_FORMAT);
    }
}
