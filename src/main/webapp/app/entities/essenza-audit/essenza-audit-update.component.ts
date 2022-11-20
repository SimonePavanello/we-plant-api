import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IEssenzaAudit } from 'app/shared/model/essenza-audit.model';
import { EssenzaAuditService } from './essenza-audit.service';
import { IEssenza } from 'app/shared/model/essenza.model';
import { EssenzaService } from 'app/entities/essenza';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-essenza-audit-update',
    templateUrl: './essenza-audit-update.component.html'
})
export class EssenzaAuditUpdateComponent implements OnInit {
    private _essenzaAudit: IEssenzaAudit;
    isSaving: boolean;

    essenzas: IEssenza[];

    users: IUser[];
    dataUltimoAggiornamento: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private essenzaAuditService: EssenzaAuditService,
        private essenzaService: EssenzaService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ essenzaAudit }) => {
            this.essenzaAudit = essenzaAudit;
        });
        this.essenzaService.query().subscribe(
            (res: HttpResponse<IEssenza[]>) => {
                this.essenzas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.essenzaAudit.dataUltimoAggiornamento = moment(this.dataUltimoAggiornamento, DATE_TIME_FORMAT);
        if (this.essenzaAudit.id !== undefined) {
            this.subscribeToSaveResponse(this.essenzaAuditService.update(this.essenzaAudit));
        } else {
            this.subscribeToSaveResponse(this.essenzaAuditService.create(this.essenzaAudit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEssenzaAudit>>) {
        result.subscribe((res: HttpResponse<IEssenzaAudit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEssenzaById(index: number, item: IEssenza) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get essenzaAudit() {
        return this._essenzaAudit;
    }

    set essenzaAudit(essenzaAudit: IEssenzaAudit) {
        this._essenzaAudit = essenzaAudit;
        this.dataUltimoAggiornamento = moment(essenzaAudit.dataUltimoAggiornamento).format(DATE_TIME_FORMAT);
    }
}
